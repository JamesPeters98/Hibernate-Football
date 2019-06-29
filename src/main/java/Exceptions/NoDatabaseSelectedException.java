package Exceptions;

public class NoDatabaseSelectedException extends Exception {

    @Override
    public String getMessage() {
        return "No Database selected! Use SessionStore.setDB()";
    }


}
