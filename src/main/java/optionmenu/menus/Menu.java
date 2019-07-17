package optionmenu.menus;

import frameworks.Season;
import optionmenu.options.Option;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Menu {

    private Scanner scanner;
    private Season season;

    public Menu(Season season){
        this.season = season;
        scanner = new Scanner(System.in);
    }

    public abstract ArrayList<Option> getOptions();

    public abstract String getMenuName();


    /**
     * Displays menu to console and waits for selection.
     */
    public void open(){
        System.out.println("------------");
        System.out.println(getMenuName());
        System.out.println("------------");

        for(int i = 0; i < getOptions().size(); i++){
            Option option = getOptions().get(i);
            System.out.println((i+1)+". "+option.getTitle()+" - "+option.getDescription());
        }

        int menuChoice = scanner.nextInt();

        while(!isValidMenuChoice(menuChoice)){
            System.out.println("Invalid menu choice! Try again.");
            menuChoice = scanner.nextInt();
        }

        Option option = getOptions().get(menuChoice-1);
        option.display();
    }

    private boolean isValidMenuChoice(int i){
        if((i > 0) && (i <= getOptions().size())) return true;
        return false;
    }

    public Season getSeason(){
        return season;
    }
}
