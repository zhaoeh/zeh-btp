package com.snowflake.handler;

import java.util.Objects;

public class AuthAccessFailHandler {

    public void doFail(Runnable failTask) {
        if (Objects.nonNull(failTask)) {
            failTask.run();
        }
    }
}
