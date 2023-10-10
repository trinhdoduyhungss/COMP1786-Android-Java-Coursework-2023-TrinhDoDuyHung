package com.example.coursework.Fragment;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;

import androidx.room.Room;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import com.example.coursework.MainActivity;
import com.example.coursework.R;
import com.example.coursework.Models.Hike;
import com.example.coursework.Adapters.HikeAdapter;
import com.example.coursework.Database.AppDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AppDatabase appDatabase;
    private RecyclerView recyclerView;
    private HikeAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        appDatabase = Room.databaseBuilder(requireContext(), AppDatabase.class, "sqlite_example_db")
                .allowMainThreadQueries() // For simplicity, don't use this in production
                .build();

        Button deleteAll = rootView.findViewById(R.id.deleteAll);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState!=RecyclerView.SCROLL_STATE_IDLE) {
                        Toast.makeText(requireContext(), "End of list", Toast.LENGTH_SHORT).show();
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        List<Hike> hikes = appDatabase.hikeDao().getAllHikes();

        adapter = new HikeAdapter(hikes);

        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appDatabase.hikeDao().deleteAllHikes();
                ((MainActivity) getActivity()).selectHomeFragment();
            }
        });

        adapter.setOnItemClickListener(new HikeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Fragment fragment = new DetailFragment();
                Bundle bundle = new Bundle();
                Hike hike = hikes.get(position);
                bundle.putLong("hike_id", hike.hike_id);
                bundle.putString("hike_name", hike.name);
                bundle.putString("hike_doh", hike.doh);
                bundle.putString("hike_description", hike.description);
                bundle.putInt("hike_loh", hike.Loh);
                bundle.putString("hike_location", hike.location);
                bundle.putString("hike_difficulty", hike.difficulty);
                bundle.putBoolean("hike_hasParking", hike.hasParking);
                fragment.setArguments(bundle);
                ((MainActivity) getActivity()).replaceFragment(fragment);
            }
        });

        recyclerView.setAdapter(adapter);

        return rootView;
    }
}