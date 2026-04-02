package implementations;

import java.util.NoSuchElementException;

import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.QueueADT;

public class MyQueue<E> implements QueueADT<E>
{
	// Make a private MyDLL field to hold the elements of the queue
	private MyDLL<E> list;

	public MyQueue()
	{
		list = new MyDLL<>();
	}
	// Implement the QueueADT interface using the MyDLL field to hold the elements of the queue
	@Override
	public void enqueue(E toAdd) throws NullPointerException
	{
		if (toAdd == null)
		{
			throw new NullPointerException();
		}

		list.add(toAdd);
	}
	// Dequeue the list with exceptions for empty queue and return the first element in the queue
	@Override
	public E dequeue() throws EmptyQueueException
	{
		if (isEmpty())
		{
			throw new EmptyQueueException();
		}

		return list.remove(0);
	}
	// Peek the list with exceptions for empty queue and return the first element in the queue without removing it
	@Override
	public E peek() throws EmptyQueueException
	{
		if (isEmpty())
		{
			throw new EmptyQueueException();
		}

		return list.get(0);
	}
	// Clears the list
	@Override
	public void dequeueAll()
	{
		list.clear();
	}
	// Checks if the list is empty
	@Override
	public boolean isEmpty()
	{
		return list.isEmpty();
	}
	// Checks if the list contains the specified element with exceptions for null element
	@Override
	public boolean contains(E toFind) throws NullPointerException
	{
		return list.contains(toFind);
	}
	// Searches the list for the specified element and returns its 1-based position with exceptions for null element
	@Override
	public int search(E toFind)
	{
		if (toFind == null)
		{
			return -1;
		}

		Iterator<E> it = iterator();
		int position = 1;
		// Iterate through the queue using the iterator and compare each element to the specified element
		while (it.hasNext())
		{
			E current = it.next();
			if (toFind.equals(current))
			{
				return position;
			}
			position++;
		}

		return -1;
	}
	// Returns an iterator over the elements in the queue by creating a snapshot of the current state of the queue and iterating through it
	@Override
	public Iterator<E> iterator()
	{
		Object[] snapshot = list.toArray();

		return new Iterator<E>()
		{
			private int currentIndex = 0;

			@Override
			public boolean hasNext()
			{
				return currentIndex < snapshot.length;
			}

			@Override
			public E next() throws NoSuchElementException
			{
				if (!hasNext())
				{
					throw new NoSuchElementException();
				}

				@SuppressWarnings("unchecked")
				E value = (E) snapshot[currentIndex++];
				return value;
			}
		};
	}
	// Compares this queue to another queue by checking if they are the same size and if each element is equal in order
	@Override
	public boolean equals(QueueADT<E> that)
	{
		if (that == null)
		{
			return false;
		}

		if (this.size() != that.size())
		{
			return false;
		}

		Object[] thisArray = this.toArray();
		Object[] thatArray = that.toArray();

		for (int i = 0; i < thisArray.length; i++)
		{
			if (!thisArray[i].equals(thatArray[i]))
			{
				return false;
			}
		}

		return true;
	}
	// Returns an array containing all of the elements in the queue
	@Override
	public Object[] toArray()
	{
		return list.toArray();
	}
	// Returns an array containing all of the elements in the queue in proper sequence
	@Override
	public E[] toArray(E[] holder) throws NullPointerException
	{
		return list.toArray(holder);
	}
	// Returns false since this implementation of the queue does not have a fixed length
	@Override
	public boolean isFull()
	{
		return false;
	}
	// Returns the size of the queue by returning the size of the underlying list
	@Override
	public int size()
	{
		return list.size();
	}
}