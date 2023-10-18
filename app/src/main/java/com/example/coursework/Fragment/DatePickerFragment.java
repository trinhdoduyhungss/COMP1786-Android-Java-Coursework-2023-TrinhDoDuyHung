package com.example.coursework.Fragment;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import android.os.Build;
import android.os.Bundle;
import android.app.Dialog;

import android.widget.DatePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.DatePickerDialog;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.coursework.Interfaces.DateSetHandle;


public class DatePickerFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        LocalDate d = LocalDate.now();
        int year = d.getYear();
        int month = d.getMonthValue();
        int day = d.getDayOfMonth();
        return new DatePickerDialog(getActivity(), this, year, --month, day);}
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day){
        LocalDate dob = LocalDate.of(year, ++month, day);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = dob.format(formatter);
        if (getTargetFragment() instanceof DateSetHandle){
            DateSetHandle handle = (DateSetHandle) getTargetFragment();
            handle.onDateSet(date);
        }
    }
}
