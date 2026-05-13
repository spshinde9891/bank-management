package com.bank.bank.enums;

public enum OperationTypeEnum {

    DEPOSIT(1),
    WITHDRAW(2);

    private int operationTypeId;

    private OperationTypeEnum status;

    OperationTypeEnum(int operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public int getOperationTypeId() {
        return operationTypeId;
    }

    public OperationTypeEnum getStatus() {
        return status;
    }
}