import java.util.Iterator;

public class MyCircularDoublyLinkedList<E> implements MyCircularList<E>
{
   private static class Node<E>
   {
      E element;
      Node<E> next;
      Node<E> prev;
      
      Node(E element)
      {
         this.element = element;
         this.next = null;
         this.prev = null;
      }
   }
   
   // The head is tail.next
   private Node<E> tail;
   private int size;
   
   public MyCircularDoublyLinkedList()
   {
      tail = null;
      size = 0;
   }
   
   // Inserts element at the beginning
   private void addFirst(E e)
   {
      Node<E> newNode = new Node<E>(e);
      if (tail == null)
      {
         tail = newNode;
         tail.next = tail;
         tail.prev = tail;
      }
      else
      {
         Node<E> head = tail.next;
         newNode.next = head;
         newNode.prev = tail;
         tail.next = newNode;
         head.prev = newNode;
      }
      size = size + 1;
   }
   
   // Inserts element at the end
   private void addLast(E e)
   {
      Node<E> newNode = new Node<E>(e);
      if (tail == null)
      {
         tail = newNode;
         tail.next = tail;
         tail.prev = tail;
      }
      else
      {
         Node<E> head = tail.next;
         newNode.next = head;
         newNode.prev = tail;
         tail.next = newNode;
         head.prev = newNode;
         tail = newNode;
      }
      size = size + 1;
   }
   
   // Implements add at index
   public void add(int index, E e)
   {
      if (index <= 0)
      {
         addFirst(e);
      }
      else if (index >= size)
      {
         addLast(e);
      }
      else
      {
         Node<E> current = tail.next;
         int count = 0;
         while (count < index)
         {
            current = current.next;
            count = count + 1;
         }
         Node<E> newNode = new Node<E>(e);
         newNode.next = current;
         newNode.prev = current.prev;
         current.prev.next = newNode;
         current.prev = newNode;
         size = size + 1;
      }
   }
   
   // Returns the element at the specified index
   public E get(int index)
   {
      if (tail == null)
      {
         return null;
      }
      if (index < 0 || index >= size)
      {
         return null;
      }
      Node<E> current = tail.next;
      int count = 0;
      while (count < index)
      {
         current = current.next;
         count = count + 1;
      }
      return current.element;
   }
   
   // Returns the index of the first matching element
   public int indexOf(Object e)
   {
      if (tail == null)
      {
         return -1;
      }
      Node<E> head = tail.next;
      Node<E> current = head;
      int index = 0;
      do
      {
         if (e == null)
         {
            if (current.element == null)
            {
               return index;
            }
         }
         else
         {
            if (current.element.equals(e))
            {
               return index;
            }
         }
         current = current.next;
         index = index + 1;
      }
      while (current != head);
      return -1;
   }
   
   // Returns the index of the last matching element
   public int lastIndexOf(E e)
   {
      if (tail == null)
      {
         return -1;
      }
      Node<E> head = tail.next;
      Node<E> current = tail;
      int index = size - 1;
      do
      {
         if (e == null)
         {
            if (current.element == null)
            {
               return index;
            }
         }
         else
         {
            if (current.element.equals(e))
            {
               return index;
            }
         }
         current = current.prev;
         index = index - 1;
      }
      while (current != tail);
      return -1;
   }
   
   // Removes and returns the element at the specified index
   public E remove(int index)
   {
      if (tail == null)
      {
         return null;
      }
      if (index < 0 || index >= size)
      {
         return null;
      }
      Node<E> head = tail.next;
      if (index == 0)
      {
         E element = head.element;
         if (size == 1)
         {
            tail = null;
         }
         else
         {
            tail.next = head.next;
            head.next.prev = tail;
         }
         size = size - 1;
         return element;
      }
      else
      {
         Node<E> current = head;
         int count = 0;
         while (count < index)
         {
            current = current.next;
            count = count + 1;
         }
         current.prev.next = current.next;
         current.next.prev = current.prev;
         if (current == tail)
         {
            tail = current.prev;
         }
         size = size - 1;
         return current.element;
      }
   }
   
   // Replaces the element at the specified index
   public E set(int index, E e)
   {
      if (tail == null)
      {
         return null;
      }
      if (index < 0 || index >= size)
      {
         return null;
      }
      Node<E> current = tail.next;
      int count = 0;
      while (count < index)
      {
         current = current.next;
         count = count + 1;
      }
      E old = current.element;
      current.element = e;
      return old;
   }
   
   // Returns the size of the list
   public int size()
   {
      return size;
   }
   
   // Returns true if the list contains the specified element
   public boolean contains(Object e)
   {
      return (indexOf(e) >= 0);
   }
   
   // Returns an iterator over the elements in this list
   public Iterator<E> iterator()
   {
      return new Iterator<E>()
      {
         private Node<E> current;
         private Node<E> lastReturned;
         private int count;
         
         {
            if (tail == null)
            {
               current = null;
            }
            else
            {
               current = tail.next;
            }
            count = 0;
            lastReturned = null;
         }
         
         public boolean hasNext()
         {
            return count < size;
         }
         
         public E next()
         {
            lastReturned = current;
            E element = current.element;
            current = current.next;
            count = count + 1;
            return element;
         }
         
         public void remove()
         {
            if (lastReturned == null)
            {
               return;
            }
            if (size == 1)
            {
               tail = null;
               current = null;
            }
            else
            {
               lastReturned.prev.next = lastReturned.next;
               lastReturned.next.prev = lastReturned.prev;
               if (lastReturned == tail.next)
               {
                  tail.next = lastReturned.next;
               }
               if (lastReturned == tail)
               {
                  tail = lastReturned.prev;
               }
            }
            size = size - 1;
            count = count - 1;
            lastReturned = null;
         }
      };
   }
   
   // Clears the list
   public void clear()
   {
      tail = null;
      size = 0;
   }
   
   // Prints all elements in forward order without using size
   public void walkForward()
   {
      if (tail == null)
      {
         System.out.println("List is empty");
         return;
      }
      Node<E> head = tail.next;
      Node<E> current = head;
      do
      {
         System.out.print(current.element + " ");
         current = current.next;
      }
      while (current != head);
      System.out.println();
   }
   
   // Prints all elements in backward order without using size
   public void walkBackward()
   {
      if (tail == null)
      {
         System.out.println("List is empty");
         return;
      }
      Node<E> head = tail.next;
      Node<E> current = tail;
      do
      {
         System.out.print(current.element + " ");
         current = current.prev;
      }
      while (current != tail);
      System.out.println();
   }
   
   // Inserts newItem before the first occurrence of item
   public void addBefore(E item, E newItem)
   {
      if (tail == null)
      {
         System.out.println("List is empty");
         return;
      }
      Node<E> head = tail.next;
      Node<E> current = head;
      do
      {
         if (current.element.equals(item))
         {
            Node<E> newNode = new Node<E>(newItem);
            newNode.next = current;
            newNode.prev = current.prev;
            current.prev.next = newNode;
            current.prev = newNode;
            if (current == head)
            {
               tail.next = newNode;
            }
            size = size + 1;
            return;
         }
         current = current.next;
      }
      while (current != head);
      System.out.println("Item not found: " + item);
   }
   
   // Rotates the list so that the first element becomes the last
   public void rotate()
   {
      if (tail != null)
      {
         tail = tail.next;
      }
   }
}