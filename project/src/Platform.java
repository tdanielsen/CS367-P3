import java.util.EmptyStackException;


public class Platform implements PlatformADT
{
	private SimpleStack stack;
	public Platform()
	{
		stack = new SimpleStack();
	}
	
	public Platform(int capacity)
	{
		stack = new SimpleStack(capacity);
	}

	public void put(Train item) throws FullPlatformException {
		if (stack.isFull())
			throw new FullPlatformException();
		try {
			stack.push(item);
		}
		catch (FullStackException e) 
		{
			
		}
		
	}

	public Train get() throws EmptyPlatformException {
		if (stack.isEmpty())
			throw new EmptyPlatformException();
		try 
		{
			return (Train) stack.pop();	
		}
		catch (EmptyStackException e) 
		{
			
		}
		return null;
	}

	public Train check() throws EmptyPlatformException {
		if (stack.isEmpty())
			throw new EmptyPlatformException();
		try 
		{
			return (Train) stack.peek();
		}
		catch (EmptyStackException e) 
		{
			
		}
		return null;
	}

	public boolean isEmpty() {
		if (stack.isEmpty())
			return true;
		return false;
	}

	public boolean isFull() {
		if (stack.isFull())
			return true;
		return false;
	}
}
