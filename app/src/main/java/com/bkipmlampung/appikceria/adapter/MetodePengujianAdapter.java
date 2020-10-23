package com.bkipmlampung.appikceria.adapter;

import android.Manifest;
import android.app.Activity;
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
import com.bkipmlampung.appikceria.model.MetodePengujianResult;
import com.bkipmlampung.appikceria.utils.DownloadFileUtil;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MetodePengujianAdapter extends RecyclerView.Adapter<MetodePengujianAdapter.ListViewHolder>{

    private int STATE_READ_EXTERNAL_STORAGE = 7;
    Context context;
    Activity activity;
    private List<MetodePengujianResult> list = new ArrayList<>();

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_metode_pengujian, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        final MetodePengujianResult metodePengujianResult = list.get(position);

        holder.textViewNamaMetode.setText(metodePengujianResult.getNamaMetode());
        holder.textViewDeskripsi.setText(metodePengujianResult.getDeskripsi());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(metodePengujianResult, holder);
            }
        });


    }

    public void setList(List<MetodePengujianResult> list, Context context){
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
        TextView textViewNamaMetode, textViewDeskripsi;

        Dialog dialogDetailMetodePengujian;

        ImageView dialogClose;
        TextInputEditText dialogEditTextJudul, dialogEditTextDeskripsi;

        Button dialogButtonDownload;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cv_item);
            textViewNamaMetode = itemView.findViewById(R.id.tv_item_1);
            textViewDeskripsi = itemView.findViewById(R.id.tv_item_2);

            dialogDetailMetodePengujian = new Dialog(context);
            dialogDetailMetodePengujian.setContentView(R.layout.dialog_detail_metode_pengujian);

            dialogClose = dialogDetailMetodePengujian.findViewById(R.id.iv_dismiss);

            dialogEditTextJudul = dialogDetailMetodePengujian.findViewById(R.id.et_nama_metode);
            dialogEditTextDeskripsi = dialogDetailMetodePengujian.findViewById(R.id.et_deskripsi);

            dialogButtonDownload = dialogDetailMetodePengujian.findViewById(R.id.btn_download);
        }
    }

    private void showPopUp(final MetodePengujianResult metodePengujianResult, final ListViewHolder holder){

        holder.dialogEditTextJudul.setText(metodePengujianResult.getNamaMetode());
        holder.dialogEditTextDeskripsi.setText(metodePengujianResult.getDeskripsi());

        holder.dialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.dialogDetailMetodePengujian.dismiss();
            }
        });

        holder.dialogButtonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(context, context.getString(R.string.izin_ditolak), Toast.LENGTH_SHORT).show();
                } else {
                    DownloadFileUtil.StartDownloading(metodePengujianResult.getFileBerkas(), context);
                }
            }
        });

        Objects.requireNonNull(holder.dialogDetailMetodePengujian.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        holder.dialogDetailMetodePengujian.getWindow().setGravity(Gravity.CENTER);
        holder.dialogDetailMetodePengujian.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.dialogDetailMetodePengujian.setCanceledOnTouchOutside(false);
        holder.dialogDetailMetodePengujian.getWindow().setWindowAnimations(R.style.DialogAnimation_up_down);
        holder.dialogDetailMetodePengujian.show();
    }
}
