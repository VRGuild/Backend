package com.mtvs.devlinkbackend.common.model;

public enum AcceptStatus {
    DELETED(0),
    PENDING(1),    // 대기중
    ACCEPTED(2),   // 수락
    REJECTED(3);   // 거절

    private final int value;

    AcceptStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    // Integer 값에서 Enum으로 변환하는 메소드
    public static AcceptStatus fromValue(int value) {
        for (AcceptStatus status : AcceptStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }
}
