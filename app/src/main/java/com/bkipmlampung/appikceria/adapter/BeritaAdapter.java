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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bkipmlampung.appikceria.BuildConfig;
import com.bkipmlampung.appikceria.R;
import com.bkipmlampung.appikceria.model.BeritaResult;
import com.bkipmlampung.appikceria.utils.TimeUtil;
import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.ListViewHolder>{

    Context context;
    private List<BeritaResult> list = new ArrayList<>();

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_berita, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        BeritaResult beritaResult = list.get(position);

        Glide.with(context)
                .load(BuildConfig.BASE_URL + beritaResult.getFileFoto())
                .centerCrop()
                .into(holder.imageView);

        Glide.with(context)
                .load(BuildConfig.BASE_URL + beritaResult.getFileFoto())
                .centerCrop()
                .into(holder.dialogImageView);

        Glide.with(context)
                .load(BuildConfig.BASE_URL + beritaResult.getFileFoto())
                .into(holder.zoomageView);

        holder.textViewNamaBerita.setText(beritaResult.getSubjek());
        holder.textViewDeskripsi.setText(beritaResult.getDeskripsi());
        holder.textViewTanggal.setText(TimeUtil.getDateDDMMMMYYYY(beritaResult.getLog()));

        holder.dialogNamaBerita.setText(beritaResult.getSubjek());
        holder.dialogDeskripsi.setText(beritaResult.getDeskripsi());
        holder.dialogTanggal.setText(TimeUtil.getDateDDMMMMYYYY(beritaResult.getLog()));

        holder.dialogImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showZoomable(holder);
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(holder);
            }
        });

        holder.dialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.dialogDetailBerita.dismiss();
            }
        });
    }

    public void setList(List<BeritaResult> list, Context context){
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

        CardView cardView;
        ImageView imageView;
        TextView textViewNamaBerita, textViewDeskripsi, textViewTanggal;

        Dialog dialogDetailBerita;
        ImageView dialogImageView, dialogClose;;
        TextView dialogNamaBerita, dialogDeskripsi, dialogTanggal;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            dialogZoomable = new Dialog(context);
            dialogZoomable.setContentView(R.layout.dialog_zoomable);

            zoomageView = dialogZoomable.findViewById(R.id.zoomableView);
            dialogZoomableClose = dialogZoomable.findViewById(R.id.iv_dismiss);

            cardView = itemView.findViewById(R.id.cv_item);
            imageView = itemView.findViewById(R.id.image_item);
            textViewNamaBerita = itemView.findViewById(R.id.tv_item_1);
            textViewDeskripsi = itemView.findViewById(R.id.tv_item_2);
            textViewTanggal = itemView.findViewById(R.id.tv_item_3);

            dialogDetailBerita = new Dialog(context);
            dialogDetailBerita.setContentView(R.layout.dialog_detail_berita);

            dialogImageView = dialogDetailBerita.findViewById(R.id.image_item);
            dialogClose = dialogDetailBerita.findViewById(R.id.iv_dismiss);

            dialogNamaBerita = dialogDetailBerita.findViewById(R.id.et_judul);
            dialogDeskripsi = dialogDetailBerita.findViewById(R.id.et_deskripsi);
            dialogTanggal = dialogDetailBerita.findViewById(R.id.et_tanggal);
        }
    }

    private void showPopUp(ListViewHolder holder){

        Objects.requireNonNull(holder.dialogDetailBerita.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        holder.dialogDetailBerita.getWindow().setGravity(Gravity.CENTER);
        holder.dialogDetailBerita.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.dialogDetailBerita.setCanceledOnTouchOutside(false);
        holder.dialogDetailBerita.getWindow().setWindowAnimations(R.style.DialogAnimation_up_down);
        holder.dialogDetailBerita.show();
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
