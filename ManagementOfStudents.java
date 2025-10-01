import java.io.*;
import java.util.*;

class Student 
{
    int id;
    String name;
    int classId;
    int marks;
    String gender;
    int age;
    String status; 
    int rank;

    Student(int id, String name, int classId, int marks, String gender, int age) 
	{
        this.id = id;
        this.name = name;
        this.classId = classId;
        this.marks = marks;
        this.gender = gender;
        this.age = age;
    }

    int getId() { return id; }
    String getName() { return name; }
    int getClassId() { return classId; }
    int getMarks() { return marks; }
    String getGender() { return gender; }
    int getAge() { return age; }
    String getStatus() { return status; }
    int getRank() { return rank; }

    void setStatus(String status) { this.status = status; }
    void setRank(int rank) { this.rank = rank; }
	
	static void addStudent(List<Student> students, Student s) 
	{
		try
	{
    if (s.getAge() > 20) {
        throw new InvalidAgeException();
    }
    if (s.getMarks() < 0 || s.getMarks() > 100) {
        throw new InvalidMarksException();
    }
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return;
	}
    students.add(s);
	}
	static void deleteStudent(List<Student> students, int id)
	{
    boolean removed = students.removeIf(s -> s.getId() == id);
    if (!removed) {
		try{   throw new StudentNotFoundException();}
		catch(Exception e){ e.printStackTrace();
		return;}
    }
}
}

class ClassRoom 
{
    int id;
    String name;

    ClassRoom(int id, String name) {
        this.id = id;
        this.name = name;
    }

    int getId() { return id; }
    String getName() { return name; }
}

class Address 
{
    int id;
    String pincode;
    String city;
    int studentId;

    Address(int id, String pincode, String city, int studentId) {
        this.id = id;
        this.pincode = pincode;
        this.city = city;
        this.studentId = studentId;
    }

    int getId() { return id; }
    String getPincode() { return pincode; }
    String getCity() { return city; }
    int getStudentId() { return studentId; }
}

class FileHandler 
{

    static void saveStudents(List<Student> students, String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Student s : students) {
                bw.write(s.getId() + "," + s.getName() + "," + s.getClassId() + "," +
                        s.getMarks() + "," + s.getGender() + "," + s.getAge() + "," +
                        s.getStatus() + "," + s.getRank());
                bw.newLine();
            }
            System.out.println("Students saved at: " + filename);
        } catch (IOException e) { e.printStackTrace(); }
    }

    static void saveClasses(List<ClassRoom> classes, String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (ClassRoom c : classes) {
                bw.write(c.getId() + "," + c.getName());
                bw.newLine();
            }
            System.out.println("Classes saved at: " + filename);
        } catch (IOException e) { e.printStackTrace(); }
    }

    static void saveAddresses(List<Address> addresses, String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Address a : addresses) {
                bw.write(a.getId() + "," + a.getPincode() + "," + a.getCity() + "," + a.getStudentId());
                bw.newLine();
            }
            System.out.println("Addresses saved at: " + filename);
        } catch (IOException e) { e.printStackTrace(); }
    }

    static void saveTop5(List<Student> students, String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            int count = 0;
            for (Student s : students) {
                if ("Pass".equals(s.getStatus())) { 
                    bw.write("Rank " + s.getRank() + ": " + s.getName() + " (" + s.getMarks() + " marks)");
                    bw.newLine();
                    count++;
                    if (count == 5) break;
                }
            }
            System.out.println("Top 5 students saved at: " + filename);
        } catch (IOException e) { e.printStackTrace(); }
    }

    static List<Student> readStudents(String filename) {
        List<Student> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Student s = new Student(
                        Integer.parseInt(parts[0]),
                        parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3]),
                        parts[4],
                        Integer.parseInt(parts[5])
                );
                s.setStatus(parts[6]);
                s.setRank(Integer.parseInt(parts[7]));
                Student.addStudent(students, s);
            }
        } catch (IOException e) { e.printStackTrace(); }
        return students;
    }

    static List<ClassRoom> readClasses(String filename) {
        List<ClassRoom> classes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                classes.add(new ClassRoom(Integer.parseInt(parts[0]), parts[1]));
            }
        } catch (IOException e) { e.printStackTrace(); }
        return classes;
    }

    static List<Address> readAddresses(String filename) {
        List<Address> addresses = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                addresses.add(new Address(
                        Integer.parseInt(parts[0]),
                        parts[1],
                        parts[2],
                        Integer.parseInt(parts[3])
                ));
            }
        } catch (IOException e) { e.printStackTrace(); }
        return addresses;
    }
}

public class ManagementOfStudents 
{

    public static void main(String[] args) {

        ArrayList<Student> students = new ArrayList<>();
        ArrayList<ClassRoom> classes = new ArrayList<>();
        ArrayList<Address> addresses = new ArrayList<>();
		
		Scanner sc = new Scanner(System.in);
try {
    System.out.print("Enter student age: ");
    int age = sc.nextInt();  
} catch (InputMismatchException e) {
    System.out.println("Invalid input! Please enter a number.");
    sc.nextLine(); 
}
		

        classes.add(new ClassRoom(1, "A"));
        classes.add(new ClassRoom(2, "B"));
        classes.add(new ClassRoom(3, "C"));
        classes.add(new ClassRoom(4, "X"));
        classes.add(new ClassRoom(5, "Y"));
        classes.add(new ClassRoom(6, "Z"));
		Student.addStudent(students, new Student(100,"Ravi",18,85,"M",11));
        Student.addStudent(students, new Student(101,"Rudra",2,80,"M",18));
		Student.addStudent(students, new Student(102,"Sourabh",1,805,"M",16));
		Student.addStudent(students, new Student(103,"Jyoti",3,15,"F",1));
		Student.addStudent(students, new Student(104,"Rajneesh",2,80,"M",12));
		Student.addStudent(students, new Student(105,"Raghav",1,90,"M",13));
		Student.addStudent(students, new Student(107,"Shruti",2,77,"M",17));
		Student.addStudent(students, new Student(108,"Fake",3,10,"M",100));
		Student.addStudent(students, new Student(106,"Jyoti",3,15,"F",1));
		Student.addStudent(students, new Student(109,"Jyoti",3,100,"F",1));
		Student.addStudent(students, new Student(114,"Rajneesh",2,8,"M",120));
		Student.addStudent(students, new Student(115,"Raghav",1,98,"M",3));

        // students.add(new Student(1, "stud1", 1, 88, "F", 10));
        // students.add(new Student(2, "stud1", 1, 70, "F", 10));
        // students.add(new Student(3, "stud3", 2, 88, "M", 12)); 
        // students.add(new Student(3, "stud3", 2, 88, "M", 12)); 
        // students.add(new Student(3, "stud3", 6, 98, "M", 12)); 
        // students.add(new Student(3, "stud3", 2, 88, "M", 12)); 
        // students.add(new Student(3, "stud3", 2, 88, "M", 12)); 
        // students.add(new Student(3, "stud3", 2, 88, "M", 12)); 
        // students.add(new Student(4, "stud4", 2, 55, "M", 13)); 
        // students.add(new Student(5, "stud1", 1, 30, "F", 44)); 
        // students.add(new Student(6, "stud6", 3, 30, "F", 33)); 
        // students.add(new Student(7, "stud7", 3, 10, "F", 2)); 
        // students.add(new Student(8, "stud6", 3, 0, "M", 11));

        students.removeIf(s -> s.getAge() > 20);

        for (Student s : students) {
            if (s.getMarks() < 50) s.setStatus("Fail");
            else s.setStatus("Pass");
        }

        students.sort((s1, s2) -> s2.getMarks() - s1.getMarks());
        int rank = 1;
        for (Student s : students) {
            s.setRank(rank++);
        }

        addresses.add(new Address(1, "452002", "indore", 1));
        addresses.add(new Address(2, "422002", "delhi", 1));
        addresses.add(new Address(3, "442002", "indore", 2));
        addresses.add(new Address(4, "462002", "delhi", 3));
        addresses.add(new Address(5, "472002", "indore", 4));
        addresses.add(new Address(6, "452002", "indore", 5));
        addresses.add(new Address(7, "452002", "delhi", 5));
        addresses.add(new Address(8, "482002", "mumbai", 6));
        addresses.add(new Address(9, "482002", "bhopal", 7));
        addresses.add(new Address(10, "482002", "jammu", 8));
		
		Student.deleteStudent(students,102);
		Student.deleteStudent(students,104);

FileHandler.saveStudents(students, "students.csv");
FileHandler.saveClasses(classes, "classes.csv");
FileHandler.saveAddresses(addresses, "addresses.csv");
FileHandler.saveTop5(students, "top5.csv");


        //List<Student> readStudents = FileHandler.readStudents("E:/students.txt");
		List<Student> loadedStudents = FileHandler.readStudents("students.csv");

        System.out.println("\n--- Students loaded from file ---");
        for (Student s : loadedStudents	) {
            System.out.println(s.getName() + " | Marks: " + s.getMarks() + " | Status: " + s.getStatus() + " | Rank: " + s.getRank());
        }
    }
}

class InvalidAgeException extends RuntimeException
{
    public InvalidAgeException() 
	{
        super("Student age cannot be greater than 20");
    }
}

class InvalidMarksException extends RuntimeException 
{
    public InvalidMarksException() 
	{
        super("Marks must be between 0 and 100");
    }
}

class StudentNotFoundException extends RuntimeException 
{
    public StudentNotFoundException() 
	{
        super("Student with this ID not found!");
    }
}