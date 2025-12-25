// Student.java
public class Student {
    private int rollNo;
    private String erpId;
    private String name;
    private String branch;
    private String course;
    private double marks;
    private String grade;

    public Student(int rollNo, String erpId, String name, String branch, String course, double marks) {
        this.rollNo = rollNo;
        this.erpId = erpId;
        this.name = name;
        this.branch = branch;
        this.course = course;
        this.marks = marks;
        this.grade = calculateGrade(marks);
    }

    private String calculateGrade(double marks) {
        if (marks >= 90) return "A+";
        else if (marks >= 80) return "A";
        else if (marks >= 70) return "B";
        else if (marks >= 60) return "C";
        else if (marks >= 50) return "D";
        else return "F";
    }

    
    public int getRollNo() { return rollNo; }
    public String getErpId() { return erpId; }
    public String getName() { return name; }
    public String getBranch() { return branch; }
    public String getCourse() { return course; }
    public double getMarks() { return marks; }
    public String getGrade() { return grade; }

    public void setName(String name) { this.name = name; }
    public void setBranch(String branch) { this.branch = branch; }
    public void setCourse(String course) { this.course = course; }
    public void setMarks(double marks) {
        this.marks = marks;
        this.grade = calculateGrade(marks);
    }

    @Override
    public String toString() {
        return rollNo + "," + erpId + "," + name + "," + branch + "," + course + "," + marks + "," + grade;
    }
}
