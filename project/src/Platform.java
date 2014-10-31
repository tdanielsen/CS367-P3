import java.util.EmptyStackException;


public class Platform implements PlatformADT
{
	private SimpleStack<Train> stack;
	public Platform()
	{
		stack = new SimpleStack<Train>();
	}
	
	public Platform(int capacity)
	{
		stack = new SimpleStack<Train>(capacity);
	}

	public void put(Train item) throws FullPlatformException 
	{
		try {
			stack.push(item);
		}
		catch (FullStackException e) 
		{
			throw new FullPlatformException();
		}
		
	}

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

	public boolean isEmpty() 
	{
		return stack.isEmpty();
	}

	public boolean isFull() 
	{
		return stack.isFull();
	}
}
