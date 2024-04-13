/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.moviereco;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author bhats
 */
public class Heartbeat implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {
        String response = "I AM ALIVE";
        t.sendResponseHeaders(200, response.length());
        OutputStream os =  t.getResponseBody();
        
        os.write(response.getBytes());
        os.close();
    }
}
