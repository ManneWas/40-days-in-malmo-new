package actors.characters;

import actors.Actor;
import actors.items.Equipment;
import dev.morphia.annotations.Transient;

import java.util.*;

public class Character extends Actor {
    double hp;
    protected int xp = 0;
    final int requiredXp = 100;
    @Transient
    private List<Equipment> equipment;

    public Character setHp(final double hp) {
        this.hp = hp;
        return this;
    }

    double attack;

    public double hp() {
        return hp;
    }

    public double attack() {
        return attack;
    }

    public void updateHp(final double v) {
        hp += v;
    }

    public int xp() {
        return xp;
    }

    protected void levelUp() {
        do {
            level += 1;
            xp -= requiredXp;
        }
        while (xp > requiredXp);
    }

    private int random_xp(int low, int high) {
        return new Random().nextInt(high - low) + low;
    }

    public void addXp(int amount) {
        xp += amount;
        if (xp >= requiredXp) {
            levelUp();
        }
    }

    public void addXp(Character character) {
        int amount;
        if (level < character.level()) {
            amount = random_xp(30, 49);
        }
        else if (level == character.level()) {
            amount = random_xp(20, 30);
        }
        else {
            amount = random_xp(1, 3);
        }
        xp += amount + character.xp();
        if (xp >= requiredXp) {
            levelUp();
        }
    }

    public List<Equipment> equipment() {
        return equipment;
    }
}
