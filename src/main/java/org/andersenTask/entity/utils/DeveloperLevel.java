package org.andersenTask.entity.utils;

public enum DeveloperLevel {
    j1("j1"), j2("j2"), m1("m1"), m2("m2"), m3("m3"), s1("s1"), s2("s2");
    private final String name;

    DeveloperLevel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
