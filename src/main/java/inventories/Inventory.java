package inventories;


import actors.items.Item;

import java.util.ArrayList;
import java.util.List;

public abstract class Inventory {

    final private List<Item> items = new ArrayList<>();

    // @TODO replace namecheck with UID
    public boolean contains(String itemName) {
            return items.stream().anyMatch(item -> item.name().equals(itemName));
    }

    public List<Item> items() {
        return items;
    }
}