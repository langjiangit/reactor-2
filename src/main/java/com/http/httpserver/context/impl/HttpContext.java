package com.http.httpserver.context.impl;

import com.http.httpserver.context.Context;
import com.http.httpserver.request.Request;
import com.http.httpserver.request.impl.HttpRequest;
import com.http.httpserver.response.Response;
import com.http.httpserver.response.impl.HttpResponse;

import java.nio.channels.SelectionKey;

public class HttpContext extends Context {

    private Request request;
    private Response response;

    @Override
    public void setContext(String requestHeader, SelectionKey key) {

        //初始化request
        request = new HttpRequest(requestHeader);
        //初始化response
        response = new HttpResponse(key);
        setRequest();
        setResponse();
    }

    private void setRequest() {
        super.request = this.request;
    }

    private void setResponse() {
        super.response = this.response;
    }
}