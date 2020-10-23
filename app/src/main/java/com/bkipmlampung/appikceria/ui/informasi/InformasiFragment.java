package com.bkipmlampung.appikceria.ui.informasi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bkipmlampung.appikceria.R;
import com.bkipmlampung.appikceria.ui.informasi.berita.BeritaActivity;

public class InformasiFragment extends Fragment implements View.OnClickListener {

    CardView cvProfilBKIPM, cvKegiatanOperasional, cvDataKepegawaian, cvBerita;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_informasi, container, false);

        cvProfilBKIPM = root.findViewById(R.id.cv_profil_bkipm);
        cvKegiatanOperasional = root.findViewById(R.id.cv_kegiatan_operational);
        cvDataKepegawaian = root.findViewById(R.id.cv_data_kepegawaian);
        cvBerita = root.findViewById(R.id.cv_berita);

        cvProfilBKIPM.setOnClickListener(this);
        cvKegiatanOperasional.setOnClickListener(this);
        cvDataKepegawaian.setOnClickListener(this);
        cvBerita.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cv_profil_bkipm:
                startActivity(new Intent(getActivity(), ProfilBKIPMActivity.class));
                break;
            case R.id.cv_kegiatan_operational:
                startActivity(new Intent(getActivity(), KegiatanOperasionalActivity.class));
                break;
            case R.id.cv_data_kepegawaian:
                startActivity(new Intent(getActivity(), DataKepegawaianActivity.class));
                break;
            case R.id.cv_berita:
                startActivity(new Intent(getActivity(), BeritaActivity.class));
                break;
        }
    }
}