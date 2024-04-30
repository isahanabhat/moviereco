/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.moviereco;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author bhats
 */
public class MovieMetadata {
    private String file;
    private ArrayList<ArrayList<String>> data;
    private ArrayList<String> header;
    
    public MovieMetadata(String file) throws FileNotFoundException, IOException, CsvValidationException{
        this.file = file;
        FileReader filereader = new FileReader(file);    
        CSVReader reader = new CSVReader(filereader);
        
        data = new ArrayList<>();
        header = new ArrayList<>();
         
        String[] row = reader.readNext();
        for (int i = 0; i < row.length; i++){
            header.add(row[i]);
        }
        
        while ((row = reader.readNext()) != null){
            ArrayList<String> line = new ArrayList<>();
            for (int i = 0; i < row.length; i++){
                line.add(row[i]);
            }
            data.add(line);
        }
        
        // this.data = fileData;
    }
    
    public boolean isCSVLoaded(){
        return !data.isEmpty();
    }
    
    public String getTopGrossers() throws IOException, CsvException{
        
        int revIndex = header.indexOf("revenue");
        int movIndex = header.indexOf("original_title");
        
        Collections.sort(data, new Comparator<ArrayList>() {
            @Override
            public int compare(ArrayList o1, ArrayList o2) {
                try{
                    BigInteger i1 = new BigInteger((String)o1.get(revIndex));
                    BigInteger i2 = new BigInteger((String)o2.get(revIndex));
                    return (i1.compareTo(i2));
                } catch (NumberFormatException e){
                    
                }
                //return ((String) o1.get(revIndex)).compareTo((String) o2.get(revIndex));
                return 0;
            }
        });
        
        JSONObject j = new JSONObject();
        JSONArray mov = new JSONArray();
        JSONArray rev = new JSONArray();
        
        for(int i = data.size() - 1; i > data.size() - 6; i--){
            ArrayList<String> row = data.get(i);
            mov.put(row.get(movIndex));
            rev.put(row.get(revIndex));
        }
        
        j.put("movies", mov);
        j.put("revenue", rev);
        
        return j.toString();
    }
    
    public String getMovieData(String movieName){
        String movie = movieName.replaceAll("%20", " ");
        int movIndex = header.indexOf("original_title");
        
        ArrayList<String> row = new ArrayList<>();
        
        for(int i = 0; i < data.size(); i++){
            ArrayList<String> temp = data.get(i);
            if(temp.get(movIndex).equals(movie)){
                row = temp;
            }
        }
        
        JSONObject j = new JSONObject();
        
        for(int i = 0; i < header.size(); i++){
            j.put(header.get(i), row.get(i));
        }
        
        return j.toString();
    }
    
    public String getMovieList(String releaseYear, float voterAvg){
        
        String[] date = releaseYear.split("-");
        
        String year = date[date.length-1];
        
        int movInd = header.indexOf("original_title");
        int yearInd = header.indexOf("release_date");
        int voterInd = header.indexOf("vote_average");
        
        JSONObject j = new JSONObject();
        JSONArray mov = new JSONArray();
        
        // ArrayList<String> row = new ArrayList<>();
        //System.out.println("voter = "+voterAvg);
        
        for(int i = 0; i < data.size(); i++){
            ArrayList<String> temp = data.get(i);
            String vote = temp.get(voterInd);
            if("".equals(vote)) { continue; }
            float voteVal = Float.parseFloat(vote);
            
            String dateVal = temp.get(yearInd);
            if(dateVal.length() != 10) { continue; }
            
            String[] dateYear = dateVal.split("-");
            if(dateYear.length != 3){ continue; }
            
            if(year.equals(dateYear[dateYear.length-1]) ){
                //System.out.println("vote = "+voteVal);
                if( voteVal >= voterAvg ){
                    //System.out.println("success");
                    mov.put(temp.get(movInd));
                }
            }
            
        }
        j.put("movies", mov);
        System.out.println(j.toString());
        System.out.println(j.toString().length());
        return j.toString();
    }
}
