import bg.sofia.uni.fmi.mjt.grading.simulator.Assistant;
import bg.sofia.uni.fmi.mjt.grading.simulator.Student;
import bg.sofia.uni.fmi.mjt.grading.simulator.grader.CodePostGrader;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        CodePostGrader grader = new CodePostGrader(2);

        Student student1 = new Student(1, "Gosho", grader);
        Student student2 = new Student(2, "Gosho", grader);
        Student student3 = new Student(3, "Gosho", grader);
        Student student4 = new Student(4, "Gosho", grader);
        Student student5 = new Student(5, "Gosho", grader);
        Student student6 = new Student(6, "Gosho", grader);
        Student student7 = new Student(7, "Gosho", grader);
        Student student8 = new Student(8, "Gosho", grader);
        Student student9 = new Student(9, "Gosho", grader);
        Student student10 = new Student(10, "Gosho", grader);
        Student student11 = new Student(11, "Gosho", grader);
        Student student12 = new Student(12, "Gosho", grader);
        Student student13 = new Student(13, "Gosho", grader);
        Student student14 = new Student(14, "Gosho", grader);
        Student student15 = new Student(15, "Gosho", grader);
        Student student16 = new Student(16, "Gosho", grader);

        Thread thread1 = new Thread(student1);
        Thread thread2 = new Thread(student2);
        Thread thread3 = new Thread(student3);
        Thread thread4 = new Thread(student4);
        Thread thread5 = new Thread(student5);
        Thread thread6 = new Thread(student6);
        Thread thread7 = new Thread(student7);
        Thread thread8 = new Thread(student8);
        Thread thread9 = new Thread(student9);
        Thread thread10 = new Thread(student10);
        Thread thread11 = new Thread(student11);
        Thread thread12 = new Thread(student12);
        Thread thread13 = new Thread(student13);
        Thread thread14 = new Thread(student14);
        Thread thread15 = new Thread(student15);
        Thread thread16 = new Thread(student16);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();
        thread9.start();
        thread10.start();
        thread11.start();
        thread12.start();
        thread13.start();
        thread14.start();
        thread15.start();
        thread16.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();
        thread6.join();
        thread7.join();
        thread8.join();
        thread9.join();
        thread10.join();
        thread11.join();
        thread12.join();
        thread13.join();
        thread14.join();
        thread15.join();
        thread16.join();

        Thread.sleep(2000);

        grader.finalizeGrading();

//        Assistant assistant = new Assistant("Ivo", grader);
//
//        Thread thread = new Thread(assistant);
//
//        thread.start();
//
//        thread.join();





        for (Assistant a : grader.getAssistants()) {
            System.out.println(a.getNumberOfGradedAssignments());
        }





        System.out.println("Hello world!");
    }
}