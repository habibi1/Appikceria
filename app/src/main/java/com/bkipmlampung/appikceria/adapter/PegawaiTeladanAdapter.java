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
import com.bkipmlampung.appikceria.model.PegawaiTeladanResult;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.jsibbold.zoomage.ZoomageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PegawaiTeladanAdapter extends RecyclerView.Adapter<PegawaiTeladanAdapter.ListViewHolder>{

    Context context;
    private List<PegawaiTeladanResult> list = new ArrayList<>();

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pegawai_teladan, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        PegawaiTeladanResult pegawaiTeladanResult = list.get(position);

        Glide.with(context)
                .load(BuildConfig.BASE_URL + pegawaiTeladanResult.getFileFoto())
                .centerCrop()
                .into(holder.imageView);

        Glide.with(context)
                .load(BuildConfig.BASE_URL + pegawaiTeladanResult.getFileFoto())
                .centerCrop()
                .into(holder.dialogImageViewProfil);

        Glide.with(context)
                .load(BuildConfig.BASE_URL + pegawaiTeladanResult.getFileFoto())
                .into(holder.zoomageView);

        holder.textViewNama.setText(pegawaiTeladanResult.getNama());
        holder.textViewNIP.setText(pegawaiTeladanResult.getNip());

        holder.dialogEditTextNama.setText(pegawaiTeladanResult.getNama());
        holder.dialogEditTextNip.setText(pegawaiTeladanResult.getNip());
        holder.dialogEditTextJabatan.setText(pegawaiTeladanResult.getJabatan());
        holder.dialogEditTextDeskripsi.setText(pegawaiTeladanResult.getDeskripsi());

        holder.dialogImageViewProfil.setOnClickListener(new View.OnClickListener() {
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
                holder.dialogDetailPegawaiTeladan.dismiss();
            }
        });
    }

    public void setList(List<PegawaiTeladanResult> list, Context context){
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
        TextView textViewNama, textViewNIP;

        Dialog dialogDetailPegawaiTeladan;

        ImageView dialogImageViewProfil, dialogClose;
        TextInputEditText dialogEditTextNama, dialogEditTextNip, dialogEditTextJabatan, dialogEditTextDeskripsi;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            dialogZoomable = new Dialog(context);
            dialogZoomable.setContentView(R.layout.dialog_zoomable);

            zoomageView = dialogZoomable.findViewById(R.id.zoomableView);
            dialogZoomableClose = dialogZoomable.findViewById(R.id.iv_dismiss);

            cardView = itemView.findViewById(R.id.cv_item);
            imageView = itemView.findViewById(R.id.image_item);
            textViewNama = itemView.findViewById(R.id.tv_item_1);
            textViewNIP = itemView.findViewById(R.id.tv_item_2);

            dialogDetailPegawaiTeladan = new Dialog(context);
            dialogDetailPegawaiTeladan.setContentView(R.layout.dialog_detail_pegawai_teladan);

            dialogImageViewProfil = dialogDetailPegawaiTeladan.findViewById(R.id.image_item);
            dialogClose = dialogDetailPegawaiTeladan.findViewById(R.id.iv_dismiss);

            dialogEditTextNama = dialogDetailPegawaiTeladan.findViewById(R.id.et_nama);
            dialogEditTextNip = dialogDetailPegawaiTeladan.findViewById(R.id.et_nip);
            dialogEditTextJabatan = dialogDetailPegawaiTeladan.findViewById(R.id.et_jabatan);
            dialogEditTextDeskripsi = dialogDetailPegawaiTeladan.findViewById(R.id.et_deskripsi);
        }
    }

    private void showPopUp(ListViewHolder holder){

        Objects.requireNonNull(holder.dialogDetailPegawaiTeladan.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        holder.dialogDetailPegawaiTeladan.getWindow().setGravity(Gravity.CENTER);
        holder.dialogDetailPegawaiTeladan.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.dialogDetailPegawaiTeladan.setCanceledOnTouchOutside(false);
        holder.dialogDetailPegawaiTeladan.getWindow().setWindowAnimations(R.style.DialogAnimation_up_down);
        holder.dialogDetailPegawaiTeladan.show();
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
