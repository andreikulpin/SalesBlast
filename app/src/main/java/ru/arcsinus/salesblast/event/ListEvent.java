package ru.arcsinus.salesblast.event;

import java.util.ArrayList;

import ru.arcsinus.salesblast.model.Item;

/**
 * Created by Andrei on 14.11.2016.
 */

public class ListEvent {
    private final ArrayList<Item> list;

    public ListEvent(ArrayList<Item> list) {
        this.list = list;
    }

    public ArrayList<Item> getList() {
        return list;
    }
}
