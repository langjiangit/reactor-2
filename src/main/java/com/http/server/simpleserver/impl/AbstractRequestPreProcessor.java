package com.http.server.simpleserver.impl;

import com.http.server.simpleserver.RequestPreProcessor;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.Callable;

public abstract class AbstractRequestPreProcessor implements RequestPreProcessor {

    protected MethodExecutionManager methodExecuteManager = null;

    public final void setMethodExecutionManager(MethodExecutionManager methodExeManager) {
        if (this.methodExecuteManager == null)
            this.methodExecuteManager = methodExeManager;
    }

    public abstract FullHttpMessage preprocessRequestWithTimeout(String paramString1, String paramString2, HttpRequest paramHttpRequest);

    public final FullHttpMessage preprocessRequest(final String projectId, final String projectName, final HttpRequest request) {
        FullHttpMessage httpMsg;
        try {
//            FullHttpMessage httpMsg;
            if (this.methodExecuteManager != null) {
                httpMsg = (FullHttpMessage)this.methodExecuteManager.executeWithTimeout(new Callable()
                {
                    public FullHttpMessage call() throws Exception {
                        AbstractRequestPreProcessor.this.methodExecuteManager.injectThreadContext(Thread.currentThread(), AbstractRequestPreProcessor.this.toString(), AbstractRequestPreProcessor.this.getClass().getClassLoader());
                        return AbstractRequestPreProcessor.this.preprocessRequestWithTimeout(projectId, projectName, request);
                    }
                });
            } else
                httpMsg = (FullHttpMessage)request;
        } catch (Exception e) {
//            FullHttpMessage httpMsg;
            httpMsg = (FullHttpMessage)buildBadRequestRsp(e.getMessage());
        }
        return httpMsg;
    }

    private HttpResponse buildBadRequestRsp(String reason) {
        if (StringUtils.isBlank(reason)) {
            reason = "Internal Server Error!";
        }
        byte[] bytes = reason.getBytes();
        FullHttpResponse fullRsp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR, Unpooled.copiedBuffer(bytes));

        fullRsp.headers().set("Content-Type", "text/html,application/json");
        fullRsp.headers().set("Content-Length", Integer.valueOf(bytes.length));
        return fullRsp;
    }

}
