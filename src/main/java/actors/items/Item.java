package actors.items;

import actors.Actor;

public class Item extends Actor {

    private int size;

    public Item() {
    }


    public int size() {
        return size;
    }

    public Item setSize(int size) {
        this.size = size;
        return this;
    }


}
