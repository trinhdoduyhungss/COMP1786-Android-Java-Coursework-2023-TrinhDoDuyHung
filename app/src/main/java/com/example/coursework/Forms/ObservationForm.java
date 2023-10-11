package com.example.coursework.Forms;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.ArrayList;

import com.example.coursework.R;

public class ObservationForm {
    public long observationId;

    public String name;
    public String tob;
    public String description;

    public long hikeId;

    public Boolean readySave = false;

    @NonNull
    private List<View> getViews(@NonNull View rootView){
        List<View> views = new ArrayList<>();
        views.add(rootView.findViewById(R.id.nameObservationText));
        views.add(rootView.findViewById(R.id.tobObservationText));
        views.add(rootView.findViewById(R.id.descriptionObservationText));
        return views;
    }

    public String getViewValue(View view){
        if(view instanceof EditText){
            return ((EditText) view).getText().toString();
        }
        return null;
    }

    public void setValue(View view, String value){
        if(view instanceof EditText){
            ((EditText) view).setText(value);
        }
    }

    public void setForm(View rootView){
        List<View> views = getViews(rootView);
        Boolean status = updateForm(views);
        if(!status){
            AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
            builder.setMessage("Please fill in all the fields")
                    .setTitle("Error");
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else{
            String message = "Name: " + name + "\n" +
                    "Time of Observation: " + tob + "\n" +
                    "Comments: " + description + "\n";
            AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
            builder.setMessage(message)
                    .setTitle("Confirmation")
                    .setPositiveButton("Confirm", (dialog, id) -> {
                        this.readySave = true;
                        rootView.findViewById(R.id.saveObservationButton).callOnClick();
                    })
                    .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private Boolean updateForm(List<View> views){
        boolean status = isValid(views);
        if(status){
            this.name = getViewValue(views.get(0));
            this.tob = getViewValue(views.get(1));
            this.description = getViewValue(views.get(2));
        }
        return status;
    }

    private Boolean isValid(List<View> views){
        boolean status = true;
        for(View view : views){
            // check if viewId != descriptionObservationText and value is empty
            String value = getViewValue(view);
            String viewId = view.getId() == R.id.descriptionObservationText ? "commentObservationText" : null;
            if((value == null || value.isEmpty()) && viewId == null){
                status = false;
                break;
            }
        }
        return status;
    }

    public void setViewForm(View rootView, @NonNull Bundle bundle){
        List<View> views = getViews(rootView);
        setValue(views.get(0), bundle.getString("name"));
        setValue(views.get(1), bundle.getString("tob"));
        setValue(views.get(2), bundle.getString("description"));
    }

}
