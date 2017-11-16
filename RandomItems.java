import java.util.*;
import java.util.ArrayList.*;

/* Program has a bunch of pools and pool lists
 * pools are made up of weighted items
 * weighted items are made of items and an int
 * items are either a pool or a namestring
 *
 * Pool lists are made up of pools. they can
 * generate concrete lists from their pools.
 * You can regenerate the whole list or parts
 * of the list.
 */




public class RandomItems {
    public static void main(String[] args) {
	TextGUI tg = new TextGUI();
	tg.start();
	
	Program prog = new Program();
	Item i = Item.getNameInstance("glaive");
	WeightedItem wi = new WeightedItem(i, 4);
	Pool p = new Pool("weapons");
	p.addWeightedItem(wi);
	prog.addPool("weapons", p);
	prog.printPool("weapons");
    }
}

class TextGUI {   
    Scanner sc = new Scanner(System.in);

    public void start() {
	boolean stayOpen = true;
	String ans;
	String options = "'1' add pool\n'2' add Item to pool\n'3' print pool\n'help' prints options\n'exit' closes program\n";
	print("type 'help' for options");
	
	while(stayOpen) {    	    
	    ans = sc.nextLine();
	    switch (ans) {
	    case "help": print(options);
		break;
	    case "exit": stayOpen = false;
		break;
	    case "1": print("one");
		break;
	    case "2": print("two");
		break;
	    case "3": print("three");
		break;		
	    }
	}

	exit();
    }
    
    private void print(String string) {
	System.out.println(string);
    }

    private void exit() {
	print("Shutting down");
    }
}

class Program {

    HashMap<String, Pool> pools = new HashMap<>();

    public void addPool(String name, Pool pool) {
	pools.put(name, pool);
    }

    public void printPool(String name) {
	Pool pool = pools.get(name);
	for(WeightedItem wi: pool.getWeightedItems()) {
	    System.out.println(wi.getName());
	}
    }
}

class Pool {
    String name;
    private ArrayList<WeightedItem> weightedItems = new ArrayList<>();
    
    public Pool(String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public ArrayList<WeightedItem> getWeightedItems() {
	return weightedItems;
    }
    
    public void addWeightedItem(WeightedItem weightedItem) {
	weightedItems.add(weightedItem);
    }
}

class WeightedItem {
   
    public int weight;
    public Item item;

    public WeightedItem(Item item, int weight) {
	this.item = item;
	this.weight = weight;
    }

    public String getName() {
	return item.name;
    }
}

class Item {
    public String name;
    public Pool pool;

    private Item(String name) {
	this.name = name;
    }

    private Item(Pool pool) {
	this.pool = pool;
    }

    public static Item getNameInstance(String name) {
	return new Item(name);
    }

    public static Item getPoolInstance(Pool pool) {
	return new Item(pool);
    }
}
