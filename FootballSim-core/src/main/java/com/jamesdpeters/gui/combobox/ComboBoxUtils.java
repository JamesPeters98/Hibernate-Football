package com.jamesdpeters.gui.combobox;

import javax.swing.*;
import java.util.List;

public class ComboBoxUtils {

    public static <T> void updateFromList(List<ComboItem<T>> list, JComboBox<ComboItem<T>> comboBox){
        GenericComboBoxModel<T> model = new GenericComboBoxModel<>();
        list.forEach(model::addElement);
        comboBox.setModel(model);
        comboBox.setSelectedIndex(0);
    }

    @SuppressWarnings(value = "unchecked")
    public static <T> T getSelectedItem(JComboBox<ComboItem<T>> comboBox){
        ComboItem<T> item = (ComboItem<T>) comboBox.getSelectedItem();
        return item != null ? item.getObj() : null;
    }
}
