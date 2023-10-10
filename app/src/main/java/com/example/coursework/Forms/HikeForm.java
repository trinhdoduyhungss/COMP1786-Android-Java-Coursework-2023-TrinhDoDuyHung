package com.example.coursework.Forms;
import java.util.List;
import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.CheckBox;
import android.widget.EditText;
import android.app.AlertDialog;

import androidx.annotation.NonNull;

import com.example.coursework.R;

public class HikeForm {
    public long hikeId;

    public String name;
    public String location;
    public String doh;
    public Boolean hasParking;
    public int Loh;
    public String difficulty;
    public String description;

    public Boolean readySave = false;

    @NonNull
    private List<View> getViews(@NonNull View rootView){
        List<View> views = new ArrayList<>();
        views.add(rootView.findViewById(R.id.nameHikeText));
        views.add(rootView.findViewById(R.id.dohHikeText));
        views.add(rootView.findViewById(R.id.lengthHikeText));
        views.add(rootView.findViewById(R.id.locationHikeText));
        views.add(rootView.findViewById(R.id.difficultHikeText));
        boolean hasParking = ((CheckBox) rootView.findViewById(R.id.parkingCheckHikeTextYes)).isChecked();
        if(hasParking){
            views.add(rootView.findViewById(R.id.parkingCheckHikeTextYes));
        }else{
            views.add(rootView.findViewById(R.id.parkingCheckHikeTextNo));
        }
        views.add(rootView.findViewById(R.id.descriptionHikeText));
        return views;
    }

    public String getViewValue(View view){
        if(view instanceof EditText){
            return ((EditText) view).getText().toString();
        }
        else if(view instanceof Spinner){
            return ((Spinner) view).getSelectedItem().toString();
        }
        else if(view instanceof CheckBox){
            return ((CheckBox) view).isChecked() && ((CheckBox) view).getId() == R.id.parkingCheckHikeTextYes ? "true" : "false";
        }
        return null;
    }

    public void setValue(View view, String value){
        if(view instanceof EditText){
            ((EditText) view).setText(value);
        }
        else if(view instanceof Spinner){
            ((Spinner) view).setSelection(getIndex((Spinner) view, value));
        }
        else if(view instanceof CheckBox){
            ((CheckBox) view).setChecked(Boolean.parseBoolean(value));
        }
    }

    public void setForm(View rootView){
        List<View> views = getViews(rootView);
        Boolean status = updateForm(
                getViewValue(views.get(0)),
                getViewValue(views.get(1)),
                Integer.parseInt("0"+getViewValue(views.get(2))),
                getViewValue(views.get(3)),
                getViewValue(views.get(4)),
                Boolean.parseBoolean(getViewValue(views.get(5))),
                getViewValue(views.get(6)));
        if(!status){
            AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
            builder.setMessage("Please fill in all the fields")
                    .setTitle("Error");
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else{
            String parkingStatus = this.hasParking? "Yes" : "No";
            // Show alert dialog to confirm
            String message = "Name: " + this.name + "\n" +
                    "Date of Hike: " + this.doh + "\n" +
                    "Distance: " + this.Loh + "\n" +
                    "Location: " + this.location + "\n" +
                    "Difficulty: " + this.difficulty + "\n" +
                    "Has Parking: " +  parkingStatus + "\n";
            AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
            builder.setMessage(message)
                    .setTitle("Confirmation")
                    .setPositiveButton("Confirm", (dialog, id) -> {
                        this.readySave = true;
                        rootView.findViewById(R.id.saveHikeButton).callOnClick();
                    })
                    .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    // Function to update the form from the database
    public void setViewForm(View rootView, @NonNull Bundle bundle){
        List<View> views = getViews(rootView);
        setValue(views.get(0), bundle.getString("hike_name"));
        setValue(views.get(1), bundle.getString("hike_doh"));
        setValue(views.get(2), Integer.toString(bundle.getInt("hike_loh")));
        setValue(views.get(3), bundle.getString("hike_location"));
        setValue(views.get(4), bundle.getString("hike_difficulty"));
        setValue(views.get(5), "true");
        setValue(views.get(6), bundle.getString("hike_description"));
    }

    private int getIndex(@NonNull Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }

    // Function to update the form
    @NonNull
    private Boolean updateForm(String name, String date, int distance, String location, String difficulty, Boolean hasParking, String description){
        boolean valid = isValid(name, date, distance, location, difficulty, hasParking);
        if(valid){
            this.name = name;
            this.doh = date;
            this.Loh = distance;
            this.location = location;
            this.difficulty = difficulty;
            this.hasParking = hasParking;
            this.description = description;
            return true;
        }
        return false;
    }

    // Fuction to check if the form is valid
    private boolean isValid(String name, String date, int distance, String location, String difficulty, Boolean hasParking) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        if (location == null || location.isEmpty()) {
            return false;
        }
        if (date == null || date.isEmpty()) {
            return false;
        }
        if (distance <= 0) {
            return false;
        }
        if (hasParking == null) {
            return false;
        }
        return difficulty != null && !difficulty.isEmpty();
    }
}
