// StudentManager.java
import java.util.*;

public class StudentManager {
    private final List<Student> students = new ArrayList<>();

    
    public void addStudent(Student s) {
        students.add(s);
    }

    
    public void updateStudent(int roll, String erpId, String name, String branch, String course, double marks) {
        for (Student s : students) {
            if (s.getRollNo() == roll) {
                s.setName(name);
                s.setBranch(branch);
                s.setCourse(course);
                s.setMarks(marks);
                return;
            }
        }
    }

    
    public boolean deleteStudent(int roll) {
        return students.removeIf(s -> s.getRollNo() == roll);
    }

    
    public boolean existsRoll(int roll) {
        return students.stream().anyMatch(s -> s.getRollNo() == roll);
    }

    
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    
    public void sortByName() {
        students.sort(Comparator.comparing(Student::getName));
    }

    
    public void sortByMarksDescending() {
        students.sort((a, b) -> Double.compare(b.getMarks(), a.getMarks()));
    }

    
    public void clearAll() {
        students.clear();
    }
}
