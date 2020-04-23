package edu.uc.ucrouteassistant;

import androidx.lifecycle.ViewModel;

public class SchedulerViewModel extends ViewModel {

    private CourseModel course;

    public CourseModel getCourse() {
        return course;
    }

    public void setCourse(CourseModel course) {
        this.course = course;
    }
}
