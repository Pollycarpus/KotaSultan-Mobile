package com.example.kotasultan;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<Map<String,Object>> mData;

    public HistoryAdapter(){
        mData = new ArrayList<>();
        Map<String,Object> element = new HashMap<>();
        element.put("nama","");
        element.put("jenis","");
        element.put("nilai","");
        element.put("currency","");
        element.put("tanggal","");
        mData.add(element);
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView nama,jenis,nilai,currency,tanggal;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = (TextView) itemView.findViewById(R.id.template_nama);
            jenis = (TextView) itemView.findViewById(R.id.template_jenis);
            nilai = (TextView) itemView.findViewById(R.id.template_nilai);
            currency = (TextView) itemView.findViewById(R.id.template_currency);
            tanggal = (TextView) itemView.findViewById(R.id.template_tanggal);
        }
    }

    public HistoryAdapter(List<Map<String,Object>> data){
        mData = data;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = (View) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_template,viewGroup,false);
        HistoryViewHolder vh = new HistoryViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder historyViewHolder, int i) {
        Map<String,Object> element = mData.get(i);
        String nama = (String) element.get("nama");
        String jenis = (String) element.get("jenis");
        String nilai = (String) element.get("nilai");
        String currency = (String) element.get("currency");
        String tanggal = (String) element.get("tanggal");
        historyViewHolder.nama.setText(nama);
        historyViewHolder.jenis.setText(jenis);
        historyViewHolder.nilai.setText(nilai);
        historyViewHolder.currency.setText(currency);
        historyViewHolder.tanggal.setText(tanggal);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
