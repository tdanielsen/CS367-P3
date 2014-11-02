///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  TrainSimulator.java
// File:             Platform.java
// Semester:         CS367 Fall 2014
//
// Author:           Tim Danielsen tdanielsen@wisc.edu
// CS Login:         danielsen
// Lecturer's Name:  J. Skrentny
// Lab Section:      n/a
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * Makes a platform that holds trains with the same function of a SimpleStack
 *
 * <p>Bugs: none known
 *
 * @author Tim Danielsen
 */

import java.util.EmptyStackException;

public class Platform implements PlatformADT
{
	private SimpleStack<Train> stack;
	
	/**
	 * makes an empty Platform based on SimpleStack
	 */
	
	public Platform()
	{
		stack = new SimpleStack<Train>();
	}
	
	/**
	 * makes an empty Platform with a set capacity based on SimpleStack
	 *
	 * @param capacity the amount of elements the Platform will be able hold
	 */
	
	public Platform(int capacity)
	{
		stack = new SimpleStack<Train>(capacity);
	}

	/**
	 * Puts a train in the platform at the top of the stack
	 *
	 * @param item the train being put in 
	 */
	
	public void put(Train item) throws FullPlatformException 
	{
		try 
		{
			stack.push(item);
		}
		catch (FullStackException e) 
		{
			throw new FullPlatformException();
		}
		
	}

	/**
	 * Removes the train at the top of the stack and returns it to the user
	 *
	 * @return the train that was at the top of the stack in the Platform
	 */
	
	public Train get() throws EmptyPlatformException 
	{
		try 
		{
			return stack.pop();	
		}
		catch (EmptyStackException e) 
		{
			throw new EmptyPlatformException();
		}
	}

	/**
	 * Looks at the train at the top of the stack in the Platform and returns
	 *
	 * @return the train that is at the top of the stack in the Platform
	 */
	
	public Train check() throws EmptyPlatformException 
	{
		try 
		{
			return stack.peek();
		}
		catch (EmptyStackException e) 
		{
			throw new EmptyPlatformException();
		}
	}
	
	/**
	 * Checks to see if the the Platform is empty
	 *
	 * @return true if it is empty and false otherwise
	 */
	
	public boolean isEmpty() 
	{
		return stack.isEmpty();
	}

	/**
	 * Checks to see if the Platform is full (at capacity)
	 *
	 * @return true if the Platform is full and false otherwise
	 */
	
	public boolean isFull() 
	{
		return stack.isFull();
	}
}
