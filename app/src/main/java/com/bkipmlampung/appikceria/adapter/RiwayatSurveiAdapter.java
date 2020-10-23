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
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bkipmlampung.appikceria.R;
import com.bkipmlampung.appikceria.model.SurveiResult;
import com.bkipmlampung.appikceria.utils.TimeUtil;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RiwayatSurveiAdapter extends RecyclerView.Adapter<RiwayatSurveiAdapter.ListViewHolder>{

    Context context;
    private List<SurveiResult> list = new ArrayList<>();

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_riwayat_survei, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        final SurveiResult surveiResult = list.get(position);

        holder.textViewNama.setText(surveiResult.getNama());
        holder.textViewTanggal.setText(TimeUtil.getDateDDMMMMYYYY(surveiResult.getLog()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(surveiResult, holder);
            }
        });

    }

    public void setList(List<SurveiResult> list, Context context){
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
        TextView textViewNama, textViewTanggal;

        Dialog dialogDetailSurvei;

        ImageView dialogClose;

        TextInputEditText dialogEditTextNama, dialogEditTextUsia, dialogEditTextKritikSaran;

        AutoCompleteTextView dialogTextViewJenisKelamin, dialogTextViewPendidikan, dialogTextViewPekerjaan,
                dialogTextViewJenisLayanan, dialogTextViewSurvei1, dialogTextViewSurvei2, dialogTextViewSurvei3,
                dialogTextViewSurvei4, dialogTextViewSurvei5, dialogTextViewSurvei6, dialogTextViewSurvei7,
                dialogTextViewSurvei8, dialogTextViewSurvei9;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cv_item);

            textViewNama = itemView.findViewById(R.id.tv_item_1);
            textViewTanggal = itemView.findViewById(R.id.tv_item_2);

            dialogDetailSurvei = new Dialog(context);
            dialogDetailSurvei.setContentView(R.layout.dialog_detail_survei_kepuasan);

            dialogClose = dialogDetailSurvei.findViewById(R.id.iv_dismiss);

            dialogEditTextNama = dialogDetailSurvei.findViewById(R.id.et_nama);
            dialogEditTextUsia = dialogDetailSurvei.findViewById(R.id.et_usia);
            dialogEditTextKritikSaran = dialogDetailSurvei.findViewById(R.id.et_kritik_dan_saran);
            dialogTextViewJenisKelamin = dialogDetailSurvei.findViewById(R.id.act_jenis_kelamin);
            dialogTextViewPendidikan = dialogDetailSurvei.findViewById(R.id.act_pendidikan);
            dialogTextViewPekerjaan = dialogDetailSurvei.findViewById(R.id.act_pekerjaan);
            dialogTextViewJenisLayanan = dialogDetailSurvei.findViewById(R.id.act_jenis_layanan);

            dialogTextViewSurvei1 = dialogDetailSurvei.findViewById(R.id.act_survei_1);
            dialogTextViewSurvei2 = dialogDetailSurvei.findViewById(R.id.act_survei_2);
            dialogTextViewSurvei3 = dialogDetailSurvei.findViewById(R.id.act_survei_3);
            dialogTextViewSurvei4 = dialogDetailSurvei.findViewById(R.id.act_survei_4);
            dialogTextViewSurvei5 = dialogDetailSurvei.findViewById(R.id.act_survei_5);
            dialogTextViewSurvei6 = dialogDetailSurvei.findViewById(R.id.act_survei_6);
            dialogTextViewSurvei7 = dialogDetailSurvei.findViewById(R.id.act_survei_7);
            dialogTextViewSurvei8 = dialogDetailSurvei.findViewById(R.id.act_survei_8);
            dialogTextViewSurvei9 = dialogDetailSurvei.findViewById(R.id.act_survei_9);
        }
    }

    @SuppressLint("SetTextI18n")
    private void showPopUp(SurveiResult surveiResult, final ListViewHolder holder){

        holder.dialogEditTextNama.setText(surveiResult.getNama());
        holder.dialogEditTextUsia.setText(surveiResult.getUsia());

        holder.dialogTextViewJenisKelamin.setText(surveiResult.getJenisKelamin());
        holder.dialogTextViewPendidikan.setText(surveiResult.getPendidikan());
        holder.dialogTextViewPekerjaan.setText(surveiResult.getPekerjaan());
        holder.dialogTextViewJenisLayanan.setText(surveiResult.getJenisLayanan());
        holder.dialogEditTextKritikSaran.setText(surveiResult.getKiritikSaran());

        holder.dialogTextViewSurvei1.setText(surveiResult.getNilai1() + ". " + surveiResult.getPenilaian1());
        holder.dialogTextViewSurvei2.setText(surveiResult.getNilai2() + ". " + surveiResult.getPenilaian2());
        holder.dialogTextViewSurvei3.setText(surveiResult.getNilai3() + ". " + surveiResult.getPenilaian3());
        holder.dialogTextViewSurvei4.setText(surveiResult.getNilai4() + ". " + surveiResult.getPenilaian4());
        holder.dialogTextViewSurvei5.setText(surveiResult.getNilai5() + ". " + surveiResult.getPenilaian5());
        holder.dialogTextViewSurvei6.setText(surveiResult.getNilai6() + ". " + surveiResult.getPenilaian6());
        holder.dialogTextViewSurvei7.setText(surveiResult.getNilai7() + ". " + surveiResult.getPenilaian7());
        holder.dialogTextViewSurvei8.setText(surveiResult.getNilai8() + ". " + surveiResult.getPenilaian8());
        holder.dialogTextViewSurvei9.setText(surveiResult.getNilai9() + ". " + surveiResult.getPenilaian9());

        holder.dialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.dialogDetailSurvei.dismiss();
            }
        });

        Objects.requireNonNull(holder.dialogDetailSurvei.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        holder.dialogDetailSurvei.getWindow().setGravity(Gravity.CENTER);
        holder.dialogDetailSurvei.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.dialogDetailSurvei.setCanceledOnTouchOutside(false);
        holder.dialogDetailSurvei.getWindow().setWindowAnimations(R.style.DialogAnimation_up_down);
        holder.dialogDetailSurvei.show();
    }
}
