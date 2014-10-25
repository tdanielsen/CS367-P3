import java.util.EmptyStackException;

/**
 * An ordered collection of items, where items are added and removed at the top.
 */
public interface StackADT<E> {	
	/**
	 * Adds an item to the top of the stack.
	 *
	 * @param item The item to add to the stack
	 */
	void push(E item) throws FullStackException;
	
	/**
	 * Removes and returns the top item of the stack.
	 *
	 * @return The top item of the stack
	 * @throws EmptyStackException If the stack is empty
	 */
	E pop() throws EmptyStackException;
	
	/**
	 * Returns the top item of the stack without removing it.
	 *
	 * @return The top item of the stack
	 * @throws EmptyStackException If the stack is empty
	 */
	E peek() throws EmptyStackException;
	
	/**
	 * Checks if the stack is empty.
	 * 
	 * @return True if stack is empty; else false
	 */
	boolean isEmpty();
	
	/**
	 * Checks if the stack is full.
	 * 
	 * @return True if stack is full; else false
	 */
	boolean isFull();
}