
public class TrainTester 
{
	public static void main(String[] args) throws FullStackException, FullQueueException, EmptyQueueException
	{
		SimpleStack<String> stack = new SimpleStack<String>(10);
		stack.push("Hat");
		stack.push("Cat");
		stack.push("Matt");
		stack.push("Lat");
		stack.push("Gat");
		System.out.println(stack.peek());
		String s = "Sat";
		stack.push(s);
		System.out.println(stack.peek());
		stack.pop();
		System.out.println(stack.peek());
		SimpleQueue<String> queue = new SimpleQueue<String>(3);
		queue.enqueue("Hat");
		queue.enqueue("Cat");
		queue.dequeue();
		queue.dequeue();
		System.out.println(queue.peek());
		queue.enqueue("Matt");
		System.out.println(queue.peek());
		queue.dequeue();
		queue.dequeue();
		queue.dequeue();
		System.out.println(queue.peek());
	}

}
