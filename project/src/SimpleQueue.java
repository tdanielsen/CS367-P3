///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  TrainSimulator.java
// File:             SimpleQueue.java
// Semester:         CS367 Fall 2014
//
// Author:           Tim Danielsen tdanielsen@wisc.edu
// CS Login:         danielsen
// Lecturer's Name:  J. Skrentny
// Lab Section:      n/a
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * Makes a queue that can have elements added to the rear of it, taken from 
 * the front of it, see what the next element in it is, and can see if it is
 * full or empty
 *
 * <p>Bugs: none known
 *
 * @author Tim Danielsen
 */

public class SimpleQueue<E> implements QueueADT<E> 
{
	private E[] items;
	private int numItems;
	private int capacity;
	private int front = capacity;
	private int rear = 0;
	
	/**
	 * Makes an empty SimpleQueue
	 */
	
	public SimpleQueue()
	{
		
	}
	/**
	 * Makes an empty SimpleQueue with a set capacity
	 *
	 * @param capacity the amount the SimpleQueue will be able to hold
	 */
	
	@SuppressWarnings("unchecked")
	public SimpleQueue(int capacity)
	{
		numItems = 0;
		this.capacity = capacity;
		items = (E[]) new Object[capacity];
		
	}
	
	/**
	 * adds an item into the rear SimpleQueue
	 *
	 * @param item an item that will be put into the SimpleQueue
	 */
	
	public void enqueue(E item) throws FullQueueException 
	{
		if (isFull())
		{
			throw new FullQueueException();
		}
		if (front == -1)
		{
			front = 0;
		}
		items[rear] = item;
		numItems++;
		if (rear >= capacity - 1)
		{
			rear = 0;
		}
		else
		{
			rear++;
		}
	}

	/**
	 * Removes the element from the front of the queue and returns it to the
	 * user
	 *
	 * @return the element at the front of the queue
	 */
	
	public E dequeue() throws EmptyQueueException
	{
		if (isEmpty())
		{
			throw new EmptyQueueException();
		}
		E result = null;
		result = items[front];
		items[front] = null;
		numItems--;
		front++;
		if (isEmpty())
		{
			front = -1;
			rear = 0;
		}
		return result;
	}

	/**
	 * Checks what element is at the front of the queue and returns it to the
	 * user without removing it
	 * 
	 * @return the element at the front of the queue
	 */
	
	public E peek() throws EmptyQueueException
	{
		if (isEmpty())
		{
			throw new EmptyQueueException();
		}
		E result = null;
		result = items[front];
		return result;
	}

	/**
	 * Checks to see if the queue is empty
	 *
	 * @return true if the queue is empty and false otherwise
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
	 * Checks to see if the queue is full
	 *
	 * @return true if the queue is full and false otherwise
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
