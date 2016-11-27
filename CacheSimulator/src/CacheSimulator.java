import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

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

    /**
     * Constructor.
     * @param args command line arguments
     */
    public CacheSimulator(String[] args) {
        arguments = new int[NUM_ARGS - 1];
        Scanner trace;
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
    }

    /**
     * Tests validity of arguments.
     * @param args int arguments
     */
    public void testValidity(int[] args) {
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
    public void testFirstTwo(int i) {
        if (i <= 0 || (i % 2 != 0 && i != 1)) {
            System.err.println("Invalid parameter.");
            System.exit(0);
        }
    }

    /**
     * Tests 3rd arg.
     * @param i int
     */
    public void testThird(int i) {
        if (i < 2 * 2 || i % 2 != 0) {
            System.err.println("Invalid parameter.");
            System.exit(0);
        }
    }

    /**
     * Tests last 3 args.
     * @param i int
     */
    public void testLastThree(int i) {
        if (i != 0 && i != 1) {
            System.err.println("Invalid parameter.");
            System.exit(0);
        }
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
        CacheSimulator cache = new CacheSimulator(args);
    }

}
