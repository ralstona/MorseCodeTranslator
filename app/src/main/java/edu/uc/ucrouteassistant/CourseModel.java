package edu.uc.ucrouteassistant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CourseModel {
    private String CourseName;
    private String startDate;
    private String endDate;
    private List<DaysModel> days ;

    public CourseModel(){
        days = new ArrayList<DaysModel>();
        DaysModel dummy = new DaysModel();

        for (int i = 0; i < 5; i++){
            days.add(dummy);
        }
    }

    public List<DaysModel> getDays() {

        return days;
    }

    public void setDay(DaysModel day) {
        List<DaysModel> helper = new ArrayList<DaysModel>();
        DaysModel dummy = new DaysModel();
        for (int i = 0; i < 5; i++){
            helper.add(dummy);
        }
        for(DaysModel pastDay : this.days){
            helper.set(this.days.indexOf(pastDay), pastDay);
        }
        this.days = helper;

        switch (day.getDayOfTheWeek()){
            case "Monday":
                this.days.set(0, day);
            break;
            case "Tuesday":
                this.days.set(1, day);
                break;
            case "Wednesday":
                this.days.set(2, day);
                break;
            case "Thursday":
                this.days.set(3, day);
                break;
            case "Friday":
                this.days.set(4, day);
        }
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
