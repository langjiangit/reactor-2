package com.http.handle;

import io.netty.handler.codec.http.FullHttpRequest;

public abstract class Handle {
//    public abstract

    public abstract void run(FullHttpRequest fullHttpRequest);
}
