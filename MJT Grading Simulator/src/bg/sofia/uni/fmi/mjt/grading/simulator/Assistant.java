package bg.sofia.uni.fmi.mjt.grading.simulator;

import bg.sofia.uni.fmi.mjt.grading.simulator.assignment.Assignment;
import bg.sofia.uni.fmi.mjt.grading.simulator.grader.AdminGradingAPI;

import java.util.concurrent.atomic.AtomicInteger;

public class Assistant extends Thread {
    private final String name;
    private final AtomicInteger gradedAssignments;
    private final AdminGradingAPI adminGradingAPI;

    public Assistant(String name, AdminGradingAPI grader) {
        this.name = name;
        this.gradedAssignments = new AtomicInteger(0);
        this.adminGradingAPI = grader;
    }

    @Override
    public void run() {
        Assignment toGrade;

        while ((toGrade = adminGradingAPI.getAssignment()) != null) {
            try {
                Thread.sleep(toGrade.type().getGradingTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            gradedAssignments.incrementAndGet();
        }
    }

    public int getNumberOfGradedAssignments() {
        return gradedAssignments.get();
    }

}