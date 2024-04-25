/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.moviereco;

import com.opencsv.exceptions.CsvException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bhats
 */
public class Query implements HttpHandler {
    private MovieMetadata mmd;
    public Query(MovieMetadata mmd){
        this.mmd = mmd;
    }
    @Override
    public void handle(HttpExchange t) throws IOException {
        String query = t.getRequestURI().toString();
        
        //System.out.println("query = "+query);
        
        String[] splitQuery = query.split("[?]",2);
        //System.out.println("splitQuery:");
        //for(int i = 0; i < splitQuery.length; i++){ System.out.println(splitQuery[i]); }

        String request = splitQuery[1];
        String[] splitRequest = request.split("[,]");
        //System.out.println("splitRequest:");
        //for(int i = 0; i < splitRequest.length; i++){ System.out.println(splitRequest[i]); }
     
        
        String movieName = "";
        String releaseYear = "";
        float voterAvg = 0;
        int type = 0;
        
        for(int i = 0; i < splitRequest.length; i++){
            String[] temp = splitRequest[i].split("=", 2);
            //System.out.println("temp:");
            //for(int j = 0; j < temp.length; j++){ System.out.println(temp[j]); }
            
            if("original_title".equals(temp[0])){
                movieName = temp[1];
            }
            else{
                if("release_date".equals(temp[0])){
                    releaseYear = temp[1];
                    //System.out.println("i = "+i+", year = "+releaseYear);
                }
                if("vote_average".equals(temp[0])){
                    voterAvg =  Float.parseFloat(temp[1]);
                    //System.out.println("i = "+i+", vote = "+voterAvg);
                }
                type = 1;
            }
        }
        
        String response = "";
        
        if(type == 0) { response = mmd.getMovieData(movieName); }
        else{ response = mmd.getMovieList(releaseYear, voterAvg); }
        
        t.sendResponseHeaders(200, response.length());
        OutputStream os =  t.getResponseBody();
        //TimeUnit.SECONDS.sleep(5);
        os.write(response.getBytes());
        os.close();
        
    }
    
}