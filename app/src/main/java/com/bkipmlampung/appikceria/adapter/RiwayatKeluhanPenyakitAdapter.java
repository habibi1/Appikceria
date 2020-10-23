package com.bkipmlampung.appikceria.adapter;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bkipmlampung.appikceria.BuildConfig;
import com.bkipmlampung.appikceria.R;
import com.bkipmlampung.appikceria.model.KeluhanPenyakitResult;
import com.bkipmlampung.appikceria.utils.DownloadFileUtil;
import com.bkipmlampung.appikceria.utils.TimeUtil;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.jsibbold.zoomage.ZoomageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RiwayatKeluhanPenyakitAdapter extends RecyclerView.Adapter<RiwayatKeluhanPenyakitAdapter.ListViewHolder>{

    Context context;
    private List<KeluhanPenyakitResult> list = new ArrayList<>();

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_riwayat_keluhan, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        final KeluhanPenyakitResult keluhanPenyakitResult = list.get(position);

        holder.textViewJenisKomoditas.setText(keluhanPenyakitResult.getJenisKomoditas());
        holder.textViewTanggal.setText(TimeUtil.getDateDDMMMMYYYY(keluhanPenyakitResult.getLog()));
        holder.textViewStatus.setText(keluhanPenyakitResult.getKonfirmasi());

        if (keluhanPenyakitResult.getKonfirmasi().equals(context.getString(R.string.menunggu))){
            holder.imageViewStatus.setImageResource(R.drawable.ic_watch_later);
        } else if (keluhanPenyakitResult.getKonfirmasi().equals(context.getString(R.string.diselesaikan))){
            holder.imageViewStatus.setImageResource(R.drawable.ic_check_circle);
        } else {
            holder.imageViewStatus.setImageResource(R.drawable.ic_cancel);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(keluhanPenyakitResult, holder);
            }
        });
    }

    public void setList(List<KeluhanPenyakitResult> list, Context context){
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
        TextView textViewJenisKomoditas, textViewTanggal, textViewStatus;

        Dialog dialogDetailKeluhanPenyakit;

        ImageView dialogClose, dialogImageViewStatus, imageViewStatus, dialogImageViewLampiran;

        TextView dialogTextViewStatus;

        TextInputEditText dialogEditTextNama, dialogEditTextAlamat, dialogEditTextJenisKomoditas,
                dialogEditTextLuasTotalLahanBudidaya, dialogEditTextLuasTerkenaPenyakit, dialogEditTextGejalaKlinis,
                dialogEditTextJumlahKematian, dialogEditTextTanggalTerjangkit, dialogEditTextKeterangan,
                dialogEditTextNomorTelepon, dialogEditTextLokasiBudidaya;

        MaterialButton dialogButtonDownload;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            dialogZoomable = new Dialog(context);
            dialogZoomable.setContentView(R.layout.dialog_zoomable);

            zoomageView = dialogZoomable.findViewById(R.id.zoomableView);
            dialogZoomableClose = dialogZoomable.findViewById(R.id.iv_dismiss);

            cardView = itemView.findViewById(R.id.cv_item);

            textViewJenisKomoditas = itemView.findViewById(R.id.tv_item_1);
            textViewTanggal = itemView.findViewById(R.id.tv_item_2);
            textViewStatus = itemView.findViewById(R.id.tv_item_3);
            imageViewStatus = itemView.findViewById(R.id.iv_status);

            dialogDetailKeluhanPenyakit = new Dialog(context);
            dialogDetailKeluhanPenyakit.setContentView(R.layout.dialog_detail_keluhan_penyakit);

            dialogClose = dialogDetailKeluhanPenyakit.findViewById(R.id.iv_dismiss);
            dialogImageViewStatus = dialogDetailKeluhanPenyakit.findViewById(R.id.iv_status);
            dialogTextViewStatus = dialogDetailKeluhanPenyakit.findViewById(R.id.tv_status);
            dialogImageViewLampiran = dialogDetailKeluhanPenyakit.findViewById(R.id.image_view_lampiran);

            dialogEditTextNama = dialogDetailKeluhanPenyakit.findViewById(R.id.et_nama);
            dialogEditTextAlamat = dialogDetailKeluhanPenyakit.findViewById(R.id.et_alamat);
            dialogEditTextJenisKomoditas = dialogDetailKeluhanPenyakit.findViewById(R.id.et_jenis_komoditas);
            dialogEditTextLuasTotalLahanBudidaya = dialogDetailKeluhanPenyakit.findViewById(R.id.et_luas_total_lahan_budidaya);
            dialogEditTextLuasTerkenaPenyakit = dialogDetailKeluhanPenyakit.findViewById(R.id.et_luas_yang_terkena_penyakit);
            dialogEditTextGejalaKlinis = dialogDetailKeluhanPenyakit.findViewById(R.id.et_gejala_klinis);
            dialogEditTextJumlahKematian = dialogDetailKeluhanPenyakit.findViewById(R.id.et_jumlah_kematian);
            dialogEditTextTanggalTerjangkit = dialogDetailKeluhanPenyakit.findViewById(R.id.et_tanggal_mulai_terjangkit);
            dialogEditTextKeterangan = dialogDetailKeluhanPenyakit.findViewById(R.id.et_keterangan);
            dialogEditTextNomorTelepon = dialogDetailKeluhanPenyakit.findViewById(R.id.et_nomor_telepon);
            dialogEditTextLokasiBudidaya = dialogDetailKeluhanPenyakit.findViewById(R.id.et_lokasi_budidaya);
            dialogButtonDownload = dialogDetailKeluhanPenyakit.findViewById(R.id.btn_download);
        }
    }

    private void showPopUp(final KeluhanPenyakitResult keluhanPenyakitResult, final ListViewHolder holder){

        Glide.with(context)
                .load(BuildConfig.BASE_URL + keluhanPenyakitResult.getNamaFile())
                .centerCrop()
                .into(holder.dialogImageViewLampiran);

        Glide.with(context)
                .load(BuildConfig.BASE_URL + keluhanPenyakitResult.getNamaFile())
                .into(holder.zoomageView);

        if (keluhanPenyakitResult.getKonfirmasi().equals(context.getString(R.string.menunggu))){
            holder.dialogImageViewStatus.setImageResource(R.drawable.ic_watch_later);
        } else if (keluhanPenyakitResult.getKonfirmasi().equals(context.getString(R.string.diselesaikan))){
            holder.dialogImageViewStatus.setImageResource(R.drawable.ic_check_circle);
        } else {
            holder.dialogImageViewStatus.setImageResource(R.drawable.ic_cancel);
        }

        holder.dialogEditTextNama.setText(keluhanPenyakitResult.getNama());
        holder.dialogEditTextAlamat.setText(keluhanPenyakitResult.getAlamatLengkap());
        holder.dialogEditTextJenisKomoditas.setText(keluhanPenyakitResult.getJenisKomoditas());
        holder.dialogEditTextLuasTotalLahanBudidaya.setText(keluhanPenyakitResult.getLuasTotalLaBu());
        holder.dialogEditTextLuasTerkenaPenyakit.setText(keluhanPenyakitResult.getLuasTerPenyakit());
        holder.dialogEditTextGejalaKlinis.setText(keluhanPenyakitResult.getGejalaK());
        holder.dialogEditTextJumlahKematian.setText(keluhanPenyakitResult.getJumKematian());
        holder.dialogEditTextTanggalTerjangkit.setText(keluhanPenyakitResult.getTanggalTerjangkit());
        holder.dialogTextViewStatus.setText(keluhanPenyakitResult.getKonfirmasi());
        holder.dialogEditTextNomorTelepon.setText(keluhanPenyakitResult.getNo_hp());
        holder.dialogEditTextLokasiBudidaya.setText(keluhanPenyakitResult.getLokasiBudidaya());

        if (keluhanPenyakitResult.getKeterangan() != null){
            holder.dialogEditTextKeterangan.setText(keluhanPenyakitResult.getKeterangan());
        } else {
            holder.dialogEditTextKeterangan.setText("-");
        }

        if (keluhanPenyakitResult.getFileBerkas() != null){
            holder.dialogButtonDownload.setEnabled(true);
        } else {
            holder.dialogButtonDownload.setEnabled(false);
        }

        holder.dialogButtonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(context, context.getString(R.string.izin_ditolak), Toast.LENGTH_SHORT).show();
                } else {
                    DownloadFileUtil.StartDownloading(keluhanPenyakitResult.getFileBerkas(), context);
                }
            }
        });

        holder.dialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.dialogDetailKeluhanPenyakit.dismiss();
            }
        });

        holder.dialogImageViewLampiran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showZoomable(holder);
            }
        });

        Objects.requireNonNull(holder.dialogDetailKeluhanPenyakit.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        holder.dialogDetailKeluhanPenyakit.getWindow().setGravity(Gravity.CENTER);
        holder.dialogDetailKeluhanPenyakit.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.dialogDetailKeluhanPenyakit.setCanceledOnTouchOutside(false);
        holder.dialogDetailKeluhanPenyakit.getWindow().setWindowAnimations(R.style.DialogAnimation_up_down);
        holder.dialogDetailKeluhanPenyakit.show();
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
