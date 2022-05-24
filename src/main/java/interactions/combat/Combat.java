package interactions.combat;

import actors.characters.Character;
import actors.items.Equipment;
import interactions.system.*;

import java.text.DecimalFormat;
import java.util.*;

public class Combat {

    final List<Battler> battlers;

    boolean resolved = false;

    Battler instigator;
    Battler responder;


    public Combat() {
        battlers = new ArrayList<>();
    }

    public boolean run() {
        if (battlers.stream().allMatch(Battler::isAlive) && !resolved) {
            battle();
        }
        Battler player = battlers.stream().filter(battler -> battler.character().equals(Menu.instance().player())).findFirst().orElse(null);
        assert player != null;
        return player.isAlive();
    }

    private void battle() {

        printf("%s %n", "#".repeat(10), "# BATTLE #", "#".repeat(10));

        printf("Instigator: %s HP: %s", instigator.name(), format(instigator.hp()));

        printf("Responder: %s HP: %s", responder.name(), format(responder.hp()));

        if (battlers.stream().allMatch(battler -> battler.choice == Battler.Choice.DEFEND)) {
            printf("You look at each other");
        }
        else {
            for (Battler battler : battlers) {
                //@optimize replace array with custom container for 2 objects, with methods for fetching other
                Battler enemy = battlers.stream().filter(b -> b.role != battler.role).findFirst().orElseThrow();
                Battler.Choice choice = battler.choice();
                Battler.Choice enemyChoice = enemy.choice();
                if (choice != null && battler.isAlive() && !resolved) {
                    switch (choice) {
                        case ATTACK -> attack(enemy, battler);
                        case RUN -> {
                            double outcome = Math.random() * 200.0;
                            if (outcome > 100) {
                                resolved = true;
                            }
                            else if (enemyChoice == Battler.Choice.ATTACK) {
                                trueAttack(battler, enemy);
                            }
                        }
                    }
                }
                if (!enemy.isAlive()) {
                    battler.character().addXp(enemy.character);
                    resolved = true;
                }
            }
            battlers.forEach(Battler::clearChoice);
        }

    }

    private void attack(final Battler defender, final Battler attacker) {
        double amount = attacker.attack() * defender.defenceModifier();
        defender.damage(amount);
    }

    private void trueAttack(final Battler defender, final Battler attacker) {
        defender.damage(attacker.specialAttack());
    }

    public String format(double value) {
        return new DecimalFormat("0.0").format(value);
    }

    private void printf(String string, Object... args) {
        System.out.printf(string, args);
    }

    public Combat setInstigator(final Character character) {
        battlers.add(0, new Battler(Battler.Role.INSTIGATOR, character));
        instigator = battlers.get(0);
        return this;
    }

    public Combat setResponder(final Character character) {
        if (battlers.isEmpty()){
            battlers.add(0,null);
        }
        battlers.add(1, new Battler(Battler.Role.RESPONDER, character));
        instigator = battlers.get(1);
        return this;
    }

    public Combat setResolved(final boolean resolved) {
        this.resolved = resolved;
        return this;
    }

    private static class Battler {
        Character character;
        final Role role;


        Choice choice;

        private Battler(final Role role, final Character character) {
            this.role = role;
            this.character = character;
            if (character().equals(Menu.instance().player())) {
                String enemy = Menu.instance().player().inFocus().name();
                Menu.instance().commands().add(new Command("Attack " + enemy, "chooseAttack", this));
                Menu.instance().commands().add(new Command("Defend", "chooseDefend", this));
                Menu.instance().commands().add(new Command("Run", "chooseRun", this));
            }
            choice = null;
        }

        private void chooseAttack() {
            choice = Choice.ATTACK;
        }

        private void chooseDefend() {
            choice = Choice.DEFEND;

        }

        private void chooseSpecial() {
            choice = Choice.SPECIAL;

        }

        private void chooseRun() {
            choice = Choice.RUN;

        }

        public void damage(double amount) {
            character.updateHp(amount);
        }

        public boolean isAlive() {
            return hp() > 0;
        }

        public double attack() {
            return character.attack();
        }

        public double specialAttack() {
            double result = attack();
            double outcome = Math.abs(Math.random() * 200.0);
            if (outcome <= 100) {
                result *= 1.20;
            }
            else if (outcome < 200) {
                result *= 1.40;
            }
            else if (outcome >= 200) {
                result *= 1.70;
            }
            return result;
        }

        public Character character() {
            return character;
        }

        public void clearChoice() {
            choice = null;
        }

        enum Role {
            INSTIGATOR, RESPONDER
        }

        enum Choice {
            ATTACK, DEFEND, SPECIAL, RUN
        }

        public String name() {
            return character.name();
        }

        public double hp() {
            return character.hp();
        }

        public Choice choice() {
            if (!character().equals(Menu.instance().player()) && choice == null) {
                randomizeChoice();
            }
            return choice;
        }

        private void randomizeChoice() {
            double outcome = Math.abs(Math.random() * 200.0);
            if (outcome <= 120) {
                choice = Choice.ATTACK;
            }
            else if (outcome < 200) {
                choice = Choice.DEFEND;
            }
            else {
                choice = Choice.RUN;
            }

        }

        public double defenceModifier() {
            return choice == Choice.DEFEND ? passiveDefence() + activeDefence() : passiveDefence();
        }

        private double activeDefence() {
            double result;
            double outcome = Math.abs(Math.random() * 200.0) + defence();
            if (outcome <= 50) {
                result = 1;
            }
            else if (outcome <= 100) {
                result = 0.95;
            }
            else if (outcome <= 150) {
                result = 0.60;
            }
            else if (outcome < 200) {
                result = 0.25;
            }
            else {
                result = 0;
            }

            return result;
        }


        private double passiveDefence() {
            double result;
            double outcome = Math.random() * 200.0 + defence();
            if (outcome <= 120) {
                result = 1;
            }
            else if (outcome < 200) {
                result = 1 - outcome / 1000.0;
            }
            else {
                result = 0;
            }

            return result;
        }

        private double defence() {
            double result = 0;
            for (Equipment equipment: character.equipment()){
                result += equipment.armor();
            }
            return result;
        }

    }

}
