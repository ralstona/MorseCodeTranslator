package edu.uc.ucrouteassistant;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewCourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewCourseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SchedulerViewModel model;
    private CourseModel viewCourse;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef;
    private ValueEventListener eventListener;

    public ViewCourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewCourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewCourseFragment newInstance(String param1, String param2) {
        ViewCourseFragment fragment = new ViewCourseFragment();
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
    public void onDestroy(){
        super.onDestroy();
        dbRef.removeEventListener(eventListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View fragment = inflater.inflate(R.layout.fragment_view_course, container, false);
        dbRef = database.getReference("test");
        Bundle bundle = new Bundle();
        bundle = requireArguments();
        final String semester = bundle.getString("Semester");
        final String course = bundle.getString("CourseName");
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                viewCourse = dataSnapshot.child(semester).child(course).getValue(CourseModel.class);
                if (viewCourse != null) {
                    TextView txtCourseName = fragment.findViewById(R.id.txtCourseName);
                    TextView txtStartDate = fragment.findViewById(R.id.txtStartDate);
                    TextView txtEndDate = fragment.findViewById(R.id.txtEndDate);

                    txtCourseName.setText(viewCourse.getCourseName());
                    txtStartDate.setText(viewCourse.getStartDate());
                    txtEndDate.setText(viewCourse.getEndDate());

                    for (DaysModel day : viewCourse.getDays()) {
                        if (day != null && day.getDayOfTheWeek() != null) {
                            switch (day.getDayOfTheWeek()) {
                                case "Monday":
                                    setDay(viewCourse, fragment, 0, R.id.txtMonBuilding, R.id.txtMonRoom, R.id.txtMonStartTime, R.id.txtMonEndTime);
                                    break;
                                case "Tuesday":
                                    setDay(viewCourse, fragment, 1, R.id.txtTueBuilding, R.id.txtTueRoom, R.id.txtTueStartTime, R.id.txtTueEndTime);
                                    break;
                                case "Wednesday":
                                    setDay(viewCourse, fragment, 2, R.id.txtWedBuilding, R.id.txtWedRoom, R.id.txtWedStartTime, R.id.txtWedEndTime);
                                    break;
                                case "Thursday":
                                    setDay(viewCourse, fragment, 3, R.id.txtThuBuilding, R.id.txtThuRoom, R.id.txtThuStartTime, R.id.txtThuEndTime);
                                    break;
                                case "Friday":
                                    setDay(viewCourse, fragment, 4, R.id.txtFriBuilding, R.id.txtFriRoom, R.id.txtFriStartTime, R.id.txtFriEndTime);
                                    break;
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        dbRef.addValueEventListener(eventListener);

        Button btnEdit = fragment.findViewById(R.id.btnEdit);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToEditScheduleFragment();
            }
        });

        Button btnBack = fragment.findViewById(R.id.btnCancel);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToListFragment();
            }
        });

        Button btnDelete = fragment.findViewById(R.id.btnDelete);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef.child(semester).child(course).removeValue();
                navigateToListFragment();
            }
        });

        return fragment;
    }

    private void setDay(CourseModel viewCourse, View fragment, int dayOfTheWeek, int building, int room, int startTime, int endTime ){

        DaysModel day = viewCourse.getDays().get(dayOfTheWeek);

        TextView txtBldg = fragment.findViewById(building);
        TextView txtRoom = fragment.findViewById(room);
        TextView txtStartTime = fragment.findViewById(startTime);
        TextView txtEndTime = fragment.findViewById(endTime);

        txtBldg.setText(day.getBuilding());
        txtRoom.setText(day.getRoomNumber());
        txtStartTime.setText(reformatTime(day.getStartTime()));
        txtEndTime.setText(reformatTime(day.getEndTime()));
    }

    private String reformatTime(String classTime){

        String[] editTime = classTime.split(":");
        String reformattedTime;
        int hour = Integer.parseInt(editTime[0]);
        if (hour > 12){
            reformattedTime = String.valueOf(hour - 12) + ":" + editTime[1] + " PM";
        }
        else if (hour == 0){
            reformattedTime = String.valueOf(12) + ":" + editTime[1] + " AM";
        }
        else if (hour < 12){
            reformattedTime = classTime + " AM";
        }

        else{
            reformattedTime = classTime + " PM";
        }
        return reformattedTime;
    }

    private void navigateToListFragment(){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentListContainer, new ListFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void navigateToEditScheduleFragment(){
        model = ViewModelProviders.of(requireActivity()).get(SchedulerViewModel.class);
        model.setCourse(viewCourse);
        Bundle bundle = new Bundle();
        Fragment fragment = new EditScheduleFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentListContainer, fragment, "EditScheduleFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
