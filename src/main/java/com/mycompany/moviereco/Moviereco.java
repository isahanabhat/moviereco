/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.moviereco;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.opencsv.exceptions.CsvException;
import com.sun.net.httpserver.HttpServer;

/**
 *
 * @author bhats
 */
public class Moviereco {

    public static void main(String[] args) throws IOException, CsvException {
        String file = "../moviereco/data/movies_metadata.csv";
        
        MovieMetadata mmd = new MovieMetadata(file);
        
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/heartbeat", new Heartbeat());
        server.createContext("/stopserver", new StopServer(server));
        server.createContext("/highestmovierevenue", new TopGrossers(mmd));
        
        server.setExecutor(Executors.newFixedThreadPool(5));
        server.start();
        System.out.println("Web server started");
    }
}
