package actors;

public class Actor implements interactable{

    String name;
    protected int level;

    public Actor setName(final String name) {
        this.name = name;
        return this;
    }


    public String name() {
        return name != null ? name : "Unknown";
    }
    public int level() {
        return level;
    }


    @Override
    public void interact() {
        System.out.printf("Intreacting with %s %n", name());
    }



}
