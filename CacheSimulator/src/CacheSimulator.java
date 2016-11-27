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
    private int[] arguments;
    private Scanner trace;
    private Map<Long, Map<Long, Boolean>> cache;
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
        this.testFirstTwo(arguments[NUM_SETS]);
        this.testFirstTwo(arguments[NUM_BLOCKS]);
        this.testThird(arguments[NUM_BYTES]);
        this.testLastThree(arguments[W_ALLOCATE]);
        this.testLastThree(arguments[W_THROUGH]);
        this.testLastThree(arguments[LEAST_RECENT]);
    }
    /**
     * Tests first 2 args.
     * @param i int
     */
    private void testFirstTwo(int i) {
        if (i <= 0 || (i % 2 != 0 && i != 1)) {
            System.err.println("Invalid parameter.");
            System.exit(0);
        }
    }
    /**
     * Tests 3rd arg.
     * @param i int
     */
    private void testThird(int i) {
        if (i < 2 * 2 || i % 2 != 0) {
            System.err.println("Invalid parameter.");
            System.exit(0);
        }
    }
    /**
     * Tests last 3 args.
     * @param i int
     */
    private void testLastThree(int i) {
        if (i != 0 && i != 1) {
            System.err.println("Invalid parameter.");
            System.exit(0);
        }
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
            System.err.println("Invalid trace file.");
            System.exit(0);
        }
        if (lineScan.hasNext()) {
            address = lineScan.next();
        } else {
            System.err.println("Invalid trace file.");
            System.exit(0);
        }
        if (lineScan.hasNext()) {
            lineScan.next();
        } else {
            System.err.println("Invalid trace file.");
            System.exit(0);
        }
        if (lineScan.hasNext()) {
            System.err.println("Invalid trace file.");
            System.exit(0);
        }
        lineScan.close();
        this.processTrace(command, address);
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
        System.out.println("Load: " + address);
        return;
    }
    /**
     * Store address.
     * @param address to store
     */
    private void store(String address) {
        System.out.println("Store: " + address);
        return;
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
