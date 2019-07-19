package gui;

public class Output {

    private static TextAreaOutputStream outputStream;

    public static void setOutputStream(TextAreaOutputStream outputStream){
        Output.outputStream = outputStream;
    }

    public static void clear(){
        if(check()) outputStream.clear();
    }

    public static void close(){
        if(check()) outputStream.close();
    }

    public static void flush(){
        if(check()) outputStream.flush();
    }

    private static boolean check(){
        if(outputStream == null){
            System.err.println("No Output stream set!");
            return false;
        }
        else return true;
    }
}
