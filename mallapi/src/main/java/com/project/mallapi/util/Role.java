package com.project.mallapi.util;

import lombok.Getter;

@Getter
public enum Role {

    ROLE_USER("ROLE_USER", "01"),
    ROLE_MANAGER("ROLE_USER", "02"),
    ROLE_ADMIN("ROLE_ADMIN", "03");


    private final String name;
    private final String value;

    Role(String name, String value) {
        this.name = name;
        this.value = value; // todo jpa라 추후 변경 시 클래스로 빼야될듯?
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
