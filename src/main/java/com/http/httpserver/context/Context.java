package com.http.httpserver.context;

import com.http.httpserver.request.Request;
import com.http.httpserver.response.Response;

import java.nio.channels.SelectionKey;

public abstract class Context {

    protected Request request;
    protected Response response;

    /**
     * 设置当前连接的上下文
     * @param:  @return
     * @return: Context

     */
    public abstract void setContext(String requestHeader, SelectionKey key);

    /**
     * 得到Request
     * @param:  @return
     * @return: Request

     */
    public Request getRequest() {
        return request;
    }

    /**
     * 得到Response
     * @param:  @return
     * @return: Response

     */
    public Response getResponse() {
        return response;
    }

}