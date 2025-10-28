package com.snowflake.permission.evaluator;

import lombok.Getter;

public class PermissionDesc {

    private final boolean evaluatorSuccess;

    @Getter
    private final StringBuilder msg;

    private PermissionDesc(boolean evaluatorSuccess,StringBuilder msg){
        this.evaluatorSuccess = evaluatorSuccess;
        this.msg = msg;
    }

    public static PermissionDesc of(boolean evaluatorSuccess,StringBuilder msg){
        return new PermissionDesc(evaluatorSuccess,msg);
    }

    public boolean getResult(){
        return evaluatorSuccess;
    }

}
