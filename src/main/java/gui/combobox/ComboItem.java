package gui.combobox;

public class ComboItem<T> {

    private T obj;
    private String title;

    public ComboItem(T obj, String title){
        this.obj = obj;
        this.title = title;
    }

    public T getObj() {
        return obj;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
