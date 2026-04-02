package implementations;

import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

public class MyDLL<E> implements ListADT<E>
{
	private MyDLLNode<E> head;
	private MyDLLNode<E> tail;
	private int size;
	
	// Constructor initializes an empty list
	public MyDLL()
	{
		head = null;
		tail = null;
		size = 0;
	}
	// Returns the number of elements in the list
	@Override
	public int size()
	{
		return size;
	}
	// Clears the list by resetting head, tail, and size
	@Override
	public void clear()
	{
		head = null;
		tail = null;
		size = 0;
	}
	// Adds an element at a specific index, handling edge cases for head and tail
	@Override
	public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException
	{
		// Validate input parameters
		if (toAdd == null)
		{
			throw new NullPointerException();
		}
		// Allow adding at the end (index == size) but not past its
		if (index < 0 || index > size)
		{
			throw new IndexOutOfBoundsException();
		}
		// Create a new node with the provided data
		MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);

		// Handle insertion based on the current size and index
		if (size == 0)
		{
			head = newNode;
			tail = newNode;
		}
		else if (index == 0)
		{
			newNode.next = head;
			head.prev = newNode;
			head = newNode;
		}
		else if (index == size)
		{
			newNode.prev = tail;
			tail.next = newNode;
			tail = newNode;
		}
		else
		{
			MyDLLNode<E> current = getNode(index);
			MyDLLNode<E> previous = current.prev;

			newNode.prev = previous;
			newNode.next = current;
			previous.next = newNode;
			current.prev = newNode;
		}

		// Increment the size of the list after successful addition
		size++;
		return true;
	}
	// Adds an element at the end of the list by calling the add method with index equal to size of the list
	@Override
	public boolean add(E toAdd) throws NullPointerException
	{
		return add(size, toAdd);
	}
    // Adds all elements from another ListADT to the end of this list
	@Override
	public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException
	{
		// Validate input parameter
		if (toAdd == null)
		{
			throw new NullPointerException();
		}
		// Use an iterator to progress the provided ListADT and add each element to this list
		Iterator<? extends E> it = toAdd.iterator();
		while (it.hasNext())
		{
			add(it.next());
		}

		return true;
	}
	// Retrieves the element at a specific index by first getting the corresponding node
	@Override
	public E get(int index) throws IndexOutOfBoundsException
	{
		return getNode(index).data;
	}
	// Removes the element at a specific index, handling edge cases for head and tail, and returns the removed data
	@Override
	public E remove(int index) throws IndexOutOfBoundsException
	{
		MyDLLNode<E> nodeToRemove = getNode(index);
		E removedData = nodeToRemove.data;

		if (size == 1)
		{
			head = null;
			tail = null;
		}
		else if (nodeToRemove == head)
		{
			head = head.next;
			head.prev = null;
		}
		else if (nodeToRemove == tail)
		{
			tail = tail.prev;
			tail.next = null;
		}
		else
		{
			nodeToRemove.prev.next = nodeToRemove.next;
			nodeToRemove.next.prev = nodeToRemove.prev;
		}

		size--;
		return removedData;
	}
	// Removes the first occurrence of a specific element by iterating through the list and calling the remove method with the corresponding inde
	@Override
	public E remove(E toRemove) throws NullPointerException
	{
		if (toRemove == null)
		{
			throw new NullPointerException();
		}

		MyDLLNode<E> current = head;
		int index = 0;

		while (current != null)
		{
			if (toRemove.equals(current.data))
			{
				return remove(index);
			}
			current = current.next;
			index++;
		}

		return null;
	}
	// Replaces the element at a specific index with a new element and returns the old element
	@Override
	public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException
	{
		if (toChange == null)
		{
			throw new NullPointerException();
		}

		MyDLLNode<E> node = getNode(index);
		E oldValue = node.data;
		node.data = toChange;
		return oldValue;
	}
	// Returns true if the list contains no elements by checking if size is zero
	@Override
	public boolean isEmpty()
	{
		return size == 0;
	}
	// Checks if the list contains a specific element by iterating through the list and comparing each element to the target
	@Override
	public boolean contains(E toFind) throws NullPointerException
	{
		// Validate input parameter
		if (toFind == null)
		{
			throw new NullPointerException();
		}
		// Iterate through the list and check if any element matches the target using equals method
		MyDLLNode<E> current = head;
		while (current != null)
		{
			if (toFind.equals(current.data))
			{
				return true;
			}
			current = current.next;
		}

		return false;
	}
	// Returns the position of the first occurrence of a  element by iterating through the list and comparing each element to the target
	@Override
	public E[] toArray(E[] toHold) throws NullPointerException
	{
		if (toHold == null)
		{
			throw new NullPointerException();
		}

		if (toHold.length < size)
		{
			@SuppressWarnings("unchecked")
			E[] newArray = (E[]) java.lang.reflect.Array.newInstance(
				toHold.getClass().getComponentType(), size);
			toHold = newArray;
		}

		MyDLLNode<E> current = head;
		int i = 0;
		while (current != null)
		{
			toHold[i++] = current.data;
			current = current.next;
		}

		if (toHold.length > size)
		{
			toHold[size] = null;
		}

		return toHold;
	}
	// Returns an array containing all of the elements in this list in sequence by iterating through the list and copying each element to a new array
	@Override
	public Object[] toArray()
	{
		Object[] array = new Object[size];
		MyDLLNode<E> current = head;
		int i = 0;

		while (current != null)
		{
			array[i++] = current.data;
			current = current.next;
		}

		return array;
	}
	// Returns an iterator over the elements in the list in sequence by taking the current state of the list and iterating through that state of list
	@Override
	public Iterator<E> iterator()
	{
		Object[] snapshot = toArray();

		return new Iterator<E>()
		{
			private int currentIndex = 0;
			// hasNext checks if there are more elements to iterate over by comparing the current index to the length of the snapshot array
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
	// Get specific node at a given index
	private MyDLLNode<E> getNode(int index)
	{
		if (index < 0 || index >= size)
		{
			throw new IndexOutOfBoundsException();
		}

		MyDLLNode<E> current;

		if (index < size / 2)
		{
			current = head;
			for (int i = 0; i < index; i++)
			{
				current = current.next;
			}
		}
		else
		{
			current = tail;
			for (int i = size - 1; i > index; i--)
			{
				current = current.prev;
			}
		}

		return current;
	}
}