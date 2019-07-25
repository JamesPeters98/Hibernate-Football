package com.jamesdpeters.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVWriter {

    private FileWriter fileWriter;
    private List<List<String>> rows;

    public CSVWriter(String filename){
        try {
            fileWriter = new FileWriter(filename+".csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        rows = new ArrayList<>();
    }

    public void setHeader(String... header){
        rows.add(0,Arrays.asList(header));
    }

    public void addRow(String... row){
        rows.add(Arrays.asList(row));
    }

    public void addRow(Object... row){
        List<String> arrayList = new ArrayList<>();
        for (Object o : row) {
            arrayList.add(o.toString());
        }
        rows.add(arrayList);
    }

    public void write() throws IOException {
        for(List<String> rowData : rows){
            fileWriter.append(String.join(",", rowData));
            fileWriter.append("\n");
        }
        fileWriter.flush();
        fileWriter.close();
    }


}
