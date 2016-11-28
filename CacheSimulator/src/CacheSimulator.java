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

    //Size arguments array should be
    private static final int NUM_ARGS = 7;
    //Array index of each necessary parameter
    private static final int NUM_SETS = 0;
    private static final int NUM_BLOCKS = 1;
    private static final int NUM_BYTES = 2;
    private static final int W_ALLOCATE = 3;
    private static final int W_THROUGH = 4;
    private static final int LEAST_RECENT = 5;
    private static final int FILE = 6;
    //Bytes in an address
    private static final int ADDRESS_BYTES = 8;
    //Bytes in a word
    private static final int FOUR = 4;
    //Base for hex
    private static final int SIXTEEN = 16;
    //Cycles for memory load/store
    private static final int HUNDRED = 100;
    //All possible hex digits
    private static final String HEX = "0123456789abcdefABCDEF";
    //Scanner to read through trace file
    private Scanner trace;
    /*
     * Stores addresses in cache
     * First Long is set index
     * Inner map is a set
     * Second long is tag
     * Boolean is whether dirty bit
     */
    private Map<Long, Map<Long, Boolean>> cache;
    //Count info
    private int totalLoads = 0;
    private int totalStores = 0;
    private int loadHits = 0;
    private int loadMisses = 0;
    private int storeHits = 0;
    private int storeMisses = 0;
    private int totalCycles = 0;
    //Command line arguments
    private int numSets;
    private int numBlocks;
    private int numBytes;
    private int wAllocate;
    private int wThrough;
    private int leastRecent;
    /**
     * Constructor.
     * @param args command line arguments
     */
    public CacheSimulator(String[] args) {
        try {
            numSets = Integer.parseInt(args[NUM_SETS]);
            numBlocks = Integer.parseInt(args[NUM_BLOCKS]);
            numBytes = Integer.parseInt(args[NUM_BYTES]);
            wAllocate = Integer.parseInt(args[W_ALLOCATE]);
            wThrough = Integer.parseInt(args[W_THROUGH]);
            leastRecent = Integer.parseInt(args[LEAST_RECENT]);
            trace = new Scanner(new File(args[FILE]));
        } catch (NumberFormatException e) {
            System.err.println("Illegal number in argument.");
            System.exit(0);
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
            System.exit(0);
        }
        testValidity();
        boolean lru = false;
        if (leastRecent == 1) {
            lru = true;
        }
        cache = new HashMap<Long, Map<Long, Boolean>>();
        /*
         * Create the correct number of sets in the cache
         * Each set is a LinkedHashMap, which keeps track of either least-
         * recently-used or first entered.
         * lru determines whether to use least-recently-used or FIFO
         * removeEldestEntry is called after put, to see if the eldest value
         * must be removed. Eldest is either lru of FIFO
         * If the cache is overfilled and dirty bit is set, need 100 cycles to
         * write to memory
         */
        for (int i = 0; i < numSets; i++) {
            cache.put(Integer.toUnsignedLong(i),
                    new LinkedHashMap<Long, Boolean>(0, 1, lru) {
                    private static final long serialVersionUID = 1L;
                    protected boolean
                        removeEldestEntry(Map.Entry<Long, Boolean> eldest) {
                        boolean full = size() > numBlocks;
                        if (full) {
                            if (eldest.getValue()) {
                                totalCycles += (HUNDRED * numBytes / FOUR);
                            }
                        }
                        return full;
                    }
                }
            );
        }
    }
    /**
     * Tests validity of arguments.
     * @param args int arguments
     */
    private void testValidity() {
        testFirstTwo(numSets);
        testFirstTwo(numBlocks);
        testThird(numBytes);
        testLastThree(wAllocate);
        testLastThree(wThrough);
        testLastThree(leastRecent);
        if (wAllocate == 0 && wThrough == 0) {
            parameterError();
        }
    }
    /**
     * If power of 2, i in binary will be in form 1000..0.
     * So i - 1 will be in form 111...1.
     * So bitwise AND should result in 0 if i is power of 2.
     */
    private void testFirstTwo(int i) {
        if (i <= 0 || (i & (i - 1)) != 0) {
            parameterError();
        }
    }
    /**
     * Same as above.
     */
    private void testThird(int i) {
        if (i < FOUR || (i & (i - 1)) != 0) {
            parameterError();
        }
    }
    /**
     * Must be 0 or 1.
     */
    private void testLastThree(int i) {
        if (i != 0 && i != 1) {
            parameterError();
        }
    }
    /**
     * Exit program.
     */
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
            parseLine(line);
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
        //Checks if file itself is in proper format
        if (lineScan.hasNext()) {
            command = lineScan.next();
        } else {
            parseError();
        }
        if (lineScan.hasNext()) {
            address = lineScan.next();
        } else {
            parseError();
        }
        if (lineScan.hasNext()) {
            lineScan.next();
        } else {
            parseError();
        }
        if (lineScan.hasNext()) {
            parseError();
        }
        lineScan.close();
        processTrace(command, address);
    }
    /**
     * Exit program.
     */
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
            load(address);
        } else if (command.equals("s") || command.equals("S")) {
            store(address);
        } else {
            System.err.println("Invalid trace file.");
            System.exit(0);
        }
    }
    /**
     * Check address validity.
     * Increment number of loads and process tag with store unset.
     * @param address to load
     */
    private void load(String address) {
        //address = addressValidity(address);
        totalLoads++;
        processTag(address, false);
    }
    /**
     * Check address validity.
     * Increment number of stores and process tag with store set.
     * @param address to store
     */
    private void store(String address) {
        //address = addressValidity(address);
        totalStores++;
        processTag(address, true);
    }
    private void processTag(String address, boolean store) {
        address = addressValidity(address);
        Long decimal = hexToDec(address);
        decimal = decimal
                >> (int) (Math.log(numBytes) / Math.log(2));
        int setBits = (int) (Math.log(numSets) / Math.log(2));
        Long setBitsLong = Integer.toUnsignedLong((int)
                Math.pow(2, setBits) - 1);
        Long setIndex = decimal & setBitsLong;
        decimal = decimal >> setBits;
        if (!store) {
            loadCache(setIndex, decimal);
        } else {
            storeCache(setIndex, decimal);
        }
    }
    private String addressValidity(String address) {
        if (!address.substring(0, 2).equals("0x")
                && !address.substring(0, 2).equals("0X")) {
            parseError();
        }
        address = address.substring(2);
        if (address.length() != ADDRESS_BYTES) {
            parseError();
        }
        for (char c : address.toCharArray()) {
            if (HEX.indexOf(c) == -1) {
                parseError();
            }
        }
        return address;
    }
    private Long hexToDec(String hex) {
        Long decimal = Long.parseLong(hex, SIXTEEN);
        return decimal;
    }
    private void loadCache(Long setIndex, Long tag) {
        Map<Long, Boolean> currSet = cache.get(setIndex);
        if (currSet.containsKey(tag)) {
            loadHits++;
            totalCycles++;
            currSet.get(tag);
        } else {
            loadMisses++;
            totalCycles += (HUNDRED * numBytes / FOUR) + 1;
            currSet.put(tag, false);
            cache.put(setIndex, currSet);
        }
    }
    private void storeCache(Long setIndex, Long tag) {
        Map<Long, Boolean> currSet = cache.get(setIndex);
        if (currSet.containsKey(tag)) {
            storeHits++;
            totalCycles++;
            currSet.get(tag);
            if (wThrough == 1) {
                totalCycles += (HUNDRED * numBytes / FOUR);
            } else {
                currSet.remove(tag);
                currSet.put(tag, true);
                cache.put(setIndex, currSet);
            }
        } else {
            storeMisses++;
            if (wAllocate == 1) {
                totalCycles += (HUNDRED * numBytes / FOUR) + 1;
                currSet.put(tag, false);
                cache.put(setIndex, currSet);
                if (wThrough == 1) {
                    totalCycles += (HUNDRED * numBytes / FOUR);
                } else {
                    currSet.remove(tag);
                    currSet.put(tag, true);
                    cache.put(setIndex, currSet);
                }
            }
        }
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
        sim.printResults();
    }

}
