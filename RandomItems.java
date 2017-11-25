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
    boolean stayOpen = true;
    String userInput;
    String helpText = ProgramVariables.HELP_TEXT;
    
    TextGUI(Program program) {
	this.program = program;
    }
    
    public void start() {
	program.initialize();
	print(ProgramVariables.START_TEXT);
	while(stayOpen) {    	    
	    runLoop();
	}
	exit();
    }

    private void runLoop() {
	userInput = sc.nextLine();
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
	String poolPath = getPoolPath();
	PoolParser poolParser =
	    PoolParser.getInstanceFromBaseDir(poolPath);
    }

    private String getPoolPath() {
	File file = new File("");
	String basePath = file.getAbsolutePath();
	return basePath + "\\pools";
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
}

class PoolParser {

    HashMap<String, Pool> pools = new HashMap<>();
    
    private PoolParser(String baseDirectory) {
	loadSavedPoolsInBaseDir(baseDirectory);
    }

    public static PoolParser getInstanceFromBaseDir(String baseDirectory) {
	return new PoolParser(baseDirectory);
    }

    private void loadSavedPoolsInBaseDir(String baseDirectory) {
	File[] files;
	
	files = new File(baseDirectory).listFiles();
	loadPoolsFromFiles(files);
    }
    
    private void loadPoolsFromFiles(File[] files) {
	for (File file : files) {
	    if (file.isDirectory()) {
		loadPoolsFromFiles(file.listFiles()); // recursion
	    } else {
		if(isValidPoolString(file.getName()))
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
	    Item item;
	    
	    poolName = file.getName(); // todo remove .pool from string
	    while((line = br.readLine()) != null) {
		if(isValidItemString(line)) {
		    item = parseToItem(line);
		    // add item to pool.
		} else if(isValidPoolString(line)) {
		    // add pool name to a list to check for existence at the end.
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

    private Item parseToItem(String poolLine) {
	
	return new Item("hey"); // todo
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
    private ArrayList<WeightedItem> weightedItems =
	new ArrayList<>();
    
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

    // could have arrayList of items and pools..
    // consider using a Tree data structure
    // since this is a tree application........
    
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

    public Item(String name) {
	this.name = name;
    }    
}

class ProgramVariables {
    public static final String START_TEXT =
	"Welcome to RandomItems!\n" +
	"Type 'help' to see options";

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
