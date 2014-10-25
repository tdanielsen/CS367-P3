/**
 * An ordered collection of items, where items are added to the back and
 * removed from the front.
 */
public interface QueueADT<E> {
	/**
	 * Adds an item to the rear of the queue.
	 *
	 * @param item The item to be added to the queue
	 */
	void enqueue(E item) throws FullQueueException;
	
	/**
	 * Removes and returns the front item of the queue.
	 *
	 * @return The front item of the queue
	 * @throws EmptyQueueException If the queue is empty
	 */
	E dequeue() throws EmptyQueueException;
	
	/**
	 * Returns the front item of the queue without removing it.
	 *
	 * @return The front item of the queue
	 * @throws EmptyQueueException If the queue is empty
	 */
	E peek() throws EmptyQueueException;
	
	/**
	 * Checks if the queue is empty.
	 * 
	 * @return True if queue is empty; else false
	 */
	boolean isEmpty();
	
	/**
	 * Checks if the queue is full.
	 * 
	 * @return True if queue is full; else false
	 */
	boolean isFull();
}