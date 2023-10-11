package com.example.coursework.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.coursework.Database.AppDatabase;
import com.example.coursework.MainActivity;
import com.example.coursework.R;
import com.example.coursework.Forms.ObservationForm;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditObservationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditObservationsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private AppDatabase appDatabase;
    private final ObservationForm observationForm = new ObservationForm();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditObservationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditObservationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditObservationsFragment newInstance(String param1, String param2) {
        EditObservationsFragment fragment = new EditObservationsFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_edit_observations, container, false);

        appDatabase = Room.databaseBuilder(requireContext(), AppDatabase.class, "sqlite_example_db")
                .allowMainThreadQueries() // For simplicity, don't use this in production
                .build();

        observationForm.setViewForm(rootView, getArguments());

        observationForm.hikeId = getArguments().getLong("hikeId");
        observationForm.observationId = getArguments().getLong("observationId");

        Button cancelButton = rootView.findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

        Button saveObservationButton = rootView.findViewById(R.id.saveObservationButton);

        saveObservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(observationForm.readySave){
                    saveDetails();
                }else if(observationForm.name == null){
                    observationForm.setForm(rootView);
                }
            }
        });

        Button deleteButton = rootView.findViewById(R.id.deleteButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appDatabase.hikeDao().deleteObservation(observationForm.observationId);
                cancel();
            }
        });

        return rootView;
    }

    private void saveDetails(){
        appDatabase.hikeDao().updateObservation(
                observationForm.observationId,
                observationForm.name,
                observationForm.tob,
                observationForm.description
        );
        cancel();
    }

    private void cancel(){
        Fragment fragment = new ObservationsFragment();
        Bundle args = new Bundle();
        args.putLong("hikeId", getArguments().getLong("hikeId"));
        fragment.setArguments(args);
        ((MainActivity) getActivity()).replaceFragment(fragment);
    }
}