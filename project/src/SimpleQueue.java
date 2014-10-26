


public class SimpleQueue<E> implements QueueADT<E> 
{
	private E[] items;
	private int numItems;
	private int capacity;
	private int front = capacity;
	private int rear = 0;
	
	public SimpleQueue()
	{
		
	}
	@SuppressWarnings("unchecked")
	public SimpleQueue(int capacity)
	{
		numItems = 0;
		this.capacity = capacity;
		items = (E[]) new Object[capacity];
		
	}
	public void enqueue(E item) throws FullQueueException {
		if (isFull())
			throw new FullQueueException();
		if (front == -1)
			front = 0;
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

	public E dequeue() throws EmptyQueueException {
		if (isEmpty())
			throw new EmptyQueueException();
		E result = null;
		result = items[front];
		items[front] = null;
		numItems--;
		front++;
		return result;
	}

	public E peek() throws EmptyQueueException {
		if (isEmpty())
			throw new EmptyQueueException();
		E result = null;
		result = items[front];
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
	public int size()
	{
		return capacity;
	}
}
