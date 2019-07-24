package optionmenu.panels;

import optionmenu.options.Option;

import javax.swing.*;

public abstract class Panel extends SwingWorker {

    protected Option option;
    protected JFrame frame;

    public Panel(Option option, JFrame frame){
        this.frame = frame;
        this.option = option;
    }

    protected JButton getBackbutton(){
        JButton button = new JButton("Back");
        button.addActionListener(e -> option.getParentMenu().display());
        return button;
    }

    public abstract JComponent getPanel();
}
