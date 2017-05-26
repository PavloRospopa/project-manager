package com.gmail.at.rospopa.pavlo.projectmanager.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum ServiceLocator {
    INSTANCE;
    private static final Logger LOGGER = LogManager.getLogger();

    private Map<String, Object> objects = new ConcurrentHashMap<>();

    public void publish(String name, Object o) {
        objects.put(name, o);
    }

    public void publish(Class<?> clazz, Object o) {
        objects.put(clazz.getName(), o);
    }

    public Object get(String name) {
        return objects.get(name);
    }

    public Object get(Class<?> clazz) {
        return objects.get(clazz.getName());
    }

    public Object remove(String name) {
        return objects.remove(name);
    }

    public Object remove(Class<?> clazz) {
        return objects.remove(clazz.getName());
    }

    public void clear() {
        objects.clear();
    }
}