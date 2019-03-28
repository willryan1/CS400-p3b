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
import java.util.ArrayList;
// TODO: comment and complete your HashTableADT implementation
// DO ADD UNIMPLEMENTED PUBLIC METHODS FROM HashTableADT and DataStructureADT TO YOUR CLASS
// DO IMPLEMENT THE PUBLIC CONSTRUCTORS STARTED
// DO NOT ADD OTHER PUBLIC MEMBERS (fields or methods) TO YOUR CLASS
//
// TODO: implement all required methods
//
// TODO: describe the collision resolution scheme you have chosen
// identify your scheme as open addressing or bucket
//
// TODO: explain your hashing algorithm here 
// NOTE: you are not required to design your own algorithm for hashing,
//       since you do not know the type for K,
//       you must use the hashCode provided by the <K key> object
//       and one of the techniques presented in lecture
//

/**
 * 
 * @author Will Ryan
 *
 *Implements a hashtable and implements the HashTableADT interface
 *
 * @param <K>
 * @param <V>
 */
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {
	
	private Object[] keys;
	private Object[] values;
	private HashNode<K,V>[] hashNode;
	private int initialCapacity;
	private double loadFactorThreshold;
	private int size;
	private int linearProbing;
	
	/**
	 * 
	 * @author Will Ryan
	 *
	 *Inner class for a HashNode
	 *
	 * @param <K>
	 * @param <V>
	 */
	private class HashNode<K,V> {
		K key;
		V value;
		private HashNode(K k, V v){
			this.key = k;
			this.value = v;
		}
	}
	
	// TODO: ADD and comment DATA FIELD MEMBERS needed for your implementation
		
	// TODO: comment and complete a default no-arg constructor
	/**
	 * no-arg constructor that initializes the data
	 */
	public HashTable() {
		initialCapacity = 10;
		keys = new Object[initialCapacity];
		values = new Object[initialCapacity];
		loadFactorThreshold = 0.75;
		size = 0;
		hashNode = new HashNode[initialCapacity];
		linearProbing = 0;
	}
	
	// TODO: comment and complete a constructor that accepts 
	// initial capacity and load factor threshold
        // threshold is the load factor that causes a resize and rehash
	public HashTable(int initialCapacity, double loadFactorThreshold) {
		this.initialCapacity = initialCapacity;
		this.loadFactorThreshold = loadFactorThreshold;
		this.size = 0;
		keys = new Object[this.initialCapacity];
		values = new Object[this.initialCapacity];
		hashNode = new HashNode[initialCapacity];
		linearProbing = 0;
	}
	// TODO: add all unimplemented methods so that the class can compile
	/**
	 * Insert method that inserts data into the hashtable by using open 
	 * addressing and linear probing
	 */
	@Override
	public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
		if(key == null) {
			throw new IllegalNullKeyException();
		}if(contains(key)) {
			throw new DuplicateKeyException();
		}if(this.getLoadFactor() >= this.getLoadFactorThreshold()) {
			Object[] increasedKeys = new Object[this.initialCapacity * 2 + 1];
            Object[] increasedVals = new Object[this.initialCapacity * 2 + 1];
            HashNode<K,V>[] increasedNode = new HashNode[this.initialCapacity * 2 +1];
            int newCapacity = this.initialCapacity * 2 + 1;
            int length = 0;
            for (int i = 0; i < this.initialCapacity; i++) {
            	length++;
                if (this.keys[i] != null) {
                    int hashIndex = this.getHashIndex(keys[i]);
                    boolean same = false;
                    for (int a = hashIndex; a < newCapacity; a++) {
                        if (a == newCapacity - 1 && !same) {
                            a = 0;
                            same = true;
                        } else if (same) {
                            break;
                        }
                        if (increasedKeys[a] == null) {
                            increasedKeys[a] = this.keys[i];
                            increasedVals[a] = this.values[i];
                            increasedNode[a] = this.hashNode[i];
                            break;
                        }
                    }
                }
            }if(length > linearProbing) {
            	linearProbing = length;
            }
            this.keys = increasedKeys;
            this.values = increasedVals;
            this.hashNode = increasedNode;
            this.initialCapacity = this.initialCapacity * 2 + 1;
		}
		int hashIndex = getHashIndex(key);
		for(int i = hashIndex; i < this.initialCapacity; i++) {
			if(i == initialCapacity -1) {
				i = 0;
			}
			if(this.keys[i] == null) {
				this.keys[i] = key;
				this.values[i] = value;
				this.hashNode[i] = new HashNode(key, value);
				this.size++;
				break;
			}
		}
	}
	
	/**
	 * remove method that removes values by iterating through data
	 */
	@Override
	public boolean remove(K key) throws IllegalNullKeyException {
		if(key == null) {
			throw new IllegalNullKeyException();
		}if(!contains(key)) {
			return false;
		}
		int hashIndex = getHashIndex(key);
		for(int i = hashIndex; i <= hashIndex + linearProbing; i++) {
			if(i == initialCapacity -1) {
				i = 0;
			}
			if(this.keys[i] != null) {
				if(key.compareTo(this.hashNode[i].key) == 0) {
					this.keys[i] = null;
					this.values[i] = null;
					this.hashNode[i] = null; 
					this.size--;
					return true;
				}
			}
		}return false;
	}
	
	/**
	 * provides the hash index
	 * @param key
	 * @return
	 */
	private int getHashIndex(Object key) {
		int hashCode = key.hashCode();
		int hashIndex = hashCode % this.initialCapacity;
		return hashIndex;
	}
	
	/**
	 * gets a value from the data structure
	 */
	@Override
	public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
		if(key == null) {
			throw new IllegalNullKeyException();
		}if(!contains(key)) {
			throw new KeyNotFoundException();
		}
		int hashIndex = getHashIndex(key);
		for(int i = hashIndex; i <= hashIndex + linearProbing; i++) {
			if(i == initialCapacity - 1) {
				i = 0;
			}
			if(this.keys[i] != null) {
				if(key.compareTo(this.hashNode[i].key) == 0) {
					return this.hashNode[i].value;
				}
			}
		}return null;
	}
	
	/**
	 * checks if a value is in the data structure
	 * @param key
	 * @return
	 */
	private boolean contains(K key) {
		int hashIndex = getHashIndex(key);
		int i = hashIndex;
		if(i<0) {
			return false;
		}// iterates through until the value is found
		while(i <= initialCapacity) {
			if(i == initialCapacity - 1) {
				for(int j = 0; j<= hashIndex + linearProbing; j++) {
					if(keys[i] != null) {
						if(keys[i] == key) {
							return true;
						}
					}
				}return false;
			}
			if(keys[i]!= null) { // checking if a value is present
				if(keys[i] == key) {
					return true;
				}
			}i++;
		}return false;
	}
	
	/**
	 * returns number of keys
	 */
	@Override
	public int numKeys() {
		return size;
	}
	
	/**
	 * gets the load factor threshold
	 */
	@Override
	public double getLoadFactorThreshold() {
		return this.loadFactorThreshold;
	}
	
	/**
	 * calculates the load factor
	 */
	@Override
	public double getLoadFactor() {
		if(this.initialCapacity <= 0) {
			return 0;
		}
		return ((double) this.size / (double) this.initialCapacity);
	}
	
	/**
	 * gets the capacity
	 */
	@Override
	public int getCapacity() {
		return this.initialCapacity;
	}
	
	/**
	 * gets the collision resolution data
	 */
	@Override
	public int getCollisionResolution() {
		// 1 OPEN ADDRESSING: linear probe
		return 1;
	}
		
}