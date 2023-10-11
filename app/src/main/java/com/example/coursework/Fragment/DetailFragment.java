package com.example.coursework.Fragment;

import androidx.room.Room;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.view.LayoutInflater;

import com.example.coursework.R;
import com.example.coursework.Models.Hike;
import com.example.coursework.MainActivity;
import com.example.coursework.Forms.HikeForm;
import com.example.coursework.Database.AppDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AppDatabase appDatabase;
    private final HikeForm hikeForm = new HikeForm();

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Long hike_id = getArguments().getLong("hike_id");
        boolean hasParking = getArguments().getBoolean("hike_hasParking");

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

        if(hasParking){
            hasParkingButtonYes.setChecked(true);
        }else{
            hasParkingButtonNo.setChecked(true);
        }

        // get hike from database
        appDatabase = Room.databaseBuilder(requireContext(), AppDatabase.class, "sqlite_example_db")
                .allowMainThreadQueries() // For simplicity, don't use this in production
                .build();

        // set text fields
        hikeForm.setViewForm(rootView, getArguments());

        Button deleteButton = rootView.findViewById(R.id.deleteButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appDatabase.hikeDao().deleteHike(hike_id);
                cancel();
            }
        });

        Button cancelButton = rootView.findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

        Button saveHikeButton = rootView.findViewById(R.id.saveHikeButton);

        saveHikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hikeForm.readySave){
                    hikeForm.hikeId = hike_id;
                    saveDetails();
                }else if(hikeForm.name == null){
                    hikeForm.setForm(rootView);
                }
            }
        });

        return rootView;
    }

    public void saveDetails() {
        Hike hike = new Hike();
        hike.hike_id = hikeForm.hikeId;
        hike.name = hikeForm.name;
        hike.doh = hikeForm.doh;
        hike.Loh = hikeForm.Loh;
        hike.difficulty = hikeForm.difficulty;
        hike.location = hikeForm.location;
        hike.hasParking = hikeForm.hasParking;
        hike.description = hikeForm.description;
        appDatabase.hikeDao().updateHike(hike);
        cancel();
    }


    private void cancel() {
        ((MainActivity) getActivity()).selectHomeFragment();
    }
}