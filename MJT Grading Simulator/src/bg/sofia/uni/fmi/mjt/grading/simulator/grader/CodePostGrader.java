package bg.sofia.uni.fmi.mjt.grading.simulator.grader;

import bg.sofia.uni.fmi.mjt.grading.simulator.Assistant;
import bg.sofia.uni.fmi.mjt.grading.simulator.assignment.Assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class CodePostGrader implements AdminGradingAPI {
    private final List<Assistant> listOfAssistants;
    private final BlockingQueue<Assignment> listOfUngradedAssignments;
    private final AtomicInteger submittedAssignmentsCount;
    private boolean isAssignmentClosed;

    public CodePostGrader(int numberOfAssistants) {
        this.listOfAssistants = new ArrayList<>(numberOfAssistants);
        this.listOfUngradedAssignments = new LinkedBlockingQueue<>();
        this.submittedAssignmentsCount = new AtomicInteger(0);
        this.isAssignmentClosed = false;


        for (int i = 0; i < numberOfAssistants; i++) {
            listOfAssistants.add(new Assistant("Assistant" + i, this));
        }

        for (Assistant assistant : listOfAssistants) {
            assistant.start();
        }
    }

    @Override
    public synchronized Assignment getAssignment() {
        while (listOfUngradedAssignments.isEmpty() && !isAssignmentClosed) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return listOfUngradedAssignments.poll();
    }

    @Override
    public synchronized int getSubmittedAssignmentsCount() {
        return submittedAssignmentsCount.get();
    }

    @Override
    public synchronized void finalizeGrading() {
        isAssignmentClosed = true;
        notifyAll();
    }

    @Override
    public synchronized List<Assistant> getAssistants() {
        return List.copyOf(listOfAssistants);
    }

    @Override
    public void submitAssignment(Assignment assignment) {
        synchronized (this) {
            if (!isAssignmentClosed) {
                listOfUngradedAssignments.add(assignment);
                submittedAssignmentsCount.incrementAndGet();
            }
            notify();
        }
    }
}
