package edu.uc.ucrouteassistant;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

public class DialogWarningFragment extends DialogFragment {

    private Bundle bundle;
    private SchedulerViewModel model;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        model = ViewModelProviders.of(this.getActivity()).get(SchedulerViewModel.class);
        bundle = new Bundle();
        bundle = getArguments();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.checkbox_warning)
                .setPositiveButton(R.string.edit_day_info, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Fragment fragment = new DayFragment();
                        bundle.putString("Saved", "true");
                        fragment.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragmentListContainer, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                })
                .setNegativeButton(R.string.remove_day, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        switch (bundle.getString("Day")){
                            case "Monday":
                                deleteDayData(0);
                                break;
                            case "Tuesday":
                                deleteDayData(1);
                                break;
                            case "Wednesday":
                                deleteDayData(2);
                                break;
                            case "Thursday":
                                deleteDayData(3);
                                break;
                            case "Friday":
                                deleteDayData(4);
                                break;
                        }


                    }
                });

        return builder.create();
    }

    public void deleteDayData(int i){
        DaysModel getDay = model.getCourse().getDays().get(i);
        getDay.setDayOfTheWeek(null);
        getDay.setBuilding(null);
        getDay.setRoomNumber(null);
        getDay.setStartTime(null);
        getDay.setEndTime(null);
    }
}
