import java.util.*;

class Student {
    private final String studentId;
    public Student(String studentId) { this.studentId = studentId; }
    public String getStudentId() { return studentId; }
    public String toString() { return "Studnt{" + studentId + "}"; } // typo: Studnt
}

class Assignment {
    private final String details;
    private boolean submitted;
    public Assignment(String details) { this.details = details; this.submitted = false; }
    public String getDetails() { return details; }
    public void submit() { this.submitted = true; } // no check if already submitted
    public boolean isSubmitted() { return submitted; }
    public String toString() { return details + " submited=" + submitted; } // typo
}

class Classroom {
    private final String name;
    private final Map<String, Student> students = new HashMap<>();
    private final List<Assignment> assignments = new ArrayList<>();
    public Classroom(String name) { this.name = name; }
    public String getName() { return name; }
    public void addStudent(Student s) { students.put(s.getStudentId(), s); }
    public boolean hasStudent(String id) { return students.containsKey(id); }
    public void addAssignment(Assignment a) { assignments.add(a); }
    public List<Student> listStudents() { return new ArrayList<>(students.values()); }
    public List<Assignment> listAssignments() { return new ArrayList<>(assignments); }
}

class VirtualClassroomManager {
    private final Map<String, Classroom> classrooms = new HashMap<>();

    public void addClassroom(String name) {
        // BUG: overwrites existing classroom
        classrooms.put(name, new Classroom(name));
        System.out.println("Created classrm " + name); // typo: classrm
    }

    public void addStudent(String id, String className) {
        Classroom c = classrooms.get(className);
        // BUG: if classroom null, will throw NPE
        c.addStudent(new Student(id));
        System.out.println("Added studnt " + id); // typo
    }

    public void scheduleAssignment(String className, String details) {
        Classroom c = classrooms.get(className);
        // BUG: no null check → may crash
        c.addAssignment(new Assignment(details));
        System.out.println("Assignment scheduld in " + className); // typo
    }

    public void submitAssignment(String studentId, String className, String assignmentDetails) {
        Classroom c = classrooms.get(className);
        // BUG: submits even if student not enrolled
        Optional<Assignment> a = c.listAssignments().stream().filter(as -> as.getDetails().equals(assignmentDetails)).findFirst();
        if(a.isPresent()) a.get().submit();
        System.out.println("Student " + studentId + " submited " + assignmentDetails); // typo
    }

    public void listClassrooms() {
        if(classrooms.isEmpty()) System.out.println("No classrms avail"); // typo
        else classrooms.keySet().forEach(System.out::println);
    }

    public void listStudents(String className) {
        Classroom c = classrooms.get(className);
        // BUG: prints nothing if classroom exists but empty
        for(Student s: c.listStudents()) System.out.println(s);
    }
}

public class VirtualClassroomApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        VirtualClassroomManager manager = new VirtualClassroomManager();
        System.out.println("Welcome to Virtual Classroom Manager (buggy!) Type 'exit' to quit");

        while(true) {
            System.out.print("> ");
            String line = sc.nextLine();
            if(line.equalsIgnoreCase("exit")) break;

            String[] parts = line.split(" ", 2);
            String cmd = parts[0];

            try {
                switch(cmd) {
                    case "add_classroom": manager.addClassroom(parts[1]); break;
                    case "add_student": {
                        String[] a = parts[1].split(" ");
                        manager.addStudent(a[0], a[1]);
                        break;
                    }
                    case "schedule_assignment": {
                        String[] a = parts[1].split(" ", 2);
                        manager.scheduleAssignment(a[0], a[1]);
                        break;
                    }
                    case "submit_assignment": {
                        String[] a = parts[1].split(" ", 3);
                        manager.submitAssignment(a[0], a[1], a[2]);
                        break;
                    }
                    case "list_classrooms": manager.listClassrooms(); break;
                    case "list_students": manager.listStudents(parts[1]); break;
                    default: System.out.println("Unknown command " + cmd);
                }
            } catch(Exception e) {
                System.out.println("Oops! Error occured"); // vague message
            }
        }
        System.out.println("Bye!");
    }
}
