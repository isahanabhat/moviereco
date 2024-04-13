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

/**
 *
 * @author bhats
 */
public class MovieMetadata {
    private String file;
    private ArrayList<ArrayList<String>> data;
    
    public MovieMetadata(String file) throws FileNotFoundException, IOException, CsvValidationException{
        this.file = file;
        FileReader filereader = new FileReader(file);    
        CSVReader reader = new CSVReader(filereader);
        
        data = new ArrayList<>();
        String[] row;
        
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
        String rev = "Top 5 revenue:\n";
        
        ArrayList<String> header = data.get(0);
        //System.out.println(header);
        int revIndex = header.indexOf("revenue");
        //System.out.println(revIndex);
        ArrayList<BigInteger> revenueVal = new ArrayList<>();
        
        for(int i = 1; i < data.size(); i++){
            ArrayList<String> currow = data.get(i);
            //System.out.println(i);
            if(currow.size() != 24) {
                continue;
            }
            try{
                BigInteger x = new BigInteger(currow.get(revIndex));
                revenueVal.add(x);
            } catch (NumberFormatException e){
                
            }
            
        }
        //System.out.println(revenueVal);
        Collections.sort(revenueVal, Collections.reverseOrder());

        for(int i = 0; i < 5; i++){
            // System.out.println("Revenue = "+ revenueVal.get(i));
            int ind = i+1;
            rev = rev + ind + ". " + revenueVal.get(i) + "\n";
        }
        return rev;
    }
}
