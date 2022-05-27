package dialogue;

import interactions.system.*;

import java.util.List;

public class Dialogue {
    List<Dialogue> outcomes;
    public final String signature;
    private String text;
    public Dialogue(String signature) {
        this.signature = signature;

    }

    public void build() {
        for (Dialogue dialogue : outcomes) {
            Menu.instance().addCommand(new Command(dialogue.signature(),"outcome",dialogue));
        }
    }

    public void outcome(){
        System.out.println(text);
        this.build();
    }

    public String signature() {
        return signature;
    }
}