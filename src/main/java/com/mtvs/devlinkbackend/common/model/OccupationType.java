package com.mtvs.devlinkbackend.common.model;

public enum OccupationType {
    CLIENT("클라이언트", "Client"),
    SERVER("서버", "Server"),
    DESIGN("디자인", "Design"),
    PLANNER("기획", "Planner"),
    AI_ENGINEER("AI", "AIEngineer");

    private final String displayName;
    private final String fieldName;

    OccupationType(String displayName, String fieldName) {
        this.displayName = displayName;
        this.fieldName = fieldName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFieldName() {
        return fieldName;
    }
}

