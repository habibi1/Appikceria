package com.bkipmlampung.appikceria.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bkipmlampung.appikceria.R;
import com.bkipmlampung.appikceria.model.BantuanResult;

import java.util.ArrayList;
import java.util.List;

public class BantuanAdapter extends RecyclerView.Adapter<BantuanAdapter.ListViewHolder>{

    private List<BantuanResult> list = new ArrayList<>();

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bantuan, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        BantuanResult bantuanResult = list.get(position);

        holder.textViewJudul.setText(bantuanResult.getJudul());
        holder.textViewDeskripsi.setText(bantuanResult.getDeskripsi());
    }

    public void setList(List<BantuanResult> list){
        this.list.clear();
        this.list.addAll(list);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView textViewJudul, textViewDeskripsi;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cv_item);
            textViewJudul = itemView.findViewById(R.id.tv_item_1);
            textViewDeskripsi = itemView.findViewById(R.id.tv_item_2);
        }
    }
}
