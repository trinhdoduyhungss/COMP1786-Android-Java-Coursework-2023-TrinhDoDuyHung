package com.example.coursework.Fragment;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.EditText;
import android.view.LayoutInflater;

import com.example.coursework.R;
import com.example.coursework.Models.Hike;
import com.example.coursework.Adapters.HikeAdapter;
import com.example.coursework.Database.AppDatabase;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private AppDatabase appDatabase;
    private RecyclerView recyclerView;
    private HikeAdapter adapter;

    private List<Hike> hikes;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        EditText searchText = rootView.findViewById(R.id.searchText);
        Button searchButton = rootView.findViewById(R.id.searchButton);

        appDatabase = Room.databaseBuilder(requireContext(), AppDatabase.class, "sqlite_example_db")
                .allowMainThreadQueries() // For simplicity, don't use this in production
                .build();

        hikes = appDatabase.hikeDao().getAllHikes();

        adapter = new HikeAdapter(hikes);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = searchText.getText().toString();
                if (!search.isEmpty()) {
                    search(search);
                }
                else{
                    adapter.setHikes(appDatabase.hikeDao().getAllHikes());
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private void search(String search) {
        hikes = appDatabase.hikeDao().searchHikes(search);
        adapter.setHikes(hikes);
        recyclerView.setAdapter(adapter);
    }
}