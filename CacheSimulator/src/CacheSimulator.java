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
        arguments = new int[NUM_ARGS];
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
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
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
        }
        CacheSimulator cache = new CacheSimulator(args);
    }

}
