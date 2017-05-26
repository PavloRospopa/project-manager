package com.gmail.at.rospopa.pavlo.projectmanager.persistence.dao.impl.jdbc;

import com.gmail.at.rospopa.pavlo.projectmanager.persistence.dao.UserDaoTest;
import com.gmail.at.rospopa.pavlo.projectmanager.persistence.dao.impl.jdbc.util.ConnectionManager;
import com.gmail.at.rospopa.pavlo.projectmanager.persistence.dao.impl.jdbc.util.ScriptExecutant;
import com.gmail.at.rospopa.pavlo.projectmanager.persistence.dao.impl.jdbc.util.TestConnectionManager;
import com.gmail.at.rospopa.pavlo.projectmanager.util.ResourcesUtil;
import org.junit.After;
import org.junit.Before;

public class JdbcUserDaoTest extends UserDaoTest {
    private ScriptExecutant scriptExecutant;

    public JdbcUserDaoTest() {
        ConnectionManager manager = new TestConnectionManager();

        scriptExecutant = new ScriptExecutant(manager);
        scriptExecutant.executePLSQLScript(ResourcesUtil.getResourceFile("dropTablesSeqs.sql"));
        scriptExecutant.executeSQLScript(ResourcesUtil.getResourceFile("createDB.sql"));
        scriptExecutant.executePLSQLScript(ResourcesUtil.getResourceFile("createTriggers.sql"));

        userDao = new JdbcUserDao(manager);
    }

    @Before
    public void setUp() {
        scriptExecutant.executeSQLScript(ResourcesUtil.getResourceFile("fillDB.sql"));
    }

    @After
    public void tearDown() {
        scriptExecutant.executeSQLScript(ResourcesUtil.getResourceFile("refreshDB.sql"));
    }
}