/**********************************************************************************************
 * @file : SeparateChainingHashTable.java
 * @description : Separate chaining hash table class for generic type AnyType.
 * @author : Ella Shipman
 * @date : December 3, 2025
 *********************************************************************************************/

import java.util.LinkedList;
import java.util.List;

// SeparateChaining Hash table class
//
// CONSTRUCTION: an approximate initial size or default of 101
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// boolean contains( x )  --> Return true if x is present
// void makeEmpty( )      --> Remove all items

public class SeparateChainingHashTable<AnyType> {
    /**
     * Construct the hash table.
     */
    public SeparateChainingHashTable() {
        this(DEFAULT_TABLE_SIZE);
    }

    /**
     * Construct the hash table.
     *
     * @param size approximate table size.
     */
    public SeparateChainingHashTable(int size) {
        theLists = new LinkedList[nextPrime(size)];
        for (int i = 0; i < theLists.length; i++)
            theLists[i] = new LinkedList<>();
    }

    /**
     * Insert into the hash table. If the item is
     * already present, then do nothing. Rehash if
     * the insertion exceeds the table size.
     *
     * @param x the item to insert.
     */
    public void insert(AnyType x) {
        int key = hash(x.toString(), theLists.length);      //may need to be length-1, check
        if (key >= theLists.length) {        //if key greater than table size, rehash to next prime
            rehash();
            //System.out.println("rehash");
            insert(x);
        } else {                            //else, go ahead and add to head of list :)
            theLists[key].add(x); //IS ADD THE CORRECT KEYWORD?
            //System.out.println("insert " + x);
        }
    }

    /**
     * Remove from the hash table.
     *
     * @param x the item to remove.
     */
    public void remove(AnyType x) {
        if (!contains(x)) {         //if not in the table, can't remove !
            //System.out.println("invalid remove " + x);
            return;
        } else {
            int key = hash(x.toString(), theLists.length);      //may need to be length-1, check
            theLists[key].remove(x);                            //IS REMOVE THE CORRECT KEYWORD?
            //System.out.println("remove " + x);
        }
    }

    /**
     * Find an item in the hash table.
     *
     * @param x the item to search for.
     * @return true if x is not found.
     */
    public boolean contains(AnyType x) {
        int key = hash(x.toString(), theLists.length);      //may need to be length-1, check
        if (key >= theLists.length) {        //if key greater than table size, false
            return false;
        } else if (theLists[key].contains(x)) {        //found in list, true
            return true;
        } else {                                        //otherwise, false
            return false;
        }
    }

    /**
     * Make the hash table logically empty.
     */
    public void makeEmpty() {
        for (int i = 0; i < theLists.length; i++) {
            theLists[i].clear();                        //is this. what he means? lol
        }
    }

    /**
     * A hash routine for String objects.
     *
     * @param key       the String to hash.
     * @param tableSize the size of the hash table.
     * @return the hash value.
     */
    public static int hash(String key, int tableSize) {
        int hashVal = 0;

        for (int i = 0; i < key.length(); i++)
            hashVal = 37 * hashVal + key.charAt(i);

        hashVal %= tableSize;
        if (hashVal < 0)
            hashVal += tableSize;

        return hashVal;
    }

    private void rehash() {
        //Create new table
        List<AnyType>[] theNewLists = new LinkedList[nextPrime(theLists.length)];
        //Copy over lists from original table
        for (int i = 0; i < theLists.length; i++) {
            theNewLists[i] = theLists[i];
        }
        //Add new lists to table
        for (int i = theLists.length; i < theNewLists.length; i++) {
            theNewLists[i] = new LinkedList<>();
        }
        //Assign new list as current hash table
        theLists = theNewLists;
    }

    private int myhash(AnyType x) {
        int hashVal = x.hashCode();

        hashVal %= theLists.length;
        if (hashVal < 0)
            hashVal += theLists.length;

        return hashVal;
    }

    private static final int DEFAULT_TABLE_SIZE = 101;

    /**
     * The array of Lists.
     */
    private List<AnyType>[] theLists;
    private int currentSize;

    /**
     * Internal method to find a prime number at least as large as n.
     *
     * @param n the starting number (must be positive).
     * @return a prime number larger than or equal to n.
     */
    private static int nextPrime(int n) {
        if (n % 2 == 0)
            n++;

        for (; !isPrime(n); n += 2)
            ;

        return n;
    }

    /**
     * Internal method to test if a number is prime.
     * Not an efficient algorithm.
     *
     * @param n the number to test.
     * @return the result of the test.
     */
    private static boolean isPrime(int n) {
        if (n == 2 || n == 3)
            return true;

        if (n == 1 || n % 2 == 0)
            return false;

        for (int i = 3; i * i <= n; i += 2)
            if (n % i == 0)
                return false;

        return true;
    }

}


