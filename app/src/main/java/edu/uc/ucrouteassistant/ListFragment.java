package edu.uc.ucrouteassistant;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef;
    private ValueEventListener eventListener;
    private EditText edTxtSemester;
    private SchedulerViewModel model;
    private CourseModel course;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        dbRef.removeEventListener(eventListener);
        LinearLayout llFragmentListContainer = getActivity().findViewById(R.id.llFragmentListContainer);
        llFragmentListContainer.removeAllViews();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View fragment =  inflater.inflate(R.layout.fragment_list, container, false);

        edTxtSemester = fragment.findViewById(R.id.edTxtSemester);
        edTxtSemester.setInputType(InputType.TYPE_NULL);
        edTxtSemester.setFocusable(false);
        edTxtSemester.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.DST_OVER);

        dbRef = database.getReference("test");
        //dbRef.keepSynced(true);

        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String currentSemester = identifySemester();
                edTxtSemester.setText(currentSemester);
                if (dataSnapshot.child(currentSemester).getChildrenCount() >= 1){

                    for (DataSnapshot course : dataSnapshot.child(currentSemester).getChildren()){
                        Bundle bundle = new Bundle();
                        String courseName = course.getValue(CourseModel.class).getCourseName();
                        bundle.putString("Semester", currentSemester);
                        bundle.putString("CourseName", courseName);
                        Fragment fragment = new CourseFragment();
                        fragment.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.llFragmentListContainer, fragment, "ViewSchedule");
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                databaseError.getMessage();
            }
        };

        dbRef.addListenerForSingleValueEvent(eventListener);

        Button btnCreate = fragment.findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToEditScheduleFragment();
            }
        });

        return fragment;
    }

    private void navigateToEditScheduleFragment(){
        model = ViewModelProviders.of(requireActivity()).get(SchedulerViewModel.class);
        course = new CourseModel();
        model.setCourse(course);
        Bundle bundle = new Bundle();
        bundle.putString("Day", "None");
        Fragment fragment = new EditScheduleFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentListContainer, fragment, "EditScheduleFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private String identifySemester(){
        Date date = Calendar.getInstance().getTime();
        String currentDateTime = date.toString();
        String[] arrayCurrentDateTime = currentDateTime.split(" ");
        String month = arrayCurrentDateTime[1];
        String year = arrayCurrentDateTime[5];
        String semester = null;
        int intMonth = 0;

        switch (month){
            case "Jan":
                intMonth = 1;
                break;
            case "Feb":
                intMonth = 2;
                break;
            case "Mar":
                intMonth = 3;
                break;
            case "Apr":
                intMonth = 4;
                break;
            case "May":
                intMonth = 5;
                break;
            case "Jun":
                intMonth = 6;
                break;
            case "Jul":
                intMonth = 7;
                break;
            case "Aug":
                intMonth = 8;
                break;
            case "Sep":
                intMonth = 9;
                break;
            case "Oct":
                intMonth = 10;
                break;
            case "Nov":
                intMonth = 11;
                break;
            case "Dec":
                intMonth = 12;
                break;
        }

        if (intMonth >= 1 && intMonth <= 4) {
            semester = "Spring " + year;
        } else if (intMonth >= 5 && intMonth <= 8) {
            semester = "Summer " + year;
        } else {
            semester = "Fall " + year;
        }

        return semester;
    }
}
