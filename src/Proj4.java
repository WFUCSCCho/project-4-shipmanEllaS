import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Proj4 {
    public static void main(String[] args) throws IOException {
        // Use command line arguments to specify the input file
        if (args.length != 2) {
            System.err.println("Usage: java Proj4 <input file> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        int numLines = Integer.parseInt(args[1]);

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line
        inputFileNameScanner.nextLine();

        // FINISH ME

        //PROCESS FILE----------------------------------------------------
        //Read file and fill out hashtable of datatype Villager
        ArrayList<Villager> villagers = new ArrayList<>();
        String currLine = null;
        for (int i=2; i <= numLines; i++) {
            currLine = inputFileNameScanner.nextLine();     //get next line in file
            String[] villInfo = null;
            if (!currLine.isEmpty()) {
                villInfo = currLine.split(",");
            }
            String[] shortInfo = new String[4];
            shortInfo[0] = villInfo[0];     //name
            shortInfo[1] = villInfo[3];     //personality
            shortInfo[2] = villInfo[4];     //hobby
            shortInfo[3] = villInfo[7];     //favorite song

            Villager v = null;
            try {
                v = new Villager(shortInfo[0], shortInfo[1], shortInfo[2], shortInfo[3]);
            } catch (ArrayIndexOutOfBoundsException e) {}

            if (shortInfo[1] != null) {         //add object to villagers list
                villagers.add(v);
            } else {
                System.out.println("insert failed - line " + i);
            }
        }

        System.out.print("-------------------------------------------------------------------------------------------\n");
        //Sorting methods used
        String sortedData = "";                               //sorted data metrics for given sorting method
        String reversedData = "";                             //reversed data metrics for given sorting method
        String shuffledData = "";                             //shuffled data metrics for given sorting method

        //Write sorted list to file-------------------------------------
        FileWriter sortedWriter = null;
        try {
            sortedWriter = new FileWriter("src/sorted.txt");
        }
        catch (IOException e) { System.out.println("FileNotFound!"); }

        //Creating and timing insertion, deletion, and search for hash table
        SeparateChainingHashTable<Villager> villagersHT = new SeparateChainingHashTable<>();
        //sorted
        Collections.sort(villagers);
        sortedData += addToHash(villagersHT, villagers) + ";";           //Insert
        sortedData += searchInHash(villagersHT, villagers) + ";";        //Search
        sortedData += removeFromHash(villagersHT, villagers);            //Remove
        villagersHT.makeEmpty();
        //shuffled
        Collections.shuffle(villagers);
        shuffledData += addToHash(villagersHT, villagers) + ";";         //Insert
        shuffledData += searchInHash(villagersHT, villagers) + ";";      //Search
        shuffledData += removeFromHash(villagersHT, villagers);          //Remove
        villagersHT.makeEmpty();
        //reversed
        Collections.sort(villagers, Collections.reverseOrder());
        reversedData += addToHash(villagersHT, villagers) + ";";         //Insert
        reversedData += searchInHash(villagersHT, villagers) + ";";      //Search
        reversedData += removeFromHash(villagersHT, villagers);          //Remove
        villagersHT.makeEmpty();

        //Print to console
        System.out.print("Number of lines read from dataset: " + numLines + "/392\n");
        System.out.println("Sorted dataset (Insert;Search;Remove):     " + sortedData);
        System.out.println("Reversed dataset (Insert;Search;Remove):   " + reversedData);
        System.out.println("Shuffled dataset (Insert;Search;Remove):   " + shuffledData);

        System.out.print("-------------------------------------------------------------------------------------------\n");

        //Write results in analysis.txt--------------------------------------------
        FileWriter analysisWriter = null;
        try {       //assume output.txt will be manually reset
            analysisWriter = new FileWriter("src/analysis.txt", true);
        }
        catch (IOException e) { System.out.println("FileNotFound!"); }

        //Append a line to analysis.txt with the information above, in CSV format
        //analysisWriter.write("Number of Lines,Sorted Data Insert;Search;Remove,Reversed Data Insert;Search;Remove,Shuffled Data Insert;Search;Remove\n");
        analysisWriter.write(numLines + "," +
                sortedData + "," + reversedData + "," + shuffledData + "\n");
        analysisWriter.flush();
        analysisWriter.close();

    }

    /**
     * Insert elements from arraylist into hash table.
     * @param ht       the hash table to manipulate.
     * @param arr      the arraylist used to populate.
     * @return the time taken to insert all elements.
     */
    public static long addToHash(SeparateChainingHashTable<Villager> ht, ArrayList<Villager> arr) {
        long timeTaken = System.nanoTime();
        for (int i = 0; i < arr.size(); i++) {
            ht.insert(arr.get(i));
        }
        return (System.nanoTime()-timeTaken);
    }

    /**
     * Search elements from arraylist in hash table.
     * @param ht       the hash table to manipulate.
     * @param arr      the arraylist used to populate.
     * @return the time taken to search all elements.
     */
    public static long searchInHash(SeparateChainingHashTable<Villager> ht, ArrayList<Villager> arr) {
        long timeTaken = System.nanoTime();
        for (int i = 0; i < arr.size(); i++) {      //Search
            if (!ht.contains(arr.get(i))) {
                System.out.println("ERROR: VILLAGER NOT FOUND");
            }
        }
        return (System.nanoTime()-timeTaken);
    }

    /**
     * Remove elements from hash table found in arraylist.
     * @param ht       the hash table to manipulate.
     * @param arr      the arraylist used to populate.
     * @return the time taken to remove all elements.
     */
    public static long removeFromHash(SeparateChainingHashTable<Villager> ht, ArrayList<Villager> arr) {
        long timeTaken = System.nanoTime();
        for (int i = 0; i < arr.size(); i++) {
            ht.remove(arr.get(i));
        }
        return (System.nanoTime()-timeTaken);
    }
}
