package gui;

import java.util.Scanner;

public class ScannerInputField extends InputField {

    private Scanner scanner;

    public ScannerInputField() {
        scanner = new Scanner(System.in);
    }

    @Override
    public String nextString() {
        return scanner.nextLine();
    }

    @Override
    public int nextInt() {
        return scanner.nextInt();
    }

    @Override
    public void nextLine() {
        scanner.nextLine();
    }
}
