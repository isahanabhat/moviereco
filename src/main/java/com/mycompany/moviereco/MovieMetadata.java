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
}
