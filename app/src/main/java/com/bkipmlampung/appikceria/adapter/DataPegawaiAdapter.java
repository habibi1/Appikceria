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
import com.bkipmlampung.appikceria.model.DataPegawaiResult;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.jsibbold.zoomage.ZoomageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataPegawaiAdapter extends RecyclerView.Adapter<DataPegawaiAdapter.ListViewHolder> {

    Context context;
    private List<DataPegawaiResult> list = new ArrayList<>();

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_pegawai, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        DataPegawaiResult dataPegawaiResult = list.get(position);

        Glide.with(context)
                .load(BuildConfig.BASE_URL + dataPegawaiResult.getFileFoto())
                .centerCrop()
                .into(holder.imageView);

        Glide.with(context)
                .load(BuildConfig.BASE_URL + dataPegawaiResult.getFileFoto())
                .centerCrop()
                .into(holder.dialogImageViewProfil);

        Glide.with(context)
                .load(BuildConfig.BASE_URL + dataPegawaiResult.getFileFoto())
                .into(holder.zoomageView);

        holder.textViewNama.setText(dataPegawaiResult.getNama());
        holder.textViewNIP.setText(dataPegawaiResult.getNip());
        holder.textViewJabatan.setText(dataPegawaiResult.getJabatan());

        holder.editTextNama.setText(dataPegawaiResult.getNama());
        holder.editTextNip.setText(dataPegawaiResult.getNip());
        holder.editTextJabatan.setText(dataPegawaiResult.getJabatan());
        holder.editTextTahunBergabung.setText(dataPegawaiResult.getTahunBergabung());
        holder.editTextTempatLahir.setText(dataPegawaiResult.getTempatLahir());
        holder.editTextTanggalLahir.setText(dataPegawaiResult.getTanggalLahir());
        holder.editTextAlamat.setText(dataPegawaiResult.getAlamat());

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
                holder.dialogDetailPegawai.dismiss();
            }
        });
    }

    public void setList(List<DataPegawaiResult> list, Context context){
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
        TextView textViewNama, textViewNIP, textViewJabatan;

        Dialog dialogDetailPegawai;

        ImageView dialogImageViewProfil, dialogClose;
        TextInputEditText editTextNama, editTextNip, editTextJabatan, editTextTahunBergabung,
                editTextTempatLahir, editTextTanggalLahir, editTextAlamat;

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
            textViewJabatan = itemView.findViewById(R.id.tv_item_3);

            dialogDetailPegawai = new Dialog(context);
            dialogDetailPegawai.setContentView(R.layout.dialog_detail_pegawai);

            dialogImageViewProfil = dialogDetailPegawai.findViewById(R.id.image_item);
            dialogClose = dialogDetailPegawai.findViewById(R.id.iv_dismiss);

            editTextNama = dialogDetailPegawai.findViewById(R.id.et_nama);
            editTextNip = dialogDetailPegawai.findViewById(R.id.et_nip);
            editTextJabatan = dialogDetailPegawai.findViewById(R.id.et_jabatan);
            editTextTahunBergabung = dialogDetailPegawai.findViewById(R.id.et_tahun_bergabung);
            editTextTempatLahir = dialogDetailPegawai.findViewById(R.id.et_tempat_lahir);
            editTextTanggalLahir = dialogDetailPegawai.findViewById(R.id.et_tanggal_lahir);
            editTextAlamat = dialogDetailPegawai.findViewById(R.id.et_alamat);
        }
    }

    private void showPopUp(ListViewHolder holder){

        Objects.requireNonNull(holder.dialogDetailPegawai.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        holder.dialogDetailPegawai.getWindow().setGravity(Gravity.CENTER);
        holder.dialogDetailPegawai.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.dialogDetailPegawai.setCanceledOnTouchOutside(false);
        holder.dialogDetailPegawai.getWindow().setWindowAnimations(R.style.DialogAnimation_up_down);
        holder.dialogDetailPegawai.show();
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
