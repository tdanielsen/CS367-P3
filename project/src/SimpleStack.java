import java.util.EmptyStackException;


public class SimpleStack<E> implements StackADT<E>
{
	private E[] items;
	private int numItems;
	private int capacity;

	public SimpleStack()
	{
		
	}
	@SuppressWarnings("unchecked")
	public SimpleStack(int capacity)
	{
		numItems = 0;
		this.capacity = capacity;
		items = (E[]) new Object[capacity];
	}	

	public void push(E item) throws FullStackException 
	{
		if (isFull())
			throw new FullStackException();
		items[numItems] = item;
		numItems++;
		
	}

	public E pop() throws EmptyStackException {
		if (isEmpty())
			throw new EmptyStackException();
		E result = null;
		result = items[numItems - 1];
		items[numItems - 1] = null;
		numItems--;
		return result;
	}

	public E peek() throws EmptyStackException {
		if (isEmpty())
			throw new EmptyStackException();
		E result = null;
		result = items[numItems-1];
		return result;
	}

	public boolean isEmpty() {
		if (numItems == 0)
			return true;
		return false;
	}

	public boolean isFull() {
		if (numItems == capacity)
			return true;
		return false;
	}
	
}
