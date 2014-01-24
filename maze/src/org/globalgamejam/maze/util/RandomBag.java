package org.globalgamejam.maze.util;

import java.util.ArrayList;
import java.util.Random;

public class RandomBag<Type> {
	
	private ArrayList<Type> elements;
	
	private int index;
	
	private Random random;
	
	public RandomBag() {
		elements = new ArrayList<Type>();
		random = new Random(System.currentTimeMillis());
	}

	public void put(Type element) {
		elements.add(element);
	}
	
	public Type fetch() {
		generateRandomIndex();
		return elements.remove(index);		
	}
	
	private void generateRandomIndex() {
		index = random.nextInt(elements.size());
	}
	
	public boolean isEmpty() {
		return elements.isEmpty();
	}
	
	public int size() {
		return elements.size();
	}
}
