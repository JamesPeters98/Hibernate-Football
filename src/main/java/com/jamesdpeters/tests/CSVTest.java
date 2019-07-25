package com.jamesdpeters.tests;

import com.jamesdpeters.utils.CSVWriter;

import java.io.IOException;

public class CSVTest {

    public static void main(String[] args) throws IOException {
        CSVWriter csv = new CSVWriter("CSVTest");
        csv.setHeader("Column 1","Column 2","Column 3");
        csv.addRow("data 1","data 2","data 3");
        csv.addRow("data 1","data 2","data 3");
        csv.addRow("data 1","data 2","data 3");
        csv.addRow("data 1","data 2","data 3");

        csv.write();
    }
}
