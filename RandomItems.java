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
    private Pool workingPool;
    
    private PoolParser(String baseDirectory) {
	loadSavedPoolsInBaseDir(baseDirectory);
    }

    public static PoolParser getInstanceFromBaseDir(String baseDirectory) {
	return new PoolParser(baseDirectory);
    }

    public HashMap<String, Pool> getPools() {
	return pools;
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
	     BufferedReader br = new BufferedReader(fr);)
	    {
	    String line = reader.readLine();
	    Item item = Item.getNonWeightedInstance(file.getName(),-1);
	    processTreeData(br, item);
	    pools.add(workingPool.getName(), workingPool);
	} catch(Exception ex) {
	    print(ex.getMessage());
	    ex.printStackTrace();
	}
	return new Pool("name");
    }

    private Item processTreeData(BufferedReader reader, WeightedItem previousItem) {
	String line = reader.readLine();
	WeightedItem currentItem;
	int previousSubLevel;
	int currentSubLevel;
	
	currentItem = parseStringToItem(line);
	previousSubLevel = previousItem.getSubLevel();
	currentSubLevel = currentItem.getSubLevel();
	while (currentSubLevel > previousSubLevel) {
	    previousItem.addItem(currentItem);
	    currentItem = ProcessTreeData(reader, currentItem); // recurse
	}
	return currentItem;	
    }

    private boolean isInvalidItemString(String line) {
	if (line == null || line.equals(""))
	    return true;
	return false;
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

    private Item parseStringToItem(String poolLine) {
	String[] tokens;
	String name;
	int weight;
	int subLevel;
	Item item;
	
	if (isInvalidItemString(itemString)){
	    return Item.getNonWeightedItem("",0,-9999);
	} else {
	    tokens = poolLine.split(",");
	    name = tokens[0];
	    subLevel = getSubLevel(poolLine);
	}
	if (tokens.length > 1) {
	    weight = Integer.parseInt(tokens[1]);
	    item = Item.getWeightedInstance(name, weight, subLevel);
	} else {
	    item = Item.getNonWeightedInstance(name, subLevel);
	}
	return item;
    }

    private int getSubLevel(String itemString) {
	int leadingWhiteSpaces;
	leadingWhiteSpaces = itemString.length - StringUtil.LeftTrim(itemString);
	return (whiteSpaces/2);
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

class Item {
    private String name;
    private String weight;
    private int subLevel;
    
    private ArrayList<Item> itemList =
	new ArrayList<>();
    
    private public Item(
	String name,
	int weight,
	int subLevel)
    {
	this.name = name;
	this.weight = weight;
	this.subLevel = subLevel;
    }

    public static Item getWeightedInstance(
	String name,
	int weight,
	int subLevel)
    {
	return new Item(name, weight, subLevel);
    }

    public static Item getNonWeightedInstance(
	String name,
	int subLevel)
    {
	return new Item(name, null, subLevel);
    }
    
    public String getName() {
	return item.name;
    }

    public addItem(Item item) {
	itemList.add(item);
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

class StringUtil {
    public static String leftTrim(String s) {
	int i = 0;
	while (i < s.length() && Character.isWhitespace(s.charAt(i))) {
	    i++;
	}
	return s.substring(i);
    }
}
