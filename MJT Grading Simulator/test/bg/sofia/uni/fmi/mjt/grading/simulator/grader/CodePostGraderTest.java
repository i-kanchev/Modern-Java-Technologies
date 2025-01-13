package bg.sofia.uni.fmi.mjt.grading.simulator.grader;

import bg.sofia.uni.fmi.mjt.grading.simulator.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CodePostGraderTest {

    @Test
    void mainTest() throws InterruptedException {
        CodePostGrader grader = new CodePostGrader(2);

        Student student1 = new Student(14, "Gosho", grader);
        Student student2 = new Student(46, "Ivo", grader);
        Student student3 = new Student(27, "Niki", grader);

        Thread thread1 = new Thread(student1);
        Thread thread2 = new Thread(student2);
        Thread thread3 = new Thread(student3);

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        Thread.sleep(500);

        grader.finalizeGrading();

        assertEquals(3, grader.getSubmittedAssignmentsCount());
    }
}