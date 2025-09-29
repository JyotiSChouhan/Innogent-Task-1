/*
1. Write a program that compares performance of ArrayList and 
   LinkedList for 10k, 50k and 100k insertions and deletions.
*/
import java.util.*;
class A {
	
        ArrayList<Integer> al = new ArrayList<>();
		LinkedList<Integer> ll = new LinkedList<>();
		
    void insertionTime(int n) 
	{
        long s1 = System.currentTimeMillis(); 
        for (int i = 0; i < n; i++) 
		{
            al.add(i); 
        }
        long e1 = System.currentTimeMillis();   
        System.out.println("-------------------------------------\nArrayList insert " + n + " elements: " + (e1 - s1) + " ms\n");
		
        long s2 = System.currentTimeMillis();
        for (int i = 0; i < n; i++) 
		{
            ll.add(i);
        }
        long e2 = System.currentTimeMillis();
        System.out.println("LinkedList insert " + n + " elements: " + (e2 - s2) + " ms");
    }

    void deletionTime(int n) 
	{
		long s1 = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            al.remove(0); 
        }
        long e1 = System.currentTimeMillis();
        System.out.println("-------------------------------------\nArrayList delete " + n + " elements: " + (e1 - s1) + " ms\n");
		
		long s2 = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            ll.remove(0); 
        }
        long e2 = System.currentTimeMillis();
        System.out.println("LinkedList delete " + n + " elements: " + (e2 - s2) + " ms");
    } 
}

class Comparison {
    public static void main(String[] args) 
	{
        A a1 = new A(); 

        a1.insertionTime(10000);
		a1.deletionTime(10000);
        a1.insertionTime(50000);
		a1.deletionTime(50000);		
        a1.insertionTime(100000);
		a1.deletionTime(100000);	
    }
}
