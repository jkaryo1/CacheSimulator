import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author Jon Karyo jkaryo1
 */
public class CacheSimulator {

    private static final int NUM_ARGS = 7;
    private static final int NUM_SETS = 0;
    private static final int NUM_BLOCKS = 1;
    private static final int NUM_BYTES = 2;
    private static final int W_ALLOCATE = 3;
    private static final int W_THROUGH = 4;
    private static final int LEAST_RECENT = 5;
    private static final int FILE = 6;
    private static final int ADDRESS_BYTES = 8;
    private static final int SIXTEEN = 16;
    private static final String HEX = "0123456789abcdefABCDEF";
    private int[] arguments;
    private Scanner trace;
    private Map<Long, Map<Long, Boolean>> cache;
    private int totalLoads = 0;
    private int totalStores = 0;
    private int loadHits = 0;
    private int loadMisses = 0;
    private int storeHits = 0;
    private int storeMisses = 0;
    private int totalCycles = 0;
    /**
     * Constructor.
     * @param args command line arguments
     */
    public CacheSimulator(String[] args) {
        arguments = new int[NUM_ARGS - 1];
        try {
            arguments[NUM_SETS] = Integer.parseInt(args[NUM_SETS]);
            arguments[NUM_BLOCKS] = Integer.parseInt(args[NUM_BLOCKS]);
            arguments[NUM_BYTES] = Integer.parseInt(args[NUM_BYTES]);
            arguments[W_ALLOCATE] = Integer.parseInt(args[W_ALLOCATE]);
            arguments[W_THROUGH] = Integer.parseInt(args[W_THROUGH]);
            arguments[LEAST_RECENT] = Integer.parseInt(args[LEAST_RECENT]);
            trace = new Scanner(new File(args[FILE]));
        } catch (NumberFormatException e) {
            System.err.println("Illegal number in argument.");
            System.exit(0);
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
            System.exit(0);
        }
        this.testValidity(arguments);
        cache = new HashMap<Long, Map<Long, Boolean>>();
        for (int i = 0; i < arguments[NUM_SETS]; i++) {
            cache.put(Integer.toUnsignedLong(i),
                    new LinkedHashMap<Long, Boolean>());
        }
    }
    /**
     * Tests validity of arguments.
     * @param args int arguments
     */
    private void testValidity(int[] args) {
        this.testFirstTwo(this.numSets());
        this.testFirstTwo(this.numBlocks());
        this.testThird(this.numBytes());
        this.testLastThree(this.wAllocate());
        this.testLastThree(this.wThrough());
        this.testLastThree(this.leastRecent());
        if (this.wAllocate() == 0 && this.wThrough() == 0) {
            this.parameterError();
        }
    }
    /**
     * Number of sets.
     * @return numSets
     */
    public int numSets() {
        return arguments[NUM_SETS];
    }
    /**
     * Number of blocks.
     * @return numBlocks
     */
    public int numBlocks() {
        return arguments[NUM_BLOCKS];
    }
    /**
     * Number of bytes.
     * @return numBytes
     */
    public int numBytes() {
        return arguments[NUM_BYTES];
    }
    /**
     * Write allocate or not.
     * @return wAllocate
     */
    public int wAllocate() {
        return arguments[W_ALLOCATE];
    }
    /**
     * Write-through or write-back.
     * @return wThrough
     */
    public int wThrough() {
        return arguments[W_THROUGH];
    }
    /**
     * Least-recently-used or FIFO.
     * @return leastRecent
     */
    public int leastRecent() {
        return arguments[LEAST_RECENT];
    }
    /**
     * Tests first 2 args.
     * @param i int
     */
    private void testFirstTwo(int i) {
        if (i <= 0 || (i % 2 != 0 && i != 1)) {
            this.parameterError();
        }
    }
    /**
     * Tests 3rd arg.
     * @param i int
     */
    private void testThird(int i) {
        if (i < 2 * 2 || i % 2 != 0) {
            this.parameterError();
        }
    }
    /**
     * Tests last 3 args.
     * @param i int
     */
    private void testLastThree(int i) {
        if (i != 0 && i != 1) {
            this.parameterError();
        }
    }
    private void parameterError() {
        System.err.println("Invalid parameter.");
        System.exit(0);
    }
    /**
     * Scans through the trace file acting accordingly.
     */
    public void lineByLine() {
        while (trace.hasNextLine()) {
            String line = trace.nextLine();
            this.parseLine(line);
        }
    }
    /**
     * Parses each individual line.
     * @param line line to parse
     */
    private void parseLine(String line) {
        Scanner lineScan = new Scanner(line);
        String command = "";
        String address = "";
        if (lineScan.hasNext()) {
            command = lineScan.next();
        } else {
            this.parseError();
        }
        if (lineScan.hasNext()) {
            address = lineScan.next();
        } else {
            this.parseError();
        }
        if (lineScan.hasNext()) {
            lineScan.next();
        } else {
            this.parseError();
        }
        if (lineScan.hasNext()) {
            this.parseError();
        }
        lineScan.close();
        this.processTrace(command, address);
    }
    private void parseError() {
        System.err.println("Invalid trace file.");
        System.exit(0);
    }
    /**
     * Processes whether to load or store.
     * @param command load or store
     * @param address the address
     */
    private void processTrace(String command, String address) {
        if (command.equals("l") || command.equals("L")) {
            this.load(address);
        } else if (command.equals("s") || command.equals("S")) {
            this.store(address);
        } else {
            System.err.println("Invalid trace file.");
            System.exit(0);
        }
    }
    /**
     * Load address.
     * @param address to load
     */
    private void load(String address) {
        this.addressValidity(address);
        this.totalLoads++;
        this.processTag(address, false);
    }
    /**
     * Store address.
     * @param address to store
     */
    private void store(String address) {
        this.addressValidity(address);
        this.totalStores++;
        this.processTag(address, true);
    }
    private void processTag(String address, boolean store) {
        Long decimal = this.hexToDec(address);
        decimal = decimal
                >> (int) Math.ceil(Math.log(this.numBytes()) / Math.log(2));
        int setBits = (int) Math.ceil(Math.log(this.numSets()) / Math.log(2));
        Long setBitsLong = Integer.toUnsignedLong((int)
                Math.pow(setBits, 2) - 1);
        Long setIndex = decimal & setBitsLong;
        decimal = decimal >> setBits;
        if (!store) {
            this.accessCache(setIndex, decimal);
        }
    }
    private void addressValidity(String address) {
        if (!address.substring(0, 2).equals("0x")
                && !address.substring(0, 2).equals("0X")) {
            this.parseError();
        }
        address = address.substring(2);
        if (address.length() != ADDRESS_BYTES) {
            this.parseError();
        }
        for (char c : address.toCharArray()) {
            if (HEX.indexOf(c) == -1) {
                this.parseError();
            }
        }
    }
    private Long hexToDec(String hex) {
        Long decimal = Long.parseLong(hex, SIXTEEN);
        return decimal;
    }
    private void accessCache(Long setIndex, Long tag) {
        return;
    }


    /**
     * Prints results.
     */
    public void printResults() {
        System.out.println("Total loads: " + totalLoads);
        System.out.println("Total stores: " + totalStores);
        System.out.println("Load hits: " + loadHits);
        System.out.println("Load misses: " + loadMisses);
        System.out.println("Store hits: " + storeHits);
        System.out.println("Store misses: " + storeMisses);
        System.out.println("Total cycles: " + totalCycles);
    }
    /**
     * Main method.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            if (args.length != NUM_ARGS) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Incorrect number of arguments.");
            System.exit(0);
        }
        CacheSimulator sim = new CacheSimulator(args);
        sim.lineByLine();
    }

}
