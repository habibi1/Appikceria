package com.bkipmlampung.appikceria.adapter;

import android.annotation.SuppressLint;
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
import com.bkipmlampung.appikceria.model.LaluLintasResult;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.jsibbold.zoomage.ZoomageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LaluLintasAdapter extends RecyclerView.Adapter<LaluLintasAdapter.ListViewHolder>{

    Context context;
    private List<LaluLintasResult> list = new ArrayList<>();

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lalu_lintas, parent, false);
        return new ListViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        LaluLintasResult laluLintasResult = list.get(position);

        Glide.with(context)
                .load(BuildConfig.BASE_URL + laluLintasResult.getFileFoto())
                .centerCrop()
                .into(holder.imageView);

        Glide.with(context)
                .load(BuildConfig.BASE_URL + laluLintasResult.getFileFoto())
                .centerCrop()
                .into(holder.dialogImageView);

        Glide.with(context)
                .load(BuildConfig.BASE_URL + laluLintasResult.getFileFoto())
                .into(holder.zoomageView);

        holder.textViewJudul.setText(laluLintasResult.getSubjekJudul());
        holder.textViewKeterangan.setText(laluLintasResult.getKeterangan());
        holder.textViewTahun.setText(laluLintasResult.getBulan() + " " + laluLintasResult.getTahun());

        holder.dialogEditTextJudul.setText(laluLintasResult.getSubjekJudul());
        holder.dialogEditTextKeterangan.setText(laluLintasResult.getKeterangan());
        holder.dialogEditTextTanggal.setText(laluLintasResult.getBulan() + " " + laluLintasResult.getTahun());

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
                holder.dialogDetailLaluLintas.dismiss();
            }
        });
    }

    public void setList(List<LaluLintasResult> list, Context context){
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

        MaterialCardView cardView;
        ImageView imageView;
        TextView textViewJudul, textViewKeterangan, textViewTahun;

        Dialog dialogDetailLaluLintas;

        ImageView dialogImageView, dialogClose;;
        TextInputEditText dialogEditTextJudul, dialogEditTextKeterangan, dialogEditTextTanggal;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            dialogZoomable = new Dialog(context);
            dialogZoomable.setContentView(R.layout.dialog_zoomable);

            zoomageView = dialogZoomable.findViewById(R.id.zoomableView);
            dialogZoomableClose = dialogZoomable.findViewById(R.id.iv_dismiss);

            cardView = itemView.findViewById(R.id.cv_item);
            imageView = itemView.findViewById(R.id.image_item);
            textViewJudul = itemView.findViewById(R.id.tv_item_1);
            textViewKeterangan = itemView.findViewById(R.id.tv_item_2);
            textViewTahun = itemView.findViewById(R.id.tv_item_3);

            dialogDetailLaluLintas = new Dialog(context);
            dialogDetailLaluLintas.setContentView(R.layout.dialog_detail_lalu_lintas);

            dialogImageView = dialogDetailLaluLintas.findViewById(R.id.image_item);
            dialogClose = dialogDetailLaluLintas.findViewById(R.id.iv_dismiss);

            dialogEditTextJudul = dialogDetailLaluLintas.findViewById(R.id.et_judul);
            dialogEditTextKeterangan = dialogDetailLaluLintas.findViewById(R.id.et_keterangan);
            dialogEditTextTanggal = dialogDetailLaluLintas.findViewById(R.id.et_waktu);
        }
    }

    private void showPopUp(ListViewHolder holder){

        Objects.requireNonNull(holder.dialogDetailLaluLintas.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        holder.dialogDetailLaluLintas.getWindow().setGravity(Gravity.CENTER);
        holder.dialogDetailLaluLintas.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.dialogDetailLaluLintas.setCanceledOnTouchOutside(false);
        holder.dialogDetailLaluLintas.getWindow().setWindowAnimations(R.style.DialogAnimation_up_down);
        holder.dialogDetailLaluLintas.show();
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
