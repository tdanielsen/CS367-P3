///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  TrainSimulator.java
// File:             SimpleStack.java
// Semester:         CS367 Fall 2014
//
// Author:           Tim Danielsen tdanielsen@wisc.edu
// CS Login:         danielsen
// Lecturer's Name:  J. Skrentny
// Lab Section:      n/a
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.EmptyStackException;

/**
 * Makes a stack that can be added to, removed from, looked at the top of, and
 * see if it is empty or full.
 *
 * <p>Bugs: none known
 *
 * @author Tim Danielsen
 */

public class SimpleStack<E> implements StackADT<E>
{
	private E[] items;
	private int numItems;
	private int capacity;
	
	/**
	 * Makes an empty SimpleStack
	 */
	
	public SimpleStack()
	{
		
	}
	
	/**
	 * Makes an empty SimpleStack with set capacity
	 *
	 * @param capacity the amount of elements the SimpleStack will be able 
	 * to hold
	 */
	
	@SuppressWarnings("unchecked")
	public SimpleStack(int capacity)
	{
		numItems = 0;
		this.capacity = capacity;
		items = (E[]) new Object[capacity];
	}	

	/**
	 * Puts an item on top of the stack
	 *
	 * @param item an item that will be put into the SimpleStack
	 */
	
	public void push(E item) throws FullStackException 
	{
		if (isFull())
		{
			throw new FullStackException();
		}
		items[numItems] = item;
		numItems++;
		
	}

	/**
	 * Takes the element from the top of the stack and returns it to the user
	 *
	 * @return the element from the top of the stack
	 */
	
	public E pop() throws EmptyStackException 
	{
		if (isEmpty())
		{
			throw new EmptyStackException();
		}
		E result = items[numItems - 1];
		items[numItems - 1] = null;
		numItems--;
		return result;
	}

	/**
	 * Looks at the top element of the stack and tells the user without
	 * removing the element
	 *
	 * @return the top element of the stack
	 */
	
	public E peek() throws EmptyStackException 
	{
		if (isEmpty())
		{
			throw new EmptyStackException();
		}
		E result = items[numItems-1];
		return result;
	}

	/**
	 * Checks to see if the stack is empty
	 *
	 * @return true if the stack is empty and false otherwise
	 */
	
	public boolean isEmpty() 
	{
		if (numItems == 0)
		{
			return true;
		}
		return false;
	}

	/**
	 * Checks to see if the stack is full (at capacity)
	 *
	 * @return true if the stack is full and false otherwise
	 */
	
	public boolean isFull() 
	{
		if (numItems == capacity)
		{
			return true;
		}
		return false;
	}	
}
