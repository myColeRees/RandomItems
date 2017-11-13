import java.util.*;
import java.util.ArrayList.*;

public class RandomItems {
    public static void main(String[] args) {

	Program p = new Program();
	p.newPool("Weapons");
	p.addItemToPool("Weapons", "Sword");
	p.addItemToPool("Weapons", "Halberd");
	p.addItemToPool("Weapons", "Glaive");

	p.newPool("Monsters");
	p.addItemToPool("Monsters", "Goblin");
	p.addItemToPool("Monsters", "Gremlin");
	p.addItemToPool("Monsters", "Ken");

	p.printPool("Monsters");
	p.printPool("Weapons");
    }
}

class Program {
    HashMap<String, ArrayList<Item>> pools = new HashMap<>();;
    ArrayList<Item> pool;

    public void newPool(String poolName) {
	pool = new ArrayList<Item>();
	pools.put(poolName, pool);
    }

    public void addItemToPool(String poolName, Item item) {
	pool = pools.get(poolName);
	pool.add(item);
    }

    public void printPool(String poolName) {
	pool = pools.get(poolName);
	System.out.println(pool);
    }
}

class Item {
    
    public HashMap<String, Item> attributes;
    public int weight;
    public String name;

    public Item(String name, int weight) {
	this.name = name;
	this.weight = weight;
    }
}

class PoolComposite {

    ArrayList<Item> poolComposite = new ArrayList<>();
    
    public void addItemFromPool(String poolName) {
	
    }

    private void getWeightedItem(String poolName) {

    }

    private int chooseIndexFromWeights(int[] weights) {
	
    }
}
