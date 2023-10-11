package com.example.coursework.Fragment;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import com.example.coursework.MainActivity;
import com.example.coursework.R;
import com.example.coursework.Models.Observation;
import com.example.coursework.Database.AppDatabase;
import com.example.coursework.Adapters.ObservationAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ObservationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ObservationsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private AppDatabase appDatabase;
    private RecyclerView recyclerView;
    private ObservationAdapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ObservationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ObservationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ObservationsFragment newInstance(String param1, String param2) {
        ObservationsFragment fragment = new ObservationsFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_observations, container, false);

        appDatabase = Room.databaseBuilder(requireContext(), AppDatabase.class, "sqlite_example_db")
                .allowMainThreadQueries() // For simplicity, don't use this in production
                .build();

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        Long hikeId = getArguments().getLong("hikeId");

        List<Observation> observations = appDatabase.hikeDao().getObservationsForHike(hikeId);

        adapter = new ObservationAdapter(observations);

        adapter.setOnItemClickListener(new ObservationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Observation observation = observations.get(position);
                Fragment fragment = new EditObservationsFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("observationId", observation.observation_id);
                bundle.putString("name", observation.name);
                bundle.putString("tob", observation.tob);
                bundle.putString("description", observation.description);
                bundle.putLong("hikeId", hikeId);
                fragment.setArguments(bundle);
                ((MainActivity) getActivity()).replaceFragment(fragment);
            }
        });

        Button cancelButton = rootView.findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

        Button addButton = rootView.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AddObservationsFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("hikeId", hikeId);
                fragment.setArguments(bundle);
                ((MainActivity) getActivity()).replaceFragment(fragment);
            }
        });

        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private void cancel(){
        ((MainActivity) getActivity()).replaceFragment(new SearchFragment());
    }
}