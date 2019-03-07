package com.http.server.simpleserver.impl;

import java.util.concurrent.Callable;

public abstract interface MethodExecutionManager
{
    public abstract <T> T executeWithTimeout(Callable<T> paramCallable)
            throws Exception;

    public abstract void setMethodTimeoutDurationInMilliSeconds(int paramInt);

    public abstract void injectThreadContext(Thread paramThread, String paramString, ClassLoader paramClassLoader);
}

