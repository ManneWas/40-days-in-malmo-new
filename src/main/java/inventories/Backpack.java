package inventories;

import actors.items.Item;
import dev.morphia.annotations.Entity;
import interactions.system.Command;
import interactions.system.Menu;

@Entity
public class Backpack extends Inventory {

	@SuppressWarnings("FieldMayBeFinal")
	private int size = 20;

	public Backpack()
	{
	}

	public boolean spaceCheck(Item item)
	{
		boolean result;
		if (item == null) {
			result = false;
		}
		else {result = item.size() <= this.currentSpace();}
		return result;
	}

	public int size()
	{
		return size;
	}

	public int currentSpace()
	{
		int size = this.size;
		for (Item item : items) {
			size -= item.size();
		}
		return size;
	}

	public void addDefaultCommands()
	{
		Menu.instance().addCommand(new Command("open backpack", "display", this));
	}

	public void display()
	{
		System.out.println("Items in backpack: ");
		items.forEach(System.out::println);
	}

	@Override
	public String toString()
	{

		StringBuilder sb = new StringBuilder();
		sb.append("Items in backpack: \r\n");

		for (Item item : items) {
			sb.append(item.toString());
			sb.append("\r\n");
		}
		return sb.toString();
	}

}








