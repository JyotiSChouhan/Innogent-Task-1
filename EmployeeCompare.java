/*2. Create Employee class (id, name, department, salary).
	Sort employees by:
		Department → Name → Salary.
		Salary (descending).
		Use both Comparable and Comparator.
		Use Iterator to traverse.
*/
import java.util.*;
class Employee implements Comparable 
{
	int id;
	String name;
	String department;
	double salary;
	Employee(int id,String name,String department,double salary)
	{
		this.id=id;
		this.name=name;
		this.department=department;
		this.salary=salary;
	}
	public String toString() 
	{
        return "ID: " + id + ", Dept: " + department +" Name: " + name + ", Salary: " + salary;
    }
	public int compareTo(Object o1)
	{
		if(o1 instanceof Employee)
		{
			Employee o=(Employee)o1;
        int deptCompare = this.department.compareTo(o.department);
        if (deptCompare != 0) return deptCompare;

        int nameCompare = this.name.compareTo(o.name);
        if (nameCompare != 0) return nameCompare;

        return new Double(salary).compareTo(o.salary);		
		}
		else 
		{
			throw new UnmatchableClass();
		}
	}
}
class UnmatchableClass extends ClassCastException{}
class EmployeeCompare
{
public static void main(String ar[]) throws Exception
{
	ArrayList al=new ArrayList();
	al.add(new Employee(101,"a","abc",10.1));
	al.add(new Employee(102,"a","def",20.2));
	al.add(new Employee(106,"a","def",200.2));
	al.add(new Employee(103,"c","abc",30.3));
	al.add(new Employee(104,"d","jkl",30.4));
	al.add(new Employee(105,"e","mno",50.5));

	System.out.println("Unsorted data :");
        Iterator<Employee> it1 = al.iterator();
        while (it1.hasNext()) 
		{
            System.out.println(it1.next());
        }
	Collections.sort(al);
	System.out.println("Sorted data :");
        Iterator<Employee> it2 = al.iterator();
        while (it2.hasNext()) 
		{
            System.out.println(it2.next());
        }
}
}