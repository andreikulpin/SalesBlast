package ru.arcsinus.salesblast.event;

import ru.arcsinus.salesblast.model.Item;

/**
 * Created by Andrei on 15.11.2016.
 */

public class FragmentEvent {
    private final Item item;

    public FragmentEvent(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
