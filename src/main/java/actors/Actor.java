package actors;

public class Actor {

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

}
