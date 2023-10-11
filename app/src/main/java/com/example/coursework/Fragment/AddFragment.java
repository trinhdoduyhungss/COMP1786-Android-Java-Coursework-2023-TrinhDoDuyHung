package com.example.coursework.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.coursework.Database.AppDatabase;
import com.example.coursework.Forms.HikeForm;
import com.example.coursework.MainActivity;
import com.example.coursework.Models.Hike;
import com.example.coursework.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private AppDatabase appDatabase;

    private final HikeForm hikeForm = new HikeForm();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);

        appDatabase = Room.databaseBuilder(requireContext(), AppDatabase.class, "sqlite_example_db")
                .allowMainThreadQueries() // For simplicity, don't use this in production
                .build();

        Button saveDetailsButton = rootView.findViewById(R.id.saveHikeButton);
        saveDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hikeForm.readySave){
                    saveDetails();
                }else if(hikeForm.name == null){
                    hikeForm.setForm(rootView);
                }
            }
        });

        CheckBox hasParkingButtonYes = rootView.findViewById(R.id.parkingCheckHikeTextYes);
        CheckBox hasParkingButtonNo = rootView.findViewById(R.id.parkingCheckHikeTextNo);
        hasParkingButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hasParkingButtonNo.setChecked(false);
            }
        });
        hasParkingButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hasParkingButtonYes.setChecked(false);
            }
        });

        return rootView;
    }

    private void saveDetails() {
        Hike hike = new Hike();
        hike.name = hikeForm.name;
        hike.doh = hikeForm.doh;
        hike.Loh = hikeForm.Loh;
        hike.difficulty = hikeForm.difficulty;
        hike.location = hikeForm.location;
        hike.hasParking = hikeForm.hasParking;
        hike.description = hikeForm.description;

        appDatabase.hikeDao().insertHike(hike);

        // Select the home fragment at bottom navigation
        ((MainActivity) getActivity()).selectHomeFragment();

    }
}