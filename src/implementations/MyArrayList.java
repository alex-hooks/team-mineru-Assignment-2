package implementations;

import java.util.NoSuchElementException;

import utilities.ListADT;
import utilities.Iterator;


/**
 * resizable-array implementation of ListADT 
 * uses an array to store elements 
 * 
 * pre-conditions: elements added must not be null
 * post-conditions: the list maintains insertion order and resizes dynamically
 * 
 * @param <E> the type of elements in this list
 */

public class MyArrayList<E> implements ListADT<E>{

	// initial / default capacity of array
	private static final int DEFAULT_CAPACITY = 10;
	
	// stores elements of the list
	private E[] array;
	
	// number of elements currently in the list
	private int size;
	
	/**
	 * method that constructs an empty list with default initial capacity
	 */
	@SuppressWarnings("unchecked")
	public MyArrayList()
	{
		array = (E[])new Object[DEFAULT_CAPACITY];
		size = 0;
	}
	
	/**
	 * method doubles the capacity of the array when full
	 */
	@SuppressWarnings("unchecked")
	private void ensureCapacity()
	{
		if (size == array.length)
		{
			E[] newArray = (E[]) new Object[array.length * 2];
			System.arraycopy(array, 0, newArray, 0, size);
			array = newArray;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override 
	public int size()
	{
		return size;
	}
	
	@Override
	public void clear() 
	{
		for (int i = 0; i < size; i++)
		{
			array[i] = null;
		}
		size = 0;
	}
	
	/**
	 *  {inheritDoc}
	 *  
	 *  @throws NullPointerException if toAdd is null
	 *  @throws IndexOutOfBoundsException if index < 0 or index > size()
	 */
	@Override
	public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException
	{
		if (toAdd == null)
		{
			throw new NullPointerException("Cannot add null element to the list");
		}
		if (index < 0 || index > size)
		{
			throw new IndexOutOfBoundsException("Index out of bounds: " + index );
		}
		
		// check if there is space in the list
		ensureCapacity();
		// shift elements right to make room 
		for (int i = size; i > index; i--)
		{
			array[i] = array[i -1];
		}
		array[index] = toAdd;
		size++;
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException if toAdd is null
	 */
	@Override
	public boolean add(E toAdd) throws NullPointerException
	{
		// append to the end of the list
		return add(size, toAdd);
	}
 
	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException if toAdd is null
	 */
	@Override
	public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException
	{
		if ( toAdd == null )
		{
			throw new NullPointerException( "Cannot add a null list" );
		}
		Iterator<? extends E> it = toAdd.iterator();
		while (it.hasNext())
		{
			add(it.next());
		}
		return true;
	}
 
	/**
	 * {@inheritDoc}
	 * 
	 * @throws IndexOutOfBoundsException if index < 0 or index >= size()
	 */
	@Override
	public E get(int index) throws IndexOutOfBoundsException
	{
		if (index < 0 || index >= size)
		{
			throw new IndexOutOfBoundsException( "Index out of bounds: " + index );
		}
		return array[index];
	}
 
	/**
	 * {@inheritDoc}
	 * 
	 * @throws IndexOutOfBoundsException if index < 0 or index >= size()
	 */
	@Override
	public E remove(int index) throws IndexOutOfBoundsException
	{
		if (index < 0 || index >= size)
		{
			throw new IndexOutOfBoundsException( "Index out of bounds: " + index );
		}
		E removedElement = array[index];
		
		// shift all elements after index one position to the left
		for (int i = index; i < size - 1; i++)
		{
			array[i] = array[i + 1];
		}
		array[size - 1] = null; 
		size --;
		return removedElement;
	}
 
	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException if toRemove is null
	 */
	@Override
	public E remove(E toRemove) throws NullPointerException
	{
		if ( toRemove == null )
		{
			throw new NullPointerException( "Cannot remove null element." );
		}
		for ( int i = 0; i < size; i++ )
		{
			if (toRemove.equals(array[i]))
			{
				return remove( i );
			}
		}
		// if not found return null
		return null;
	}
 
	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException if toChange is null
	 * @throws IndexOutOfBoundsException if index < 0 or index >= size()
	 */
	@Override
	public E set( int index, E toChange ) throws NullPointerException, IndexOutOfBoundsException
	{
		if (toChange == null)
		{
			throw new NullPointerException( "Cannot set a null element." );
		}
		if ( index < 0 || index >= size )
		{
			throw new IndexOutOfBoundsException( "Index out of bounds: " + index );
		}
		E old = (E) array[index];
		array[index] = toChange;
		return old;
	}
 
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEmpty()
	{
		return size == 0;
	}
 
	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException if toFind is null.
	 */
	@Override
	public boolean contains(E toFind) throws NullPointerException
	{
		if (toFind == null)
		{
			throw new NullPointerException( "Cannot search for null element." );
		}
		for (int i = 0; i < size; i++)
		{
			if ( toFind.equals(array[i]))
				return true;
		}
		return false;
	}
 
	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException if toHold is null.
	 */
	@Override
	public E[] toArray(E[] toHold) throws NullPointerException
	{
		if (toHold == null)
		{
			throw new NullPointerException( "Holder array cannot be null." );
		}
		if (toHold.length < size)
		{
			// create new array of the same type with correct size
			@SuppressWarnings("unchecked")
			E[] newArray = (E[]) java.lang.reflect.Array.newInstance(toHold.getClass().getComponentType(),size);
			toHold = newArray;
		}
		System.arraycopy(array, 0, toHold, 0, size);
		if (toHold.length > size)
		{
			// clear extra elements
			toHold[size] = null;
		}
		return toHold;
	}
	
	/**
     * {@inheritDoc}
     * Returns a new array containing all elements in the list.
     */
    @Override
    public Object[] toArray() 
    {
        Object[] result = new Object[size];
        System.arraycopy(array, 0, result, 0, size);
        return result;
    }
 
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<E> iterator()
	{
		final Object[] snapshot = toArray();
		return new Iterator<E>() 
		{
			private int currentIndex = 0;
			
			@Override
			public boolean hasNext() 
			{
				return currentIndex < snapshot.length;
			}
			@SuppressWarnings("unchecked")
			@Override
			public E next() throws NoSuchElementException
			{
				if(!hasNext())
				{
					throw new NoSuchElementException("No more elements in iterator");
				}
				return (E) snapshot[currentIndex++];
			}
		};
	}
}

