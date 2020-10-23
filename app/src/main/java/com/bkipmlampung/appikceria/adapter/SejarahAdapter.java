package com.bkipmlampung.appikceria.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bkipmlampung.appikceria.BuildConfig;
import com.bkipmlampung.appikceria.R;
import com.bkipmlampung.appikceria.model.SejarahResult;
import com.bkipmlampung.appikceria.utils.TimeUtil;
import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SejarahAdapter extends RecyclerView.Adapter<SejarahAdapter.ListViewHolder>{

    Context context;
    private List<SejarahResult> list = new ArrayList<>();

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sejarah, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        SejarahResult sejarahResult = list.get(position);

        Glide.with(context)
                .load(BuildConfig.BASE_URL + sejarahResult.getFileFoto())
                .centerCrop()
                .into(holder.imageView);

        Glide.with(context)
                .load(BuildConfig.BASE_URL + sejarahResult.getFileFoto())
                .into(holder.zoomageView);

        holder.textViewJudul.setText(sejarahResult.getSubjek());
        holder.textViewTanggal.setText(TimeUtil.getDateDDMMMMYYYY(sejarahResult.getTanggal()));
        holder.textViewDeskripsi.setText(sejarahResult.getDeskripsi());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showZoomable(holder);
            }
        });
    }

    public void setList(List<SejarahResult> list, Context context){
        this.list.clear();
        this.list.addAll(list);
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        Dialog dialogZoomable;
        ZoomageView zoomageView;
        ImageView dialogZoomableClose;

        ImageView imageView;
        TextView textViewJudul, textViewTanggal, textViewDeskripsi;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            dialogZoomable = new Dialog(context);
            dialogZoomable.setContentView(R.layout.dialog_zoomable);

            zoomageView = dialogZoomable.findViewById(R.id.zoomableView);
            dialogZoomableClose = dialogZoomable.findViewById(R.id.iv_dismiss);

            imageView = itemView.findViewById(R.id.image_view);
            textViewJudul = itemView.findViewById(R.id.et_judul);
            textViewTanggal = itemView.findViewById(R.id.et_waktu);
            textViewDeskripsi = itemView.findViewById(R.id.et_deskripsi);
        }
    }

    private void showZoomable(final ListViewHolder holder){

        holder.dialogZoomableClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.dialogZoomable.dismiss();
            }
        });

        Objects.requireNonNull(holder.dialogZoomable.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        holder.dialogZoomable.getWindow().setGravity(Gravity.CENTER);
        holder.dialogZoomable.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        holder.dialogZoomable.setCanceledOnTouchOutside(false);
        holder.dialogZoomable.getWindow().setWindowAnimations(R.style.DialogAnimation_up_down);
        holder.dialogZoomable.show();
    }
}
