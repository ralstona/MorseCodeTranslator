package edu.uc.ucrouteassistant;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditScheduleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DaysModel days = new DaysModel();
    private SchedulerViewModel model;
    private EditText startDate;
    private EditText endDate;
    private CheckBox cbMonday;
    private CheckBox cbTuesday;
    private CheckBox cbWednesday;
    private CheckBox cbThursday;
    private CheckBox cbFriday;
    private EditText edTxtCourseName;
    private boolean finished = true;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();



    public EditScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditScheduleFragment newInstance(String param1, String param2) {
        EditScheduleFragment fragment = new EditScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume(){
        super.onResume();

            /*Bundle bundle = new Bundle();
            bundle = requireArguments();
            String day = bundle.getString("Day");*/
        if ( model != null && model.getCourse() != null && model.getCourse().getDays() != null) {
            for (DaysModel day : model.getCourse().getDays()){
                if (day != null && day.getDayOfTheWeek() != null) {
                    switch (day.getDayOfTheWeek()) {
                        case "Monday":
                            cbMonday.setChecked(true);
                            break;
                        case "Tuesday":
                            cbTuesday.setChecked(true);
                            break;
                        case "Wednesday":
                            cbWednesday.setChecked(true);
                            break;
                        case "Thursday":
                            cbThursday.setChecked(true);
                            break;
                        case "Friday":
                            cbFriday.setChecked(true);
                            break;
                        case "null":
                            break;
                    }
                }
            }
        }

        if ( model != null && model.getCourse() != null && model.getCourse().getStartDate() != null && !model.getCourse().getStartDate().isEmpty()) {
            if (checkDates()){
                startDate.setText(model.getCourse().getStartDate());
            }
            else{
                startDate.setText(model.getCourse().getStartDate());
                startDate.setTextColor(Color.RED);
            }

        }

        if ( model != null && model.getCourse() != null && model.getCourse().getEndDate() != null && !model.getCourse().getEndDate().isEmpty()) {
            if (checkDates()){
                endDate.setText(model.getCourse().getEndDate());
            }
            else{
                endDate.setText(model.getCourse().getEndDate());
                endDate.setTextColor(Color.RED);
            }
        }

        if (model != null && model.getCourse() != null && model.getCourse().getCourseName() != null){
            edTxtCourseName.setText(model.getCourse().getCourseName());
        }
    }

    private void navigateToNextFragment(Fragment fragment, String tag){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentListContainer, fragment, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View fragment = inflater.inflate(R.layout.fragment_edit_schedule, container, false);

        model = ViewModelProviders.of(this.getActivity()).get(SchedulerViewModel.class);

        edTxtCourseName = fragment.findViewById(R.id.edTxtCourseName);
        edTxtCourseName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CourseModel course = new CourseModel();
                course = model.getCourse();
                course.setCourseName(edTxtCourseName.getText().toString());
                model.setCourse(course);
            }
        });
        edTxtCourseName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                CourseModel course = new CourseModel();
                course = model.getCourse();
                course.setCourseName(edTxtCourseName.getText().toString());
                model.setCourse(course);
            }
        });

        startDate = fragment.findViewById(R.id.edTxtStartDate);
        startDate.setInputType(InputType.TYPE_NULL);

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToNextFragment(new StartDate(), "StartDate");
            }
        });
        startDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                navigateToNextFragment(new StartDate(), "StartDate");
            }
        });

        endDate = fragment.findViewById(R.id.edTxtEndDate);
        endDate.setInputType(InputType.TYPE_NULL);

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToNextFragment(new EndDate(), "EndDate");
            }
        });
        endDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                navigateToNextFragment(new EndDate(), "EndDate");
            }
        });

        cbMonday = fragment.findViewById(R.id.cbMonday);
        cbMonday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cbMonday.isChecked()) {
                    editDaySchedule("Monday");
                }
                else{
                    createDaySchedule("Monday");
                }
            }
        });

        cbTuesday = fragment.findViewById(R.id.cbTuesday);
        cbTuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cbTuesday.isChecked()){
                    editDaySchedule("Tuesday");
                }
                else{
                    createDaySchedule("Tuesday");
                }
            }
        });

        cbWednesday = fragment.findViewById(R.id.cbWednesday);
        cbWednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cbWednesday.isChecked()){
                    editDaySchedule("Wednesday");
                }
                else{
                    createDaySchedule("Wednesday");
                }
            }
        });

        cbThursday = fragment.findViewById(R.id.cbThursday);
        cbThursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cbThursday.isChecked()){
                    editDaySchedule("Thursday");
                }
                else{
                    createDaySchedule("Thursday");
                }
            }
        });

        cbFriday = fragment.findViewById(R.id.cbFriday);
        cbFriday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cbFriday.isChecked()){
                    editDaySchedule("Friday");
                }
                else{
                    createDaySchedule("Friday");
                }
            }
        });

        Button btnSave = fragment.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateBeforeSave();
                if (finished == true) {
                    String semester;
                    String[] parsedDate = model.getCourse().getStartDate().split("/");
                    int intMonth = Integer.parseInt(parsedDate[0]);
                    if (intMonth >= 1 && intMonth <= 4) {
                        semester = "Spring " + parsedDate[2];
                    } else if (intMonth >= 5 && intMonth <= 8) {
                        semester = "Summer " + parsedDate[2];
                    } else {
                        semester = "Fall " + parsedDate[2];
                    }
                    DatabaseReference dbRef = database.getReference("test");
                    dbRef.child(semester).child(model.getCourse().getCourseName()).setValue(model.getCourse());

                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentListContainer, new ListFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                else{
                    Toast toast = Toast.makeText(getActivity(), "Please be sure to enter all information.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        Button btnCancel = fragment.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.getCourse().setCourseName(null);
                model.getCourse().setStartDate(null);
                model.getCourse().setEndDate(null);
                navigateToNextFragment(new ListFragment(), "List");
            }
        });

        return fragment;
    }

    private void editDaySchedule(String dayOfWeek){
        DialogWarningFragment warning = new DialogWarningFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Day", dayOfWeek);
        warning.setArguments(bundle);
        warning.show(getFragmentManager(), "warning");
    }

    private void createDaySchedule(String dayOfWeek){
        Bundle bundle = new Bundle();
        bundle.putString("Saved", "false");
        bundle.putString("Day", dayOfWeek);
        Fragment newFragment = new DayFragment();
        newFragment.setArguments(bundle);

        DaysModel day = new DaysModel();
        day.setDayOfTheWeek(dayOfWeek);

        model.getCourse().setDay(day);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentListContainer, newFragment, "Day");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private boolean checkDates(){
        Date sDate = null;
        Date eDate = null;
        if (model.getCourse().getStartDate() != null && model.getCourse().getEndDate() != null){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            try {
                sDate = simpleDateFormat.parse(model.getCourse().getStartDate());
                eDate = simpleDateFormat.parse(model.getCourse().getEndDate());
            }
            catch (Exception ex){
                //log.d()
            }
            if (eDate.getTime() - sDate.getTime() > 0){
                finished = true;
                return true;

            }
        }
        else{
            finished = true;
            return true;
        }
        Toast toast = Toast.makeText(this.getActivity(), "Please be sure the dates are valid", Toast.LENGTH_SHORT);
        toast.show();
        finished = false;
        return false;
    }

    private void validateBeforeSave(){
        if(finished){
           if (model.getCourse().getCourseName() != null && model.getCourse().getStartDate() != null && model.getCourse().getEndDate() != null && (cbMonday.isChecked() || cbTuesday.isChecked() || cbWednesday.isChecked() || cbThursday.isChecked() || cbFriday.isChecked())){
                finished = true;
            }
           else{
               finished = false;
           }
        }
    }

}
