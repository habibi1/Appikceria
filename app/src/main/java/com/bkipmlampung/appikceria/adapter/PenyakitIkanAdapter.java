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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bkipmlampung.appikceria.R;
import com.bkipmlampung.appikceria.model.PenyakitIkanResult;
import com.bkipmlampung.appikceria.utils.DownloadFileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PenyakitIkanAdapter extends RecyclerView.Adapter<PenyakitIkanAdapter.ListViewHolder>{


    Context context;
    private List<PenyakitIkanResult> list = new ArrayList<>();

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_penyakit_ikan, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        final PenyakitIkanResult penyakitIkanResult = list.get(position);

        holder.textViewNamaPenyakit.setText(penyakitIkanResult.getNamaPenyakit());
        holder.textViewNamaLatin.setText(penyakitIkanResult.getNamaLatin());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(penyakitIkanResult, holder);
            }
        });

    }

    public void setList(List<PenyakitIkanResult> list, Context context){
        this.list.clear();
        this.list.addAll(list);
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView textViewNamaPenyakit, textViewNamaLatin;

        Dialog dialogDetailPenyakitIkan;

        ImageView dialogClose;
        TextView dialogEditTextNamaPenyakit, dialogEditTextNamaLatin, dialogEditTextGejala, dialogEditTextDeskripsi;

        Button dialogButtonDownload;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cv_item);
            textViewNamaPenyakit = itemView.findViewById(R.id.tv_item_1);
            textViewNamaLatin = itemView.findViewById(R.id.tv_item_2);

            dialogDetailPenyakitIkan = new Dialog(context);
            dialogDetailPenyakitIkan.setContentView(R.layout.dialog_detail_penyakit_ikan);

            dialogClose = dialogDetailPenyakitIkan.findViewById(R.id.iv_dismiss);

            dialogEditTextNamaPenyakit = dialogDetailPenyakitIkan.findViewById(R.id.et_nama_penyakit);
            dialogEditTextNamaLatin = dialogDetailPenyakitIkan.findViewById(R.id.et_nama_latin);
            dialogEditTextGejala = dialogDetailPenyakitIkan.findViewById(R.id.et_gejala);
            dialogEditTextDeskripsi = dialogDetailPenyakitIkan.findViewById(R.id.et_deskripsi);

            dialogButtonDownload = dialogDetailPenyakitIkan.findViewById(R.id.btn_download);
        }
    }

    private void showPopUp(final PenyakitIkanResult penyakitIkanResult, final ListViewHolder holder){

        holder.dialogEditTextNamaPenyakit.setText(penyakitIkanResult.getNamaPenyakit());
        holder.dialogEditTextNamaLatin.setText(penyakitIkanResult.getNamaLatin());
        holder.dialogEditTextGejala.setText(penyakitIkanResult.getGejala());
        holder.dialogEditTextDeskripsi.setText(penyakitIkanResult.getDeskripsi());

        holder.dialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.dialogDetailPenyakitIkan.dismiss();
            }
        });

        holder.dialogButtonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(context, context.getString(R.string.izin_ditolak), Toast.LENGTH_SHORT).show();
                } else {
                    DownloadFileUtil.StartDownloading(penyakitIkanResult.getFileBerkas(), context);
                }
            }
        });

        Objects.requireNonNull(holder.dialogDetailPenyakitIkan.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        holder.dialogDetailPenyakitIkan.getWindow().setGravity(Gravity.CENTER);
        holder.dialogDetailPenyakitIkan.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.dialogDetailPenyakitIkan.setCanceledOnTouchOutside(false);
        holder.dialogDetailPenyakitIkan.getWindow().setWindowAnimations(R.style.DialogAnimation_up_down);
        holder.dialogDetailPenyakitIkan.show();
    }
}
