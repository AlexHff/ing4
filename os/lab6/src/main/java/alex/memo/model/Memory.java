package alex.memo.model;

import java.util.Map;
import java.util.TreeMap;

public class Memory {
	private Short[] lomem;
	private Short[] phymem;
	private Map<Integer,Integer> map;
	
	public Memory(int size) {
		this.lomem = new Short[size];
		this.phymem = new Short[size];
		this.map = new TreeMap<Integer,Integer>(); 
	}

	@Override
	public String toString() {
		return "Memory [map=" + map + "]";
	}

	/**
	 * Allocates memory space using first-fit
	 * @param size
	 * @return start address of allocated memory space
	 */
	public int alloc(int size) {
		// Throw an error if the given size is negative
		if (size < 0) {
			throw new IllegalArgumentException("Size cannot be negative");
		}
		
		// Find number of contiguous words in logical memory
		int start = 0, end = 0;
		while (end < lomem.length && end - start != size) {
			if (lomem[end] != null) {
				start = end+1;
			}
			end++;
		}
		
		// Throw an error if there is not enough space in logical memory left
		if (end - start < size) {
			throw new OutOfMemoryError("Out of logical memory");
		}
		
		// Update the free list of logical words accordingly to book them
		for (int i = start; i < end; i++) {
			lomem[i] = 1;
		}
		
		// Find addresses in physical memory and update the free list of physical
		// words accordingly to book them
		int[] phymemAddresses = new int[size];
		int index = 0;
		for (int i = 0; i < phymem.length && index < size; i++) {
			if (phymem[i] == null) {
				phymemAddresses[index++] = i;
				phymem[i] = 1;
			}
		}
		
		// Throw an error if there is not enough space in physical memory left
		if (index < size) {
			throw new OutOfMemoryError("Out of physical memory");
		}
		
		// Update the mapping table to link a logical word to a physical one
		for (int i = start, j = 0; i < end && j < phymemAddresses.length; i++, j++) {
			map.put(i, phymemAddresses[j]);
		}

		return start;
	}
	
	public void write(int adr, short val) {
		// Throw an error if the address is not contained in the mapping table
		if (!map.containsKey(adr)) {
			throw new IllegalAccessError();
		}

		// Write value to logical and physical memory
		lomem[adr] = val;
		phymem[map.get(adr)] = val;
	}
	
	public Short read(int adr) {
		// Throw an error if the address is not contained in the mapping table
		if (!map.containsKey(adr)) {
			throw new IllegalAccessError();
		}
		
		// Access the element mapped to the address
		return phymem[map.get(adr)];
	}
	
	public void free(int start, int size) {
		// Throw an error if the given size is negative
		if (size < 0) {
			throw new IllegalArgumentException("Size cannot be negative");
		}
		
		// Throw an error if the given address is negative
		if (start < 0) {
			throw new IllegalArgumentException("Address cannot be negative");
		}
		
		// Set addresses to null and remove mapping from table
		for (int i = start; i < start + size; i++) {
			lomem[i] = null;
			phymem[map.get(i)] = null;
			map.remove(i);
		}
	}
}
