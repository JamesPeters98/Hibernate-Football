package com.jamesdpeters.gui.combobox;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.List;

public class GenericComboBoxModel<T> implements MutableComboBoxModel<ComboItem<T>> {

    private List<ComboItem<T>> list;
    private ComboItem<T> selectedItem;

    public GenericComboBoxModel(){
        list = new ArrayList<>();
    }


    @Override
    public void addElement(ComboItem<T> item) {
        list.add(item);
    }

    @Override
    public void removeElement(Object obj) {
        list.remove(obj);
    }

    @Override
    public void insertElementAt(ComboItem<T> item, int index) {
        list.add(index,item);
    }


    @Override
    public void removeElementAt(int index) {
        list.remove(index);
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public void setSelectedItem(Object anItem) {
        try{
            selectedItem = (ComboItem<T>) anItem;
        } catch (ClassCastException ignore){}
    }

    @Override
    public ComboItem<T> getSelectedItem() {
        return selectedItem;
    }

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public ComboItem<T> getElementAt(int index) {
        return list.get(index);
    }


    @Override
    public void addListDataListener(ListDataListener l) {
        //Dont need this.
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        //Don't need this.
    }
}
