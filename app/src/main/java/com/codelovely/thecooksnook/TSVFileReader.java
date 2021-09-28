package com.codelovely.thecooksnook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class TSVFileReader {
    InputStream inputStream;

    public TSVFileReader(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public List<String[]> read(){
        List<String[]> resultList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            int numTimes = 0;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split("\\t");
                resultList.add(row);
                numTimes++;
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }
        return resultList;
    }
}
