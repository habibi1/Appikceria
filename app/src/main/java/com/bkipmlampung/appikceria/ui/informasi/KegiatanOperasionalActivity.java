package com.bkipmlampung.appikceria.ui.informasi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.bkipmlampung.appikceria.R;
import com.bkipmlampung.appikceria.ui.informasi.kegiatan_operasional.laboratorium.LaboratoriumActivity;
import com.bkipmlampung.appikceria.ui.informasi.kegiatan_operasional.lalu_lintas.LaluLintasActivity;

import java.util.Objects;

public class KegiatanOperasionalActivity extends AppCompatActivity implements View.OnClickListener {

    CardView cardViewLaluLintas, cardViewLaboratorium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kegiatan_operasional);

        Toolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cardViewLaluLintas = findViewById(R.id.cv_lalu_lintas);
        cardViewLaboratorium = findViewById(R.id.cv_laboratorium);

        cardViewLaluLintas.setOnClickListener(this);
        cardViewLaboratorium.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cv_lalu_lintas:
                startActivity(new Intent(KegiatanOperasionalActivity.this, LaluLintasActivity.class));
                break;
            case R.id.cv_laboratorium:
                startActivity(new Intent(KegiatanOperasionalActivity.this, LaboratoriumActivity.class));
                break;
        }
    }
}