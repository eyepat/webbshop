package Model.BO;

import Model.DB.ItemDB;

import java.util.Collection;

public class Item {
    private String name, descr;
    private int id;
    private float price;

    static public Collection searchItems(String group) {
        return ItemDB.searchItems(group);
    }

    protected Item(int id,String name, String descr,float price) {
        this.id = id;
        this.name = name;
        this.descr = descr;
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescr() {
        return descr;
    }
    public void setDescr(String descr) {
        this.descr = descr;
    }

    public int getId() {
        return id;
    }
    public static Item getById(int id) {
        return ItemDB.getById(id);
    }
    public void setId(int id) {
        this.id = id;
    }
}
