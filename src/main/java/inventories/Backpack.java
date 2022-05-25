package inventories;

import actors.items.Item;
import dev.morphia.annotations.Entity;

@Entity
public class Backpack extends Inventory {

    private int spaceLimit = 20;
    private int currentSpace = spaceLimit;

    public Backpack() {}


    public boolean spaceCheck(Item item) {
        boolean result;
        if(item == null){
            result = false;
        }
        else result = item.size() <= this.currentSpace;
        return result;
    }

    public int spaceLimit() {
        return spaceLimit;
    }

    public int currentSpace() {
        return currentSpace;
    }



}








