package edu.uc.ucrouteassistant;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClassTime#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassTime extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SchedulerViewModel model;
    private Bundle bundle;
    private boolean startTime;

    public ClassTime() {
        // Required empty public constructor
    }

    public ClassTime(boolean startTime){
        this.startTime = startTime;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StartTime.
     */
    // TODO: Rename and change types and number of parameters
    public static ClassTime newInstance(String param1, String param2) {
        ClassTime fragment = new ClassTime();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment = inflater.inflate(R.layout.fragment_class_time, container, false);
        model = ViewModelProviders.of(this.getActivity()).get(SchedulerViewModel.class);
        bundle = requireArguments();

        final TimePicker tpTime = fragment.findViewById(R.id.selectStartTime);

        tpTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int min) {

                String day = bundle.getString("Day");

                switch (day){
                    case "Monday":
                        setClassTime(0, hour, min);
                        break;
                    case "Tuesday":
                        setClassTime(1, hour, min);
                        break;
                    case "Wednesday":
                        setClassTime(2, hour, min);
                        break;
                    case "Thursday":
                        setClassTime(3, hour, min);
                        break;
                    case "Friday":
                        setClassTime(4, hour, min);
                        break;
                }


            }
        });

        Button btnFinish = fragment.findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new DayFragment();
                fragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentListContainer, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return fragment;
    }

    public void setClassTime(int dayOfTheWeek, int hour, int min){
        DaysModel newDay = model.getCourse().getDays().get(dayOfTheWeek);

        if (min < 10){
            if (startTime == true) {
                newDay.setStartTime(hour + ":0" + min);
            }
            else{
                newDay.setEndTime(hour + ":0" + min);
            }
        }
        else{
            if (startTime == true){
                newDay.setStartTime(hour + ":" + min);
            }
            else{
                newDay.setEndTime(hour + ":" + min);
            }
        }

        model.getCourse().getDays().set(dayOfTheWeek, newDay);
    }
}
