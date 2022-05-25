package actors.characters;

import interactions.system.Command;
import interactions.system.Menu;

public class Enemy extends NPC{
	@Override
	public void interact(){
		Menu.instance().player().setInFocus(this);
		Menu.instance().addCommand(new Command("battle " + name(),"fight", Menu.instance().player()){{
			setIsStateChanger(true);
		}});
	}
}
