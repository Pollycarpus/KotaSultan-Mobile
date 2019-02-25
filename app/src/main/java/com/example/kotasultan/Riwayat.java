package com.example.kotasultan;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Riwayat extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private boolean finished = false;

    public Riwayat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_riwayat, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.history_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("History").document(MainActivity.Uid).collection("data")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    if (task.getResult()!=null){
                        List<Map<String,Object>> data = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()){
                            data.add(document.getData());
                            mAdapter = new HistoryAdapter(data);
                            recyclerView.setAdapter(mAdapter);
                        }
                    }
                }else{
                    Log.d("Riwayat","failed to retrieve document");
                }
            }
        });
        mAdapter = new HistoryAdapter();
        recyclerView.setAdapter(mAdapter);
        return view;
    }

}
