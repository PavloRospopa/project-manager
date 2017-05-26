package com.gmail.at.rospopa.pavlo.projectmanager.application;

import com.gmail.at.rospopa.pavlo.projectmanager.controller.RedirectController;
import com.gmail.at.rospopa.pavlo.projectmanager.dispatcher.DispatcherServletBuilder;
import com.gmail.at.rospopa.pavlo.projectmanager.persistence.dao.factory.DaoFactory;
import com.gmail.at.rospopa.pavlo.projectmanager.persistence.dao.factory.JdbcDaoFactory;
import com.gmail.at.rospopa.pavlo.projectmanager.persistence.dao.impl.jdbc.util.ConnectionManager;
import com.gmail.at.rospopa.pavlo.projectmanager.persistence.dao.impl.jdbc.util.ScriptExecutant;
import com.gmail.at.rospopa.pavlo.projectmanager.service.*;
import com.gmail.at.rospopa.pavlo.projectmanager.service.impl.*;
import com.gmail.at.rospopa.pavlo.projectmanager.util.PropertiesLoader;
import com.gmail.at.rospopa.pavlo.projectmanager.util.ResourcesUtil;

import javax.servlet.ServletContext;

public class Application {
    private ServletContext servletContext;
    private ServiceLocator serviceLocator;
    private ConnectionManager manager;
    private DaoFactory daoFactory;
    private PropertiesLoader propLoader;

    public Application() {
    }

    public Application(ServletContext servletContext, ServiceLocator serviceLocator) {
        this.servletContext = servletContext;
        this.serviceLocator = serviceLocator;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public ServiceLocator getServiceLocator() {
        return serviceLocator;
    }

    public void setServiceLocator(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

    public void init() {
        propLoader = new PropertiesLoader();

        setUpPersistence();
        createDatabase();
        publishServices();

        DispatcherServletBuilder servletBuilder = new DispatcherServletBuilder(servletContext);
        addMappings(servletBuilder)
                .buildAndRegister("Dispatcher servlet", "/app/*");
    }

    private DispatcherServletBuilder addMappings(DispatcherServletBuilder servletBuilder) {
        return servletBuilder.addMapping("/", new RedirectController("/index"));
    }

    private void setUpPersistence() {
        manager = ConnectionManager
                .fromJNDI(propLoader.getDbProperties().getProperty("jndi.entry.path"));
        daoFactory = new JdbcDaoFactory(manager);
    }

    private void createDatabase() {
        ScriptExecutant scriptExecutant = new ScriptExecutant(manager);
        scriptExecutant.executePLSQLScript(ResourcesUtil.getResourceFile("dropTablesSeqs.sql"));
        scriptExecutant.executeSQLScript(ResourcesUtil.getResourceFile("createDB.sql"));
        scriptExecutant.executePLSQLScript(ResourcesUtil.getResourceFile("createTriggers.sql"));
    }

    private void publishServices() {
        serviceLocator = ServiceLocator.INSTANCE;
        serviceLocator.publish(ConnectionManager.class, manager);
        serviceLocator.publish(DaoFactory.class, daoFactory);
        serviceLocator.publish(PropertiesLoader.class, propLoader);

        AdministratorService administratorService =
                new AdministratorServiceImpl(daoFactory.getAdministratorDao());

        CustomerService customerService = new CustomerServiceImpl(daoFactory.getCustomerDao());

        EmployeeService employeeService = new EmployeeServiceImpl(daoFactory.getEmployeeDao());

        ProjectManagerService projectManagerService =
                new ProjectManagerServiceImpl(daoFactory.getProjectManagerDao());

        ProjectService projectService = new ProjectServiceImpl(daoFactory.getProjectDao());

        SprintService sprintService = new SprintServiceImpl(daoFactory.getSprintDao());

        TaskDelegationService taskDelegationService =
                new TaskDelegationServiceImpl(daoFactory.getTaskDelegationDao());

        TaskService taskService = new TaskServiceImpl(daoFactory.getTaskDao());

        TaskTimeRequestService taskTimeRequestService =
                new TaskTimeRequestServiceImpl(daoFactory.getTaskTimeRequestDao());

        serviceLocator.publish(AdministratorService.class, administratorService);
        serviceLocator.publish(CustomerService.class, customerService);
        serviceLocator.publish(EmployeeService.class, employeeService);
        serviceLocator.publish(ProjectManagerService.class, projectManagerService);
        serviceLocator.publish(ProjectService.class, projectService);
        serviceLocator.publish(SprintService.class, sprintService);
        serviceLocator.publish(TaskDelegationService.class, taskDelegationService);
        serviceLocator.publish(TaskService.class, taskService);
        serviceLocator.publish(TaskTimeRequestService.class, taskTimeRequestService);
    }
}