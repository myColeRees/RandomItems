import java.util.*;
import java.util.ArrayList.*;
import java.io.*;
import java.util.regex.*;

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

	Program program = new Program();
	//	Item i = Item.getNameInstance("glaive");
	//	WeightedItem wi9 = new WeightedItem(i9, 3);
	//	Pool p = new Pool("weapons");
	//	p.addWeightedItem(wi9);
	//	prog.addPool("weapons", p);

	TextGUI textGUI = new TextGUI(program);
	textGUI.start();	
    }
}

class TextGUI {   
    Program program;
    Scanner sc = new Scanner(System.in);
    
    TextGUI(Program program) {
	this.program = program;
    }
    
    public void start() {
	boolean stayOpen = true;
	String userInput;
	String helpText = ProgramVariables.HELP_TEXT;
	program.initialize();
	print("type 'help' for options");
	while(stayOpen) {    	    
	    ans = sc.nextLine();
	    switch (userInput) {
	    case "help": print(helpText);
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
    
    private void exit() {
	print("Shutting down");
    }

    private void print(String string) {
	System.out.println(string);
    }
}

class Program {
    HashMap<String, Pool> pools = new HashMap<>();

    public void initialize() {
	loadSavedPools();
    }
    
    public void addPool(String name, Pool pool) {
	pools.put(name, pool);
    }

    public void printPool(String name) {
	Pool pool = pools.get(name);
	for(WeightedItem wi: pool.getWeightedItems()) {
	    System.out.println(wi.getName());
	}
    }

    private void loadSavedPools() {
	String poolPath;
	File[] files;
	
	poolPath = getPoolPath();
	files = new File(poolPath).listFiles();
	loadPoolsFromFiles(files);
    }

    private String getPoolPath() {
	File file = new File("");
	String basePath = file.getAbsolutePath();
	return basePath + "\\pools";
    }

    private void loadPoolsFromFiles(File[] files) {
	for (File file : files) {
	    if (file.isDirectory()) {
		print("Directory: " + file.getName());
		loadPoolsFromFiles(file.listFiles()); // recursion
	    } else {
		print("File: " + file.getName());
		parseFileToPool(file);
	    }
	}	
    }

    private Pool parseFileToPool(File file) {
	try (FileReader fr = new FileReader(file);
	     BufferedReader br = new BufferedReader(fr);) {
	    String line;
	    String poolName;
	    String itemName;
	    int weight;

	    poolName = file.getName();
	    while((line = br.readLine()) != null) {
		print(line);
		if(isValidItemString(line)) {
		    print("valid item");
		} else if(isValidPoolString(line)) {
		    print("valid pool");
		}
	    }
	} catch(Exception ex) {
	    print(ex.getMessage());
	    ex.printStackTrace();
	}
	return new Pool("name");
    }

    private boolean isValidItemString(String poolLine) {
	String[] tokens;
	
	tokens = poolLine.split(",");

	if(tokens.length != 2)
	    return false;
		
	return true;
    }

    private boolean isValidPoolString(String poolLine) {

	String[] tokens;
	String extension;

	extension = getFileExtension(poolLine);
	if(!extension.equals("pool"))
	    return false;
	return true;
    }

    private String getFileExtension (String fileName) {
	String extension;
	String[] tokens = fileName.split(Pattern.quote("."));

	if (tokens.length < 2)
	    return "";
	return tokens[tokens.length - 1];	
    }
    
    private void print(String string) {
	System.out.println(string);
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

class ProgramVariables {
    public static final String HELP_TEXT =
	"'1' create pool\n" +
	"'2' add Item to pool\n" +
	"'3' list pools\n" +
	"'4' create new RandomList\n" +
	"'5' add item to RandomList\n" +
	"'6' print RandomList\n" +
	"'7' randomize RandomList\n" +
	"'8' randomize single item\n" +
	"'help' prints options\n" +
	"'exit' closes program\n";
}
