package edu.uc.ucrouteassistant;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DayFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SchedulerViewModel model;
    private EditText edTxtRoom;
    private EditText edTxtStartTime;
    private EditText edTxtEndTime;
    private Bundle bundle;
    private boolean finished;
    private AutoCompleteTextView acTxtBldgs;

    public DayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DayFragment newInstance(String param1, String param2) {
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private String reformatTime(int dayOfTheWeek, boolean startTime){
        String classTime;
        if (startTime == true){
            classTime = model.getCourse().getDays().get(dayOfTheWeek).getStartTime();
        }
        else{
            classTime = model.getCourse().getDays().get(dayOfTheWeek).getEndTime();
        }

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

    @Override
    public void onResume(){
        super.onResume();

        if ( model != null && model.getCourse() != null && model.getCourse().getDays() != null){

            String day = bundle.getString("Day");
            DaysModel actualDay;

            switch (day) {
                case "Monday":
                    actualDay = model.getCourse().getDays().get(0);
                    if (checkTime(0)) {
                        if (actualDay.getStartTime() != null) {
                            edTxtStartTime.setText(reformatTime(0, true));
                            if (actualDay.getBuilding() !=  null){
                                acTxtBldgs.setText(actualDay.getBuilding());
                            }
                            if (actualDay.getRoomNumber() != null){
                                edTxtRoom.setText(actualDay.getRoomNumber());
                            }
                        }
                        if (actualDay.getEndTime() != null) {
                            edTxtEndTime.setText(reformatTime(0, false));
                            if (actualDay.getBuilding() !=  null){
                                acTxtBldgs.setText(actualDay.getBuilding());
                            }
                            if (actualDay.getRoomNumber() != null){
                                edTxtRoom.setText(actualDay.getRoomNumber());
                            }
                        }
                        finished = true;
                    }
                    else{
                        if (actualDay.getStartTime() != null) {
                            edTxtStartTime.setText(reformatTime(0, true));
                            edTxtStartTime.setTextColor(Color.RED);
                            if (actualDay.getBuilding() !=  null){
                                acTxtBldgs.setText(actualDay.getBuilding());
                            }
                            if (actualDay.getRoomNumber() != null){
                                edTxtRoom.setText(actualDay.getRoomNumber());
                            }
                        }
                        if (actualDay.getEndTime() != null) {
                            edTxtEndTime.setText(reformatTime(0, false));
                            edTxtEndTime.setTextColor(Color.RED);
                            if (actualDay.getBuilding() !=  null){
                                acTxtBldgs.setText(actualDay.getBuilding());
                            }
                            if (actualDay.getRoomNumber() != null){
                                edTxtRoom.setText(actualDay.getRoomNumber());
                            }
                        }
                        finished = false;
                    }
                    break;
                case "Tuesday":
                    actualDay = model.getCourse().getDays().get(1);
                    if (checkTime(1)){
                        if (actualDay.getStartTime() != null){
                            edTxtStartTime.setText(reformatTime(1, true));
                            if (actualDay.getBuilding() !=  null){
                                acTxtBldgs.setText(actualDay.getBuilding());
                            }
                            if (actualDay.getRoomNumber() != null){
                                edTxtRoom.setText(actualDay.getRoomNumber());
                            }
                        }
                        if (actualDay.getEndTime() != null){
                            edTxtEndTime.setText(reformatTime(1, false));
                            if (actualDay.getBuilding() !=  null){
                                acTxtBldgs.setText(actualDay.getBuilding());
                            }
                            if (actualDay.getRoomNumber() != null){
                                edTxtRoom.setText(actualDay.getRoomNumber());
                            }
                        }
                        finished = true;
                    }
                    else{
                        if (actualDay.getStartTime() != null){
                            edTxtStartTime.setText(reformatTime(1, true));
                            edTxtStartTime.setTextColor(Color.RED);
                            if (actualDay.getBuilding() !=  null){
                                acTxtBldgs.setText(actualDay.getBuilding());
                            }
                            if (actualDay.getRoomNumber() != null){
                                edTxtRoom.setText(actualDay.getRoomNumber());
                            }
                        }
                        if (actualDay.getEndTime() != null){
                            edTxtEndTime.setText(reformatTime(1, false));
                            edTxtEndTime.setTextColor(Color.RED);
                            if (actualDay.getBuilding() !=  null){
                                acTxtBldgs.setText(actualDay.getBuilding());
                            }
                            if (actualDay.getRoomNumber() != null){
                                edTxtRoom.setText(actualDay.getRoomNumber());
                            }
                        }
                        finished = false;
                    }
                    break;
                case "Wednesday":
                    actualDay = model.getCourse().getDays().get(2);
                    if (checkTime(2)) {
                        if (actualDay.getStartTime() != null) {
                            edTxtStartTime.setText(reformatTime(2, true));
                            if (actualDay.getBuilding() !=  null){
                                acTxtBldgs.setText(actualDay.getBuilding());
                            }
                            if (actualDay.getRoomNumber() != null){
                                edTxtRoom.setText(actualDay.getRoomNumber());
                            }
                        }
                        if (actualDay.getEndTime() != null) {
                            edTxtEndTime.setText(reformatTime(2, false));
                            if (actualDay.getBuilding() !=  null){
                                acTxtBldgs.setText(actualDay.getBuilding());
                            }
                            if (actualDay.getRoomNumber() != null){
                                edTxtRoom.setText(actualDay.getRoomNumber());
                            }
                        }
                        finished = true;
                    }
                    else{
                        if (actualDay.getStartTime() != null) {
                            edTxtStartTime.setText(reformatTime(2, true));
                            edTxtStartTime.setTextColor(Color.RED);
                            if (actualDay.getBuilding() !=  null){
                                acTxtBldgs.setText(actualDay.getBuilding());
                            }
                            if (actualDay.getRoomNumber() != null){
                                edTxtRoom.setText(actualDay.getRoomNumber());
                            }
                        }
                        if (actualDay.getEndTime() != null) {
                            edTxtEndTime.setText(reformatTime(2, false));
                            edTxtEndTime.setTextColor(Color.RED);
                            if (actualDay.getBuilding() !=  null){
                                acTxtBldgs.setText(actualDay.getBuilding());
                            }
                            if (actualDay.getRoomNumber() != null){
                                edTxtRoom.setText(actualDay.getRoomNumber());
                            }
                        }
                        finished = false;
                    }
                    break;
                case "Thursday":
                    actualDay = model.getCourse().getDays().get(3);
                    if (checkTime(3)) {
                        if (actualDay.getStartTime() != null) {
                            edTxtStartTime.setText(reformatTime(3, true));
                            if (actualDay.getBuilding() !=  null){
                                acTxtBldgs.setText(actualDay.getBuilding());
                            }
                            if (actualDay.getRoomNumber() != null){
                                edTxtRoom.setText(actualDay.getRoomNumber());
                            }
                        }
                        if (actualDay.getEndTime() != null) {
                            edTxtEndTime.setText(reformatTime(3, false));
                            if (actualDay.getBuilding() !=  null){
                                acTxtBldgs.setText(actualDay.getBuilding());
                            }
                            if (actualDay.getRoomNumber() != null){
                                edTxtRoom.setText(actualDay.getRoomNumber());
                            }
                        }
                        finished = true;
                    }
                    else{
                        if (actualDay.getStartTime() != null) {
                            edTxtStartTime.setText(reformatTime(3, true));
                            edTxtStartTime.setTextColor(Color.RED);
                            if (actualDay.getBuilding() !=  null){
                                acTxtBldgs.setText(actualDay.getBuilding());
                            }
                            if (actualDay.getRoomNumber() != null){
                                edTxtRoom.setText(actualDay.getRoomNumber());
                            }
                        }
                        if (actualDay.getEndTime() != null) {
                            edTxtEndTime.setText(reformatTime(3, false));
                            edTxtEndTime.setTextColor(Color.RED);
                            if (actualDay.getBuilding() !=  null){
                                acTxtBldgs.setText(actualDay.getBuilding());
                            }
                            if (actualDay.getRoomNumber() != null){
                                edTxtRoom.setText(actualDay.getRoomNumber());
                            }
                        }
                        finished = false;
                    }
                    break;
                case "Friday":
                    actualDay = model.getCourse().getDays().get(4);
                    if (checkTime(4)) {
                        if (actualDay.getStartTime() != null) {
                            edTxtStartTime.setText(reformatTime(4, true));
                            if (actualDay.getBuilding() !=  null){
                                acTxtBldgs.setText(actualDay.getBuilding());
                            }
                            if (actualDay.getRoomNumber() != null){
                                edTxtRoom.setText(actualDay.getRoomNumber());
                            }
                        }
                        if (actualDay.getEndTime() != null) {
                            edTxtEndTime.setText(reformatTime(4, false));
                            if (actualDay.getBuilding() !=  null){
                                acTxtBldgs.setText(actualDay.getBuilding());
                            }
                            if (actualDay.getRoomNumber() != null){
                                edTxtRoom.setText(actualDay.getRoomNumber());
                            }
                        }
                        finished = true;
                    }
                    else{
                        if (actualDay.getStartTime() != null) {
                            edTxtStartTime.setText(reformatTime(4, true));
                            edTxtStartTime.setTextColor(Color.RED);
                            if (actualDay.getBuilding() !=  null){
                                acTxtBldgs.setText(actualDay.getBuilding());
                            }
                            if (actualDay.getRoomNumber() != null){
                                edTxtRoom.setText(actualDay.getRoomNumber());
                            }
                        }
                        if (actualDay.getEndTime() != null) {
                            edTxtEndTime.setText(reformatTime(4, false));
                            edTxtEndTime.setTextColor(Color.RED);
                            if (actualDay.getBuilding() !=  null){
                                acTxtBldgs.setText(actualDay.getBuilding());
                            }
                            if (actualDay.getRoomNumber() != null){
                                edTxtRoom.setText(actualDay.getRoomNumber());
                            }
                        }
                        finished = false;
                    }
                    break;
            }
        }
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
        final View fragment = inflater.inflate(R.layout.fragment_day, container, false);
        model = ViewModelProviders.of(this.getActivity()).get(SchedulerViewModel.class);
        bundle = requireArguments();

        acTxtBldgs = fragment.findViewById(R.id.acTxtBldgs);
        acTxtBldgs.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    acTxtBldgs.setText("");
                }
            }
        });
        acTxtBldgs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                model.getCourse().getDays().get(dayAsInt()).setBuilding(acTxtBldgs.getText().toString());
            }
        });
        acTxtBldgs.setThreshold(1);
        String[] buildings = getResources().getStringArray(R.array.ucBuildings);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, buildings);
        acTxtBldgs.setAdapter(adapter);

        edTxtRoom = fragment.findViewById(R.id.edTxtRoom);
        edTxtRoom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    edTxtRoom.setText("");
                }
            }
        });
        edTxtRoom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                model.getCourse().getDays().get(dayAsInt()).setRoomNumber(edTxtRoom.getText().toString());
            }
        });

        edTxtStartTime = fragment.findViewById(R.id.edTxtStartTime);
        edTxtStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToFragment(new ClassTime(true), "StartTime");
            }
        });
        edTxtStartTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                navigateToFragment(new ClassTime(true), "StartTime");
            }
        });

        edTxtEndTime = fragment.findViewById(R.id.edTxtEndTime);
        edTxtEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToFragment(new ClassTime(false), "EndTime");
            }
        });
        edTxtEndTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                navigateToFragment(new ClassTime(false), "EndTime");
            }
        });

        Button btnDone = fragment.findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateBeforeFinish();
                if (finished == true) {
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    Fragment fragment = getFragmentManager().findFragmentByTag("EditScheduleFragment");
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragmentListContainer, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                else{
                    Toast toast = Toast.makeText(getActivity(), "Please correct the errors above and ensure all fields have been filled out.", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        Button btnCancel = fragment.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!bundle.getString("Saved").equals("true")) {
                    DaysModel cancelDay = model.getCourse().getDays().get(dayAsInt());
                    cancelDay.setRoomNumber(null);
                    cancelDay.setBuilding(null);
                    cancelDay.setEndTime(null);
                    cancelDay.setStartTime(null);
                    cancelDay.setDayOfTheWeek(null);
                }

                navigateToFragment(new EditScheduleFragment(), "Edit");
            }
        });


        return fragment;
    }

    private void navigateToFragment(Fragment fragment, String tag) {
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentListContainer, fragment, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private boolean checkTime(int dayOfTheWeek){
        Date sTime = null;
        Date eTime = null;

        if (model.getCourse().getDays().get(dayOfTheWeek).getStartTime() != null && model.getCourse().getDays().get(dayOfTheWeek).getEndTime() != null){
            DateFormat dateFormat = new SimpleDateFormat("HH:mm");
            try {
                sTime = dateFormat.parse(model.getCourse().getDays().get(dayOfTheWeek).getStartTime());
                eTime = dateFormat.parse(model.getCourse().getDays().get(dayOfTheWeek).getEndTime());
            }
            catch (Exception ex){
                //
            }
            if (eTime.getTime() - sTime.getTime() > 0){
                return true;
            }
            else{
                Toast toast = Toast.makeText(this.getActivity(), "Please be sure the time range is valid", Toast.LENGTH_SHORT);
                toast.show();
                return false;
            }
        }
        return true;
    }

    private int dayAsInt(){
        String dayOfTheWeek = bundle.getString("Day");
        int day = 0;
        switch(dayOfTheWeek) {
            case "Monday":
                day = 0;
                break;
            case "Tuesday":
                day = 1;
                break;
            case "Wednesday":
                day = 2;
                break;
            case "Thursday":
                day = 3;
                break;
            case "Friday":
                day = 4;
                break;
        }
        return day;
    }

    private void validateBeforeFinish(){
        if(finished){
            int day = dayAsInt();
            if (model.getCourse().getDays().get(day).getStartTime() != null && model.getCourse().getDays().get(day).getEndTime() != null && model.getCourse().getDays().get(day).getBuilding() != null && model.getCourse().getDays().get(day).getRoomNumber() != null){
                finished = true;
            }
            else{
                finished = false;
            }
        }
    }
}
