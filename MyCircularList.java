import java.util.Collection;
import java.util.Iterator;

public interface MyCircularList<E> extends Collection<E>
{
   /** Add a new element at the specified index in this list */
   public void add(int index, E e);
   
   /** Return the element from this list at the specified index */
   public E get(int index);
   
   /** Return the index of the first matching element in this list.
    *  Return -1 if no match. */
   public int indexOf(Object e);
   
   /** Return the index of the last matching element in this list
    *  Return -1 if no match. */
   public int lastIndexOf(E e);
   
   /** Remove the element at the specified position in this list
    *  Shift any subsequent elements to the left.
    *  Return the element that was removed from the list. */
   public E remove(int index);
   
   /** Replace the element at the specified position in this list
    *  with the specified element and returns the new set. */
   public E set(int index, E e);
   
   public int size();
   
   public boolean contains(Object e);
   
   public Iterator<E> iterator();
   
   @Override
   /** Add a new element at the end of this list */
   public default boolean add(E e)
   {
      add(size(), e);
      return true;
   }
   
   @Override
   /** Return true if this list contains no elements */
   public default boolean isEmpty()
   {
      return size() == 0;
   }
   
   @Override
   /** Remove the first occurrence of the element e 
    *  from this list. Shift any subsequent elements to the left.
    *  Return true if the element is removed. */
   public default boolean remove(Object e)
   {
      if (indexOf(e) >= 0)
      {
         remove(indexOf(e));
         return true;
      }
      else
      {
         return false;
      }
   }
   
   @Override
   public default boolean containsAll(Collection<?> c)
   {
      for (Object element : c)
      {
         if (!contains(element))
         {
            return false;
         }
      }
      return true;
   }
   
   @Override
   public default boolean addAll(Collection<? extends E> c)
   {
      boolean added = false;
      for (E e : c)
      {
         if (add(e))
         {
            added = true;
         }
      }
      return added;
   }
   
   @Override
   public default boolean removeAll(Collection<?> c)
   {
      boolean changed = false;
      for (Iterator<E> it = iterator(); it.hasNext(); )
      {
         if (c.contains(it.next()))
         {
            it.remove();
            changed = true;
         }
      }
      return changed;
   }
   
   @Override
   public default boolean retainAll(Collection<?> c)
   {
      boolean changed = false;
      for (Iterator<E> it = iterator(); it.hasNext(); )
      {
         if (!c.contains(it.next()))
         {
            it.remove();
            changed = true;
         }
      }
      return changed;
   }
   
   @Override
   public default Object[] toArray()
   {
      Object[] result = new Object[size()];
      for (int i = 0; i < size(); i++)
      {
         result[i] = get(i);
      }
      return result;
   }
   
   @Override
   public default <T> T[] toArray(T[] array)
   {
      int size = size();
      if (array.length < size)
      {
         throw new IllegalArgumentException("Array is too small. Expected length is at least " 
            + size + ", but this array length is " + array.length);
      }
      for (int i = 0; i < size; i++)
      {
         array[i] = (T)get(i);
      }
      if (array.length > size)
      {
         array[size] = null;
      }
      return array;
   }
}