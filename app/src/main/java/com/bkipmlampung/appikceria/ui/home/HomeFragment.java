package com.bkipmlampung.appikceria.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bkipmlampung.appikceria.R;
import com.bkipmlampung.appikceria.ui.home.keluhan_penyakit.KeluhanPenyakitActivity;
import com.bkipmlampung.appikceria.ui.home.pengaduan.PengaduanActivity;
import com.bkipmlampung.appikceria.ui.home.pengajuan_pkl.PengajuanPKLActivity;
import com.bkipmlampung.appikceria.ui.home.survei_kepuasan.SurveiKepuasanActivity;

public class HomeFragment extends Fragment implements View.OnClickListener {

    CardView cvKeluhanPenyakit, cvSurveiKepuasan, cvPengaduan, cvPengajuanPKL;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        cvKeluhanPenyakit = root.findViewById(R.id.cv_keluhan_penyakit);
        cvSurveiKepuasan = root.findViewById(R.id.cv_survei_kepuasan);
        cvPengaduan = root.findViewById(R.id.cv_pengaduan);
        cvPengajuanPKL = root.findViewById(R.id.cv_pengajuan_pkl);

        cvKeluhanPenyakit.setOnClickListener(this);
        cvSurveiKepuasan.setOnClickListener(this);
        cvPengaduan.setOnClickListener(this);
        cvPengajuanPKL.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cv_keluhan_penyakit:
                startActivity(new Intent(getActivity(), KeluhanPenyakitActivity.class));
                break;
            case R.id.cv_survei_kepuasan:
                startActivity(new Intent(getActivity(), SurveiKepuasanActivity.class));
                break;
            case R.id.cv_pengaduan:
                startActivity(new Intent(getActivity(), PengaduanActivity.class));
                break;
            case R.id.cv_pengajuan_pkl:
                startActivity(new Intent(getActivity(), PengajuanPKLActivity.class));
                break;
        }
    }
}