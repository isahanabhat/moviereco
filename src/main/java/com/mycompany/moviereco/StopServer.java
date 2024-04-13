/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.moviereco;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author bhats
 */
public class StopServer implements HttpHandler {
    private HttpServer server;
    public StopServer(HttpServer server) {
        this.server = server;
    }
    @Override
    public void handle(HttpExchange t) throws IOException {
        System.out.println("Stopping server after 1 second delay");
        server.stop(1);
        // httpThreadPool.shutdownNow();
    }
}
