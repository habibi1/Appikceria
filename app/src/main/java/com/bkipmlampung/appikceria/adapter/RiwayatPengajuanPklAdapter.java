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
import com.bkipmlampung.appikceria.model.PengajuanPklResult;
import com.bkipmlampung.appikceria.utils.DownloadFileUtil;
import com.bkipmlampung.appikceria.utils.TimeUtil;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.jsibbold.zoomage.ZoomageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RiwayatPengajuanPklAdapter extends RecyclerView.Adapter<RiwayatPengajuanPklAdapter.ListViewHolder>{

    Context context;
    private List<PengajuanPklResult> list = new ArrayList<>();

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_riwayat_pkl, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        final PengajuanPklResult pengajuanPklResult = list.get(position);

        holder.textViewNama.setText(pengajuanPklResult.getNama());
        holder.textViewTanggalLog.setText(TimeUtil.getDateDDMMMMYYYY(pengajuanPklResult.getLog()));
        holder.textViewStatus.setText(pengajuanPklResult.getKonfirmasi());

        if (pengajuanPklResult.getKonfirmasi().equals(context.getString(R.string.menunggu))){
            holder.imageViewStatus.setImageResource(R.drawable.ic_watch_later);
        } else if (pengajuanPklResult.getKonfirmasi().equals(context.getString(R.string.disetujui))){
            holder.imageViewStatus.setImageResource(R.drawable.ic_check_circle);
        } else {
            holder.imageViewStatus.setImageResource(R.drawable.ic_cancel);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(pengajuanPklResult, holder);
            }
        });

    }

    public void setList(List<PengajuanPklResult> list, Context context){
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
        TextView textViewNama, textViewTanggalLog, textViewStatus;

        Dialog dialogDetailPengajuanPkl;

        ImageView dialogClose, dialogImageViewStatus, imageViewStatus, dialogImageViewLampiran;

        TextView dialogTextViewStatus;

        TextInputEditText dialogEditTextNama, dialogEditTextTempatLahir, dialogEditTextTanggalLahir,
                dialogEditTextSekolahUniversitas, dialogEditTextJurusan, dialogEditTextNilai,
                dialogEditTextKeterangan;
        MaterialButton dialogDownload;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            dialogZoomable = new Dialog(context);
            dialogZoomable.setContentView(R.layout.dialog_zoomable);

            zoomageView = dialogZoomable.findViewById(R.id.zoomableView);
            dialogZoomableClose = dialogZoomable.findViewById(R.id.iv_dismiss);

            cardView = itemView.findViewById(R.id.cv_item);

            textViewNama = itemView.findViewById(R.id.tv_item_1);
            textViewTanggalLog = itemView.findViewById(R.id.tv_item_2);
            textViewStatus = itemView.findViewById(R.id.tv_item_3);
            imageViewStatus = itemView.findViewById(R.id.iv_status);

        }
    }

    private void showPopUp(final PengajuanPklResult pengajuanPklResult, final ListViewHolder holder){

        holder.dialogDetailPengajuanPkl = new Dialog(context);
        holder.dialogDetailPengajuanPkl.setContentView(R.layout.dialog_detail_pengajuan_pkl);

        holder.dialogClose = holder.dialogDetailPengajuanPkl.findViewById(R.id.iv_dismiss);
        holder.dialogImageViewStatus = holder.dialogDetailPengajuanPkl.findViewById(R.id.iv_status);
        holder.dialogTextViewStatus = holder.dialogDetailPengajuanPkl.findViewById(R.id.tv_status);
        holder.dialogDownload = holder.dialogDetailPengajuanPkl.findViewById(R.id.btn_download);

        holder.dialogEditTextNama = holder.dialogDetailPengajuanPkl.findViewById(R.id.et_nama);
        holder.dialogEditTextTempatLahir = holder.dialogDetailPengajuanPkl.findViewById(R.id.et_tempat_lahir);
        holder.dialogEditTextTanggalLahir = holder.dialogDetailPengajuanPkl.findViewById(R.id.et_tanggal_lahir);
        holder.dialogEditTextSekolahUniversitas = holder.dialogDetailPengajuanPkl.findViewById(R.id.et_sekolah_universitas);
        holder.dialogEditTextJurusan = holder.dialogDetailPengajuanPkl.findViewById(R.id.et_jurusan);
        holder.dialogEditTextNilai = holder.dialogDetailPengajuanPkl.findViewById(R.id.et_nilai);
        holder.dialogEditTextKeterangan = holder.dialogDetailPengajuanPkl.findViewById(R.id.et_keterangan);
        holder.dialogImageViewLampiran = holder.dialogDetailPengajuanPkl.findViewById(R.id.image_view_lampiran);

        if (pengajuanPklResult.getKonfirmasi().equals(context.getString(R.string.menunggu))){
            holder.dialogImageViewStatus.setImageResource(R.drawable.ic_watch_later);
        } else if (pengajuanPklResult.getKonfirmasi().equals(context.getString(R.string.disetujui))){
            holder.dialogImageViewStatus.setImageResource(R.drawable.ic_check_circle);
        } else {
            holder.dialogImageViewStatus.setImageResource(R.drawable.ic_cancel);
        }

        holder.dialogEditTextNama.setText(pengajuanPklResult.getNama());
        holder.dialogEditTextTempatLahir.setText(pengajuanPklResult.getTempatLahir());
        holder.dialogEditTextTanggalLahir.setText(pengajuanPklResult.getTahunLahir());
        holder.dialogEditTextSekolahUniversitas.setText(pengajuanPklResult.getNamaUniv());
        holder.dialogEditTextJurusan.setText(pengajuanPklResult.getNamaJur());

        if (pengajuanPklResult.getNilaiPkl()!=null)
            holder.dialogEditTextNilai.setText(pengajuanPklResult.getNilaiPkl());
        else
            holder.dialogEditTextNilai.setText("-");

        if (pengajuanPklResult.getKeterangan()!=null)
            holder.dialogEditTextKeterangan.setText(pengajuanPklResult.getKeterangan());
        else
            holder.dialogEditTextKeterangan.setText("-");

        if (pengajuanPklResult.getFileBerkasPkl()!=null)
            holder.dialogDownload.setEnabled(true);
        else
            holder.dialogDownload.setEnabled(false);

        holder.dialogTextViewStatus.setText(pengajuanPklResult.getKonfirmasi());

        Glide.with(context)
                .load(BuildConfig.BASE_URL + pengajuanPklResult.getFileBerkas())
                .centerCrop()
                .into(holder.dialogImageViewLampiran);

        Glide.with(context)
                .load(BuildConfig.BASE_URL + pengajuanPklResult.getFileBerkas())
                .into(holder.zoomageView);

        holder.dialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.dialogDetailPengajuanPkl.dismiss();
            }
        });

        holder.dialogDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(context, context.getString(R.string.izin_ditolak), Toast.LENGTH_SHORT).show();
                } else {
                    DownloadFileUtil.StartDownloading(pengajuanPklResult.getFileBerkasPkl(), context);
                }
            }
        });

        holder.dialogImageViewLampiran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showZoomable(holder);
            }
        });

        Objects.requireNonNull(holder.dialogDetailPengajuanPkl.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        holder.dialogDetailPengajuanPkl.getWindow().setGravity(Gravity.CENTER);
        holder.dialogDetailPengajuanPkl.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.dialogDetailPengajuanPkl.setCanceledOnTouchOutside(false);
        holder.dialogDetailPengajuanPkl.getWindow().setWindowAnimations(R.style.DialogAnimation_up_down);
        holder.dialogDetailPengajuanPkl.show();
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
