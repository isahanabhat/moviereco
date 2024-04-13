/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.moviereco;

import java.io.IOException;
import com.opencsv.exceptions.CsvException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author bhats
 */
public class TopGrossers implements HttpHandler {
    
    private MovieMetadata mmd;
    public TopGrossers(MovieMetadata mmd){
        this.mmd = mmd;
    }
    
    @Override
    public void handle(HttpExchange t) throws IOException {
        String response;
        try {
            
            response = mmd.getTopGrossers();
            t.sendResponseHeaders(200, response.length());
            OutputStream os =  t.getResponseBody();
            //TimeUnit.SECONDS.sleep(5);
            os.write(response.getBytes());
            os.close();
        
        } catch (CsvException ex) {
            Logger.getLogger(TopGrossers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
