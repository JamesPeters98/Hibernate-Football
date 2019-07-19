package optionmenu.options;

import optionmenu.menus.Menu;

public class ExitOption extends Option {
    public ExitOption(Menu menu) {
        super(menu);
    }

    @Override
    public String getTitle() {
        return "Exit";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public Menu postRunMenu() {
        return null;
    }

    @Override
    protected void run() {
        System.exit(0);
    }
}
