import java.util.*;
import java.util.stream.*;

/*
    * Insert students with age validation (age > 20 rejected)
    * Marks evaluation => Pass/Fail
    * Ranking by marks (highest = rank 1)
    * Find by pincode, city, class with optional filters (gender, age, class, city, pincode)
    * Get passed / failed students with optional filters
    * Delete student (also delete corresponding addresses). If a class has no students left, delete class.
    * Pagination reads with filters and ordering (by name or marks)
*/

class ClassRoom {
    int id;
    String name;

    ClassRoom(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

class Address {
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
}

class Student {
    int id;
    String name;
    int classId;
    int marks;
    String gender; 
    int age;

    String status; 
    int rank;      

    Student(int id, String name, int classId, int marks, String gender, int age) {
        this.id = id;
        this.name = name;
        this.classId = classId;
        this.marks = marks;
        this.gender = gender;
        this.age = age;
        this.status = null;
        this.rank = 0;
    }
}

class StudentManagementSystem 
{
    ArrayList<ClassRoom> classes = new ArrayList<>();
    ArrayList<Student> students = new ArrayList<>();
    ArrayList<Address> addresses = new ArrayList<>();
    
	public static void main(String[] args) {
        StudentManagementSystem sys = new StudentManagementSystem();
        sys.AddData();

        System.out.println("\n-- All accepted students --");
        for (Student s : sys.students) sys.printStudentBrief(s);

        System.out.println("\n-- Find students by pincode 482002 --");
        List<Student> byPincode = sys.findStudentsByPincode("482002", null, null, null);
        for (Student s : byPincode) sys.printStudentBrief(s);

        System.out.println("\n-- Find students by city Indore (female only) --");
        List<Student> byCityFemale = sys.findStudentsByCity("Indore", "F", null, null);
        for (Student s : byCityFemale) sys.printStudentBrief(s);

        System.out.println("\n-- Passed students --");
        List<Student> passed = sys.getPassedStudents(null, null, null, null, null);
        for (Student s : passed) sys.printStudentBrief(s);

        System.out.println("\n-- Failed students --");
        List<Student> failed = sys.getFailedStudents(null, null, null, null, null);
        for (Student s : failed) sys.printStudentBrief(s);

        System.out.println("\n-- Pagination examples --");
        List<Student> pg1 = sys.readStudentsPaginated("F", null, null, null, null, null, 1, 9);
        System.out.println("Female students 1-9:");
        for (Student s : pg1) sys.printStudentBrief(s);

        List<Student> pg2 = sys.readStudentsPaginated("F", null, null, null, null, "name", 7, 8);
        System.out.println("Female students 7-8 ordered by name:");
        for (Student s : pg2) sys.printStudentBrief(s);

        List<Student> pg3 = sys.readStudentsPaginated("F", null, null, null, null, "marks", 1, 5);
        System.out.println("Female students 1-5 ordered by marks:");
        for (Student s : pg3) sys.printStudentBrief(s);

        System.out.println("\n-- Delete student id=1 --");
        boolean deleted = sys.deleteStudentById(1);
        System.out.println("Deleted? " + deleted);

        System.out.println("\n-- Students after delete --");
        for (Student s : sys.students) sys.printStudentBrief(s);

        System.out.println("\n-- Classes after delete (if empty classes removed) --");
        for (ClassRoom c : sys.classes) {
            System.out.println("Class id:" + c.id + " name:" + c.name);
        }
    }

    void AddData() 
	{
        classes.add(new ClassRoom(1, "A"));
        classes.add(new ClassRoom(2, "B"));
        classes.add(new ClassRoom(3, "C"));
        classes.add(new ClassRoom(4, "D"));

        addStudent(new Student(1, "aaa", 1, 88, "F", 10));
        addStudent(new Student(2, "bbb", 1, 70, "F", 11));
        addStudent(new Student(3, "ccc", 2, 88, "F", 10)); 
        addStudent(new Student(4, "ddd", 1, 88, "M", 33)); 
        addStudent(new Student(5, "aaa", 1, 30, "F", 12)); 
        addStudent(new Student(6, "eee", 3, 30, "F", 13)); 
        addStudent(new Student(7, "bbb", 3, 10, "F", 22)); 
        addStudent(new Student(8, "fff", 3, 0, "M", 11));  

        addresses.add(new Address(1, "452002", "Indore", 1));
        addresses.add(new Address(2, "422002", "Delhi", 1));
        addresses.add(new Address(3, "442002", "Indore", 2));
        addresses.add(new Address(4, "462002", "Delhi", 3));
        addresses.add(new Address(5, "472002", "Indore", 4));
        addresses.add(new Address(6, "452002", "Indore", 5));
        addresses.add(new Address(7, "452002", "Delhi", 5));
        addresses.add(new Address(8, "482002", "Mumbai", 6));
        addresses.add(new Address(9, "482002", "Bhopal", 7));
        addresses.add(new Address(10, "482002", "Indore", 8));

        assignRanks();
    }

    void addStudent(Student s) 
	{
        if (s.age > 20) 
		{
            System.out.println("Insert rejected (age > 20): " + s.name + " (age=" + s.age + ")");
            return;
        }
        s.status = (s.marks < 50) ? "Fail" : "Pass";
        students.add(s);
        assignRanks();
    }

    void assignRanks() 
	{
        ArrayList<Student> sorted = new ArrayList<>(students);
        Collections.sort(sorted, new Comparator<Student>() {
            public int compare(Student a, Student b) {
                return Integer.compare(b.marks, a.marks); 
            }
        });
        int r = 1;
        for (Student s : sorted) 
		{
            s.rank = r++;
        }
        Map<Integer, Integer> idToRank = new HashMap<>();
        for (Student s : sorted) idToRank.put(s.id, s.rank);
        for (Student s : students) s.rank = idToRank.get(s.id);
    }

    String getClassNameById(int classId) 
	{
        for (ClassRoom c : classes) {
            if (c.id == classId) return c.name;
        }
        return "";
    }

    List<Address> getAddressesForStudent(int studentId) {
        List<Address> res = new ArrayList<>();
        for (Address a : addresses) {
            if (a.studentId == studentId) res.add(a);
        }
        return res;
    }

    List<Student> findStudentsByPincode(String pincode, String genderFilter, Integer ageFilter, String classFilter) 
	{
        Set<Integer> studentIds = new HashSet<>();
        for (Address a : addresses) 
		{
            if (a.pincode.equals(pincode)) studentIds.add(a.studentId);
        }
        List<Student> res = new ArrayList<>();
        for (Student s : students) 
		{
            if (!studentIds.contains(s.id)) continue;
            if (genderFilter != null && !s.gender.equalsIgnoreCase(genderFilter)) continue;
            if (ageFilter != null && s.age != ageFilter) continue;
            if (classFilter != null && !getClassNameById(s.classId).equalsIgnoreCase(classFilter)) continue;
            res.add(s);
        }
        return res;
    }

    List<Student> findStudentsByCity(String city, String genderFilter, Integer ageFilter, String classFilter) {
        Set<Integer> studentIds = new HashSet<>();
        for (Address a : addresses) {
            if (a.city.equalsIgnoreCase(city)) studentIds.add(a.studentId);
        }
        List<Student> res = new ArrayList<>();
        for (Student s : students) {
            if (!studentIds.contains(s.id)) continue;
            if (genderFilter != null && !s.gender.equalsIgnoreCase(genderFilter)) continue;
            if (ageFilter != null && s.age != ageFilter) continue;
            if (classFilter != null && !getClassNameById(s.classId).equalsIgnoreCase(classFilter)) continue;
            res.add(s);
        }
        return res;
    }

    List<Student> findStudentsByClass(String className, String genderFilter, Integer ageFilter, String cityFilter, String pincodeFilter) {
        Set<Integer> classIds = new HashSet<>();
        for (ClassRoom c : classes) {
            if (c.name.equalsIgnoreCase(className)) classIds.add(c.id);
        }
        List<Student> res = new ArrayList<>();
        for (Student s : students) {
            if (!classIds.contains(s.classId)) continue;
            if (genderFilter != null && !s.gender.equalsIgnoreCase(genderFilter)) continue;
            if (ageFilter != null && s.age != ageFilter) continue;
            if (cityFilter != null) {
                boolean any = false;
                for (Address a : getAddressesForStudent(s.id)) {
                    if (a.city.equalsIgnoreCase(cityFilter)) { any = true; break; }
                }
                if (!any) continue;
            }
            if (pincodeFilter != null) {
                boolean any = false;
                for (Address a : getAddressesForStudent(s.id)) {
                    if (a.pincode.equals(pincodeFilter)) { any = true; break; }
                }
                if (!any) continue;
            }
            res.add(s);
        }
        return res;
    }

    List<Student> getPassedStudents(String genderFilter, Integer ageFilter, String classFilter, String cityFilter, String pincodeFilter) 
	{
        return filterByStatus("Pass", genderFilter, ageFilter, classFilter, cityFilter, pincodeFilter);
    }

    List<Student> getFailedStudents(String genderFilter, Integer ageFilter, String classFilter, String cityFilter, String pincodeFilter) 
	{
        return filterByStatus("Fail", genderFilter, ageFilter, classFilter, cityFilter, pincodeFilter);
    }

    List<Student> filterByStatus(String status, String genderFilter, Integer ageFilter, String classFilter, String cityFilter, String pincodeFilter) 
	{
        List<Student> res = new ArrayList<>();
        for (Student s : students) {
            if (!status.equalsIgnoreCase(s.status)) continue;
            if (genderFilter != null && !s.gender.equalsIgnoreCase(genderFilter)) continue;
            if (ageFilter != null && s.age != ageFilter) continue;
            if (classFilter != null && !getClassNameById(s.classId).equalsIgnoreCase(classFilter)) continue;
            if (cityFilter != null) {
                boolean any = false;
                for (Address a : getAddressesForStudent(s.id)) {
                    if (a.city.equalsIgnoreCase(cityFilter)) { any = true; break; }
                }
                if (!any) continue;
            }
            if (pincodeFilter != null) {
                boolean any = false;
                for (Address a : getAddressesForStudent(s.id)) {
                    if (a.pincode.equals(pincodeFilter)) { any = true; break; }
                }
                if (!any) continue;
            }
            res.add(s);
        }
        return res;
    }

    boolean deleteStudentById(int studentId) {
        boolean removed = false;
        Iterator<Student> sit = students.iterator();
        while (sit.hasNext()) {
            Student s = sit.next();
            if (s.id == studentId) {
                sit.remove();
                removed = true;
                break;
            }
        }
        if (!removed) return false;

        Iterator<Address> ait = addresses.iterator();
        while (ait.hasNext()) {
            Address a = ait.next();
            if (a.studentId == studentId) ait.remove();
        }

        Set<Integer> usedClassIds = new HashSet<>();
        for (Student s : students) usedClassIds.add(s.classId);

        Iterator<ClassRoom> cit = classes.iterator();
        while (cit.hasNext()) {
            ClassRoom c = cit.next();
            if (!usedClassIds.contains(c.id)) {
                cit.remove();
            }
        }

        assignRanks();
        return true;
    }

    List<Student> readStudentsPaginated(String genderFilter, Integer ageFilter, String classFilter,
                                        String cityFilter, String pincodeFilter,
                                        String orderBy, int startRecord, int endRecord) 
										{
        List<Student> filtered = new ArrayList<>();
        for (Student s : students) {
            if (genderFilter != null && !s.gender.equalsIgnoreCase(genderFilter)) continue;
            if (ageFilter != null && s.age != ageFilter) continue;
            if (classFilter != null && !getClassNameById(s.classId).equalsIgnoreCase(classFilter)) continue;
            if (cityFilter != null) {
                boolean any = false;
                for (Address a : getAddressesForStudent(s.id)) {
                    if (a.city.equalsIgnoreCase(cityFilter)) { any = true; break; }
                }
                if (!any) continue;
            }
            if (pincodeFilter != null) {
                boolean any = false;
                for (Address a : getAddressesForStudent(s.id)) {
                    if (a.pincode.equals(pincodeFilter)) { any = true; break; }
                }
                if (!any) continue;
            }
            filtered.add(s);
        }

        if ("name".equalsIgnoreCase(orderBy)) {
            Collections.sort(filtered, new Comparator<Student>() {
                public int compare(Student a, Student b) { return a.name.compareToIgnoreCase(b.name); }
            });
        } else if ("marks".equalsIgnoreCase(orderBy)) {
            Collections.sort(filtered, new Comparator<Student>() {
                public int compare(Student a, Student b) { return Integer.compare(b.marks, a.marks); } 
            });
        } 

        int n = filtered.size();
        if (startRecord < 1) startRecord = 1;
        if (endRecord > n) endRecord = n;
        if (startRecord > endRecord) return new ArrayList<>(); 

        int fromIndex = startRecord - 1;
        int toIndex = endRecord; 

        return new ArrayList<>(filtered.subList(fromIndex, toIndex));
    }

    void printStudentBrief(Student s) {
        String className = getClassNameById(s.classId);
        List<Address> addrs = getAddressesForStudent(s.id);
        String addrStr = addrs.stream()
                              .map(a -> a.city + "(" + a.pincode + ")")
                              .collect(Collectors.joining(", "));
        System.out.println("ID:" + s.id + " Name:" + s.name + " Class:" + className +
                           " Marks:" + s.marks + " Status:" + s.status + " Rank:" + s.rank +
                           " Gender:" + s.gender + " Age:" + s.age + " Addr:[" + addrStr + "]");
    }
}