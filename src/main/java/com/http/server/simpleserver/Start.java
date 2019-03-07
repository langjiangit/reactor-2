package com.http.server.simpleserver;



public class Start {
    public static void main(String[] args) throws Exception {
        HttpServer httpServer = new HttpServer(1234);
        httpServer.start();
    }
}
