package bg.sofia.uni.fmi.mjt.grading.simulator;

import bg.sofia.uni.fmi.mjt.grading.simulator.assignment.Assignment;
import bg.sofia.uni.fmi.mjt.grading.simulator.assignment.AssignmentType;
import bg.sofia.uni.fmi.mjt.grading.simulator.grader.StudentGradingAPI;

import java.util.Random;

public class Student implements Runnable {
    private final int fn;
    private final String name;
    private final StudentGradingAPI studentGradingAPI;

    static final Random RANDOM = new Random();
    static final int MAX_DELAY = 1001;

    public Student(int fn, String name, StudentGradingAPI studentGradingAPI) {
        this.fn = fn;
        this.name = name;
        this.studentGradingAPI = studentGradingAPI;
    }

    @Override
    public void run() {
        Assignment toSubmit = new Assignment(fn, name, getRandomType());

        try {
            Thread.sleep(RANDOM.nextInt(MAX_DELAY));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        studentGradingAPI.submitAssignment(toSubmit);
    }

    public int getFn() {
        return fn;
    }

    public String getName() {
        return name;
    }

    public StudentGradingAPI getGrader() {
        return studentGradingAPI;
    }

    private AssignmentType getRandomType() {
        AssignmentType[] values = AssignmentType.values();
        int length = values.length;
        int randIndex = new Random().nextInt(length);
        return values[randIndex];
    }

}