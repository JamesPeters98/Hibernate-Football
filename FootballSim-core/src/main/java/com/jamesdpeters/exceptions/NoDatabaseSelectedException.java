package com.jamesdpeters.exceptions;

public class NoDatabaseSelectedException extends Exception {

    @Override
    public String getMessage() {
        return "No Database selected! Use SessionStore.setDB()";
    }


}
