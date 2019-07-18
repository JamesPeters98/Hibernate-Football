package optionmenu.menus;

import frameworks.Season;
import optionmenu.options.Option;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Menu {

    private Scanner scanner;
    private Season season;
    private Menu parentMenu;

    public Menu(Season season){
        this.season = season;
        scanner = new Scanner(System.in);
    }

    public Menu(Season season, Menu parentMenu){
        this(season);
        this.parentMenu = parentMenu;
    }

    public abstract ArrayList<Option> getOptions();

    public abstract ArrayList<Menu> getSubMenus();

    public abstract String getMenuName();

    public Menu getParentMenu(){
        return parentMenu;
    }


    /**
     * Displays menu to console and waits for selection.
     */
    public void open(){
        System.out.println("------------");
        System.out.println(getMenuName());
        System.out.println("------------");

        int options = 0;
        if(getOptions() != null) {
            for (int i = 0; i < getOptions().size(); i++) {
                Option option = getOptions().get(i);
                System.out.println((options + 1) + ". " + option.getTitle() + " - " + option.getDescription());
                options++;
            }
        }
        if(getSubMenus() != null) {
            for (int i = 0; i < getSubMenus().size(); i++) {
                Menu subMenu = getSubMenus().get(i);
                System.out.println((options + 1) + ". " + subMenu.getMenuName());
                options++;
            }
        }
        if(getParentMenu() != null){
            Menu menu = getParentMenu();
            System.out.println((options + 1) + ". Return to " + menu.getMenuName());
        }

        int menuChoice = scanner.nextInt();

        while(!isValidMenuChoice(menuChoice)){
            System.out.println("Invalid menu choice! Try again.");
            menuChoice = scanner.nextInt();
        }

        displayMenu(menuChoice);

//        Option option = getOptions().get(menuChoice-1);
//        option.display();
    }

    private boolean isValidMenuChoice(int i){
        int size = 0;
        if(getOptions() != null) size += getOptions().size();
        if(getSubMenus() != null) size += getSubMenus().size();
        if(getParentMenu() != null) size++;

        return (i > 0) && (i <= size);
    }

    private void displayMenu(int menuChoice){
        //Menu sizes
        int optionSize = 0, subMenuSize = 0;
        if(getOptions() != null) optionSize = getOptions().size();
        if(getSubMenus() != null) subMenuSize = getSubMenus().size();

        if(menuChoice <= optionSize){
            getOptions().get(menuChoice-1).display();
        }
        if(menuChoice <= (optionSize+subMenuSize)){
            getSubMenus().get(menuChoice-getOptions().size()-1).open();
        }
        else {
            getParentMenu().open();
        }
    }

    public Season getSeason(){
        return season;
    }
}
