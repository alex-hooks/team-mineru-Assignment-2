package implementations;

import exceptions.EmptyStackException;
import utilities.Iterator;
import utilities.StackADT;

/**
 * array-based implementation of StackADT 
 * uses MyArrayList as the underlying data structure to store elements 
 * 
 * pre-conditions: elements added (push) must not be null
 * post-conditions: the stack maintains LIFO order and grows dynamically as needed
 * 
 * @param <E> the type of elements in this stack
 */
public class MyStack<E> implements StackADT<E> {

	// the underlying data structure (resizable-array list) that holds stack elements
	// top of stack is always the last element in the list
	private MyArrayList<E> list;

	/**
	 * method that constructs an empty stack using MyArrayList as the backing store
	 */
	public MyStack()
	{
		list = new MyArrayList<>();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException if toAdd is null
	 */
	@Override 
	public void push(E toAdd) throws NullPointerException
	{
		if (toAdd == null)
		{
			throw new NullPointerException("Cannot push null onto stack");
		}
		// add to the end of the list = top of the stack (LIFO)
		list.add(toAdd);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws EmptyStackException if the stack is empty
	 */
	@Override
	public E pop() throws EmptyStackException 
	{
		if (isEmpty())
		{
			throw new EmptyStackException();
		}
		// remove and return the last element in the list (top of stack)
		return list.remove(list.size() - 1);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws EmptyStackException if the stack is empty
	 */
	@Override
	public E peek() throws EmptyStackException
	{
		if (isEmpty())
		{
			throw new EmptyStackException();
		}
		// return (but do not remove) the last element in the list (top of stack)
		return list.get(list.size() - 1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() 
	{
		// remove all elements from the underlying list
		list.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEmpty()
	{
		// stack is empty when the underlying list has zero elements
		return list.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] toArray() 
	{
		// return a new array containing all elements (bottom to top)
		return list.toArray();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException if holder is null
	 */
	@Override
	public E[] toArray(E[] holder) throws NullPointerException
	{
		// delegate to the underlying list's toArray (reuses its logic)
		return list.toArray(holder);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException if toFind is null
	 */
	@Override
	public boolean contains(E toFind) throws NullPointerException
	{
		// delegate to the underlying list's contains method
		return list.contains(toFind);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int search(E toFind)
	{
		if (toFind == null)
		{
			return -1;
		}
		// search from the top of the stack (position 1 = most recently pushed)
		for (int i = list.size() - 1, pos = 1; i >= 0; i--, pos++)
		{
			if (toFind.equals(list.get(i)))
			{
				return pos;
			}
		}
		// element not found in the stack
		return -1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<E> iterator()
	{
		// create a snapshot so iteration is consistent even if stack changes
		final Object[] snapshot = list.toArray();
		return new Iterator<E>() 
		{
			private int currentIndex = 0;
			
			@Override
			public boolean hasNext() 
			{
				// still more elements left in the snapshot?
				return currentIndex < snapshot.length;
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public E next() throws java.util.NoSuchElementException
			{
				if (!hasNext())
				{
					throw new java.util.NoSuchElementException();
				}
				// return next element and advance the index
				return (E) snapshot[currentIndex++];
			}
		};
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(StackADT<E> that)
	{
		if (that == null || size() != that.size())
		{
			return false;
		}
		// compare the two stacks element-by-element from bottom to top
		Object[] thisArr = toArray();
		Object[] thatArr = that.toArray();
		for (int i = 0; i < thisArr.length; i++)
		{
			if (!thisArr[i].equals(thatArr[i]))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size()
	{
		// return the number of elements in the underlying list
		return list.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean stackOverflow()
	{
		// this implementation is unbounded (uses resizable-MyArrayList)
		return false;
	}
}