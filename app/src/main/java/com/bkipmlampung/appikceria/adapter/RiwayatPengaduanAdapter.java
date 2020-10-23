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
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bkipmlampung.appikceria.BuildConfig;
import com.bkipmlampung.appikceria.R;
import com.bkipmlampung.appikceria.model.PengaduanResult;
import com.bkipmlampung.appikceria.utils.DownloadFileUtil;
import com.bkipmlampung.appikceria.utils.TimeUtil;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.jsibbold.zoomage.ZoomageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RiwayatPengaduanAdapter extends RecyclerView.Adapter<RiwayatPengaduanAdapter.ListViewHolder>{

    Context context;
    private List<PengaduanResult> list = new ArrayList<>();

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_riwayat_pengaduan, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        final PengaduanResult pengaduanResult = list.get(position);

        holder.textViewBidangPengaduan.setText(pengaduanResult.getBidangPelayanan());
        holder.textViewTanggal.setText(TimeUtil.getDateDDMMMMYYYY(pengaduanResult.getLog()));
        holder.textViewStatus.setText(pengaduanResult.getKonfirmasi());

        if (pengaduanResult.getKonfirmasi().equals(context.getString(R.string.menunggu))){
            holder.imageViewStatus.setImageResource(R.drawable.ic_watch_later);
        } else if (pengaduanResult.getKonfirmasi().equals(context.getString(R.string.diselesaikan))){
            holder.imageViewStatus.setImageResource(R.drawable.ic_check_circle);
        } else {
            holder.imageViewStatus.setImageResource(R.drawable.ic_cancel);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(pengaduanResult, holder);
            }
        });
    }

    public void setList(List<PengaduanResult> list, Context context){
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
        TextView textViewBidangPengaduan, textViewTanggal, textViewStatus;

        Dialog dialogDetailPengaduan;

        ImageView dialogClose, dialogImageViewStatus, imageViewStatus, dialogImageViewLampiran;

        TextView dialogTextViewStatus;

        TextInputEditText dialogEditTextNama, dialogEditTextAlamat, dialogEditTextInstansi,
                dialogEditTextTujuanPengaduan, dialogEditTextSumberInformasi,
                dialogEditTextIsiAduan, dialogEditTextKeterangan;

        AutoCompleteTextView dialogDropdownBidangPelayanan;

        SwitchMaterial switch_lampiran;

        MaterialButton dialogDownload;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            dialogZoomable = new Dialog(context);
            dialogZoomable.setContentView(R.layout.dialog_zoomable);

            zoomageView = dialogZoomable.findViewById(R.id.zoomableView);
            dialogZoomableClose = dialogZoomable.findViewById(R.id.iv_dismiss);

            cardView = itemView.findViewById(R.id.cv_item);

            textViewBidangPengaduan = itemView.findViewById(R.id.tv_item_1);
            textViewTanggal = itemView.findViewById(R.id.tv_item_2);
            textViewStatus = itemView.findViewById(R.id.tv_item_3);
            imageViewStatus = itemView.findViewById(R.id.iv_status);

            dialogDetailPengaduan = new Dialog(context);
            dialogDetailPengaduan.setContentView(R.layout.dialog_detail_pengaduan);

            dialogClose = dialogDetailPengaduan.findViewById(R.id.iv_dismiss);
            dialogImageViewStatus = dialogDetailPengaduan.findViewById(R.id.iv_status);
            dialogTextViewStatus = dialogDetailPengaduan.findViewById(R.id.tv_status);
            dialogDownload = dialogDetailPengaduan.findViewById(R.id.btn_download);

            dialogEditTextKeterangan = dialogDetailPengaduan.findViewById(R.id.et_keterangan);
            dialogEditTextNama = dialogDetailPengaduan.findViewById(R.id.et_nama);
            dialogEditTextAlamat = dialogDetailPengaduan.findViewById(R.id.et_alamat);
            dialogEditTextInstansi = dialogDetailPengaduan.findViewById(R.id.et_instansi);
            dialogDropdownBidangPelayanan = dialogDetailPengaduan.findViewById(R.id.act_bidang_pelayanan);
            dialogEditTextTujuanPengaduan = dialogDetailPengaduan.findViewById(R.id.et_tujuan_pengaduan);
            dialogEditTextSumberInformasi = dialogDetailPengaduan.findViewById(R.id.et_sumber_informasi);
            dialogEditTextIsiAduan = dialogDetailPengaduan.findViewById(R.id.et_isi_aduan);
            dialogImageViewLampiran = dialogDetailPengaduan.findViewById(R.id.image_view_lampiran);
            switch_lampiran = dialogDetailPengaduan.findViewById(R.id.switch_lampiran);

        }
    }

    private void showPopUp(final PengaduanResult pengaduanResult, final ListViewHolder holder){

        if (pengaduanResult.getKonfirmasi().equals(context.getString(R.string.menunggu))){
            holder.dialogImageViewStatus.setImageResource(R.drawable.ic_watch_later);
        } else if (pengaduanResult.getKonfirmasi().equals(context.getString(R.string.diselesaikan))){
            holder.dialogImageViewStatus.setImageResource(R.drawable.ic_check_circle);
        } else {
            holder.dialogImageViewStatus.setImageResource(R.drawable.ic_cancel);
        }

        holder.dialogEditTextNama.setText(pengaduanResult.getNama());
        holder.dialogEditTextAlamat.setText(pengaduanResult.getAlamat());
        holder.dialogEditTextInstansi.setText(pengaduanResult.getPekerjaanI());
        holder.dialogDropdownBidangPelayanan.setText(pengaduanResult.getBidangPelayanan());
        holder.dialogEditTextTujuanPengaduan.setText(pengaduanResult.getTujuanPengaduan());
        holder.dialogEditTextSumberInformasi.setText(pengaduanResult.getSumberInformasi());
        holder.dialogEditTextIsiAduan.setText(pengaduanResult.getIsiAduan());
        holder.dialogTextViewStatus.setText(pengaduanResult.getKonfirmasi());

        if (pengaduanResult.getKeterangan() != null){
            holder.dialogEditTextKeterangan.setText(pengaduanResult.getKeterangan());
        } else {
            holder.dialogEditTextKeterangan.setText("-");
        }

        if (pengaduanResult.getFileLampiran()!=null){
            holder.switch_lampiran.setChecked(true);
            holder.dialogImageViewLampiran.setVisibility(View.VISIBLE);

            Glide.with(context)
                    .load(BuildConfig.BASE_URL + pengaduanResult.getFileLampiran())
                    .centerCrop()
                    .into(holder.dialogImageViewLampiran);

            Glide.with(context)
                    .load(BuildConfig.BASE_URL + pengaduanResult.getFileLampiran())
                    .into(holder.zoomageView);
        } else {
            holder.switch_lampiran.setChecked(false);
            holder.dialogImageViewLampiran.setVisibility(View.GONE);
        }

        if (pengaduanResult.getFileBerkas()!=null){
            holder.dialogDownload.setEnabled(true);
        } else {
            holder.dialogDownload.setEnabled(false);
        }

        holder.dialogDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(context, context.getString(R.string.izin_ditolak), Toast.LENGTH_SHORT).show();
                } else {
                    DownloadFileUtil.StartDownloading(pengaduanResult.getFileBerkas(), context);
                }
            }
        });

        holder.dialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.dialogDetailPengaduan.dismiss();
            }
        });

        holder.dialogImageViewLampiran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showZoomable(holder);
            }
        });

        Objects.requireNonNull(holder.dialogDetailPengaduan.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        holder.dialogDetailPengaduan.getWindow().setGravity(Gravity.CENTER);
        holder.dialogDetailPengaduan.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.dialogDetailPengaduan.setCanceledOnTouchOutside(false);
        holder.dialogDetailPengaduan.getWindow().setWindowAnimations(R.style.DialogAnimation_up_down);
        holder.dialogDetailPengaduan.show();
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
