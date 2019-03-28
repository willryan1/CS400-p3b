//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           P3a
// Files:           HashTable.java,HashTableTest.java 
//
// Course:          CS 400, Spring Term 2019
//
// Author:          Will Ryan
// Email:           wrryan@wisc.edu
// Lecturer's Name: Debra Deppeler
//
///////////////////////////////////////////////////////////////////////////////

import static org.junit.jupiter.api.Assertions.*; // org.junit.Assert.*; 
import org.junit.jupiter.api.Assertions;
 
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
 
import java.util.Random;



/**
 * 
 * @author Will Ryan
 *
 * Tests the implementation of the hashtable
 *
 */
public class HashTableTest{

    // TODO: add other fields that will be used by multiple tests
    
    // TODO: add code that runs before each test method
    @Before
    public void setUp() throws Exception {

    }

    // TODO: add code that runs after each test method
    @After
    public void tearDown() throws Exception {

    }
    
    /** 
     * Tests that a HashTable returns an integer code
     * indicating which collision resolution strategy 
     * is used.
     * REFER TO HashTableADT for valid collision scheme codes.
     */
    @Test
    public void test000_collision_scheme() {
        HashTableADT htIntegerKey = new HashTable<Integer,String>();
        int scheme = htIntegerKey.getCollisionResolution();
        if (scheme < 1 || scheme > 9) 
            fail("collision resolution must be indicated with 1-9");
    }
        
    /** IMPLEMENTED AS EXAMPLE FOR YOU
     * Tests that insert(null,null) throws IllegalNullKeyException
     */
    @Test
    public void test001_IllegalNullKey() {
        try {
            HashTableADT htIntegerKey = new HashTable<Integer,String>();
            htIntegerKey.insert(null, null);
            fail("should not be able to insert null key");
        } 
        catch (IllegalNullKeyException e) { /* expected */ } 
        catch (Exception e) {
            fail("insert null key should not throw exception "+e.getClass().getName());
        }
    }
    
    @Test
    public void test002_test_insert() {
    	try {
    		HashTableADT table = new HashTable<Integer,String>();
            table.insert("hello", "1st");
            if(!table.get("hello").equals("1st")) {
            	fail("insert or get worked improperly");
            }
    	}catch(Exception e) {
    		fail("Unknown exception thrown");
    	}
    }
    
    @Test
    public void test003_test_insert_remove() {
    	try {
    		HashTableADT table = new HashTable<Integer,String>();
            table.insert("hello", "1st");
            table.remove("hello");
            table.get("hello");
    	}catch(KeyNotFoundException e) {}
    	catch(Exception e) {
    		fail("Unknown exception thrown");
    	}
    }
    
    @Test
    public void test004_test_wrong_get() {
    	try {
    		HashTableADT table = new HashTable<Integer,String>();
            table.insert("hello", "1st");
            table.get("b");
    	}catch(KeyNotFoundException e) {}
    	catch(Exception e) {
    		fail("Unknown exception thrown");
    	}
    }
    
    @Test
    public void test005_test_many_input() {
    	try {
    		HashTableADT table = new HashTable<Integer,String>();
            for(int i = 0; i < 20; i++) {
            	table.insert("insert" + i, "e" + i);
            }
    	}
    	catch(Exception e) {
    		fail("Unknown exception thrown");
    	}
    }
    
    @Test
    public void test006_insert_many_keys_and_remove() {
    	try {
    		HashTableADT table = new HashTable<Integer,String>();
            for(int i = 0; i < 50; i++) {
            	table.insert("insert" + i, "e" + i);
            }
            table.remove("insert5");
            table.remove("insert3");
            table.remove("insert22");
    	}
    	catch(Exception e) {
    		fail("Unknown exception thrown");
    	}
    }
    
    @Test
    public void test007_insert_and_check_get_capacity() {
    	try {
    		HashTableADT table = new HashTable<Integer,String>(12,.75);
            for(int i = 0; i < 3; i++) {
            	table.insert("insert" + i, "e" + i);
            }
            if(table.getCapacity()!= 12) {
            	fail("capacity did not work properly");
            }
    	}
    	catch(Exception e) {
    		fail("Unknown exception thrown");
    	}
    }
    
    @Test
    public void test008_test_num_keys() {
    	try {
    		HashTableADT table = new HashTable<Integer,String>();
            for(int i = 0; i < 50; i++) {
            	table.insert("insert" + i, "e" + i);
            }
            table.remove("insert5");
            if(table.numKeys() != 50) {
            	fail("numKeys() did not work properly");
            }
    	}
    	catch(Exception e) {
    		fail("Unknown exception thrown");
    	}
    }
    
    @Test
    public void test009_test_amount_of_inserts() {
    	try {
    		HashTableADT table = new HashTable<Integer,String>();
            for(int i = 0; i < 100; i++) {
            	table.insert("insert" + i, "e" + i);
            }
            if(table.numKeys() != 100) {
            	fail("numKeys() did not work properly");
            }
    	}
    	catch(Exception e) {
    		fail("Unknown exception thrown");
    	}
    }
    
    
    
    // TODO add your own tests of your implementation
    
}
