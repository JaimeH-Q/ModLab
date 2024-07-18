package ml.jaime.model;

public class DeserializedMenu {
    private int rows;
    private String title;

    public DeserializedMenu(String title, int rows) {
        this.rows = rows;
        this.title = title;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
