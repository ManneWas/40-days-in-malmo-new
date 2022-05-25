package actors.items;

import actors.Actor;
import interactions.system.Command;
import interactions.system.Menu;
import inventories.Backpack;

public class Item extends Actor {

	private int size;

	public Item()
	{
	}

	public int size()
	{
		return size;
	}

	public Item setSize(int size)
	{
		this.size = size;
		return this;
	}

	@Override
	public void interact()
	{
		Command addToInventory = new Command("pick up " + name(), "addToInventory", this);
		Menu.instance().addCommand(addToInventory);

	}

	public void pickUp()
	{

	}

	public void addToInventory()
	{
		Backpack backpack = Menu.instance().player().backpack();
		if (backpack.spaceCheck(this)) {
			backpack.add(this);
			Menu.instance().removeCommand("pick up " + name());
		}
		else {
			System.out.printf("%s is too large for your backpack, please empty at least %d slots", name(),
			                  size() - backpack.currentSpace());
		}
	}

	@Override
	public String toString(){
		return name();
	}

}
