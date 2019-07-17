package optionmenu.options;

import optionmenu.menus.Menu;
import org.hibernate.Session;
import utils.SessionStore;

import java.util.Scanner;

public abstract class Option {

    private Scanner scanner;
    protected Session session;
    private Menu menu;

    public Option(Menu menu){
        this.menu = menu; //Parent menu to this option.
        scanner = new Scanner(System.in);
        session = SessionStore.getSession();
    }

    public abstract String getTitle();

    public abstract String getDescription();

    public abstract Menu postRunMenu();

    protected abstract void run();

    public void display(){
        System.out.println("--------------------------");
        System.out.println(getTitle());
        System.out.println("--------------------------");
        run();
        System.out.println();
        System.out.println("Continue [Enter] ");
        scanner.nextLine();
        postRunMenu().open();
    }

    public Menu getParentMenu(){
        return menu;
    }


}
