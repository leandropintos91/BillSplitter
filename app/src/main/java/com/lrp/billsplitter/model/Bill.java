package com.lrp.billsplitter.model;

import java.util.ArrayList;
import java.util.List;

public class Bill {

    String title;
    List<Item> itemList = new ArrayList<>();
    double total = 0;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void addItem(Item item) {
        itemList.add(item);
    }
}
