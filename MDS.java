/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p3driver;

import java.util.TreeMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.LinkedList;

/**
 * Starter code for P3
 *
 * @author
 */
// BXZ180000, KAC180002
//package dsa;
// If you want to create additional classes, place them in this file as subclasses of MDS
public class MDS {

    // Add fields of MDS here
    int id;
    int price;
    LinkedList<Integer> list;
    TreeMap<Integer, Integer> tree;
    HashMap<Integer, TreeMap<Integer, Integer>> table;

    // Constructors
    public MDS() {
        list = new LinkedList<>();
        tree = new TreeMap<>();
        table = new HashMap<>();
    }

    /* Public methods of MDS. Do not change their signatures.
       __________________________________________________________________
       a. Insert(id,price,list): insert a new item whose description is given
       in the list.  If an entry with the same id already exists, then its
       description and price are replaced by the new values, unless list
       is null or empty, in which case, just the price is updated. 
       Returns 1 if the item is new, and 0 otherwise.
     */
    public int insert(int id, int price, java.util.List<Integer> list) {

        if (tree.containsKey(id)) { //if id is found in tree, put id and price in tree
            //System.out.println(price);
            //System.out.println(tree.get(id));
           // if (price == tree.get(id)) {
           //     return 0;
           // }
            tree.put(id, price); //place id, price in tree
            if (!list.isEmpty()) {
                for (int x : table.keySet()) { //loop though and check if x is in table  and remove the id
                    table.get(x).remove(id);
                }
                for (int x : list) { //loop though list and check if x appears in it
                    TreeMap<Integer,Integer> newSet; //create newset list 
                    if (table.containsKey(x)) {
                        newSet = table.get(x); //set newset  to output of x in table
                    }
                    else{
                        
                        newSet = new TreeMap<>();//create newset list 
                        table.put(x, newSet); //put x and newset in table
                    }
                    if(newSet.containsKey(id)){ //is id is in new set..
                        newSet.put(id, newSet.get(id)+1); // put is, is+! from treemap, into newset
                    }
                    else{
                        newSet.put(id, 1); //put id and 1 inot newset if all else fails
                    }
                    
                }
            }
            return 0;
        } else {
            tree.put(id, price);// put id and price into tree 
            for (int x : list) {
                    TreeMap<Integer,Integer> newSet; //create new treemap called newset
                    if (table.containsKey(x)) { //if table has x in it, put id of x from table into newset
                        newSet = table.get(x);
                    }
                    else{
                        
                        newSet = new TreeMap<>(); //create newtreemap 
                        table.put(x, newSet); //put newset and x into table
                    }
                    if(newSet.containsKey(id)){
                        newSet.put(id, newSet.get(id)+1); //put newset+1 and id into newset again
                    }
                    else{
                        newSet.put(id, 1); //put id and 1 into newset if all else fails
                    }
                    
                }
        }
        return 1;
    }

    // b. Find(id): return price of item with given id (or 0, if not found).
    public int find(int id) {
        if (!tree.containsKey(id)) {
            return 0;
        }
        return (tree.get(id));
    }

    /* 
       c. Delete(id): delete item from storage.  Returns the sum of the
       ints that are in the description of the item deleted,
       or 0, if such an id did not exist.
     */
    public int delete(int id) {
        
        if (tree.containsKey(id)) { //if tree contains id, remove id and loop though table
            int total = 0;
            tree.remove(id);
            for (int x : table.keySet()) { //if int x is found in table, remove the id from table
                Integer freq = table.get(x).remove(id);
                if (freq != null) { //if freq is not null, then multipy by freq and at that to current total of freq
                    total += x*freq;
                    System.out.println(x);
                }
            }
            return total; //treurn total
        }
        return 0;
    }

    /* 
       d. FindMinPrice(n): given an integer, find items whose description
       contains that number (exact match with one of the ints in the
       item's description), and return lowest price of those items.
       Return 0 if there is no such item.
     */
    public int findMinPrice(int n) {
        if (table.containsKey(n)) {// if n is in table, loop though list and check if x is found in it
            int minprice = tree.get(table.get(n).firstKey());
            for (int x : table.get(n).keySet()) {//loop though table for int x and check
                if (tree.get(x) < minprice) {//if current x of tree is less than minpice, set minprice to current x of tree
                    minprice = tree.get(x);
                }
            }
            return minprice;// return minprice
        }
        return 0;
    }

    /* 
       e. FindMaxPrice(n): given an integer, find items whose description
       contains that number, and return highest price of those items.
       Return 0 if there is no such item.
     */
    public int findMaxPrice(int n) {
        if (table.containsKey(n)) {// if n is in table, loop though list and check if x is found in it
            int maxprice = 0;//tree.get(table.get(n).first());
            for (int x : table.get(n).keySet()) {//loop though table for int x and check
                if (tree.get(x) > maxprice) { //if current x of tree is greater than maxrice, set maxprice to current x of tree
                    maxprice = tree.get(x);
                }
            }
            return maxprice; //return maxprice
        }
        return 0;
    }

    /* 
       f. FindPriceRange(n,low,high): given int n, find the number
       of items whose description contains n, and in addition,
       their prices fall within the given range, [low, high].
     */
    public int findPriceRange(int n, int low, int high) {
        if (table.containsKey(n)) {// if n is in table, loop though list and check if x is found in it
            int count = 0;
            //TreeMap<Integer, Integer> newSet = table.get(n);
            for (int x : table.get(n).keySet()) { //loop though table for int x and check
                if (tree.get(x) >= low && tree.get(x) <= high) { //if the num is in price range, then increase count of x and return it
                    count++;
                }
            }
            return (count);
        }
        return 0;
    }

    /*
      g. RemoveNames(id, list): Remove elements of list from the description of id.
      It is possible that some of the items in the list are not in the
      id's description.  Return the sum of the numbers that are actually
      deleted from the description of id.  Return 0 if there is no such id.
     */
    public int removeNames(int id, java.util.List<Integer> list) {
        if (tree.containsKey(id)) { // if id is in tree, loop though list and check if x is found in ti
            int count = 0;
            if (list.isEmpty()) {
                return 0;
            }
            for (int x : list) {//loop though list and check if x is found in it
                //               if(!table.get(x).isEmpty()){
                TreeMap<Integer, Integer> newlist = table.get(x); //create new treemap, set to output of x in table
                if (newlist != null && newlist.remove(id) != null) { //is newlist.remove is not empty, then increase count and remove is
                    count += x;
                    //break;
                }
//                }
            }
            return count; //return count
        }
        return 0;
    }
}
