package utils;

import gui.InputField;

public class InputUtil {

    private static InputField inputField = null;

    public static InputField getInputField(){
        if(inputField == null) {
            throw new NullPointerException("Input Field was not set up!!!");
        }
        return inputField;
    }

    public static void setInputField(InputField inputField){
        InputUtil.inputField = inputField;
    }
}
