package com.bkipmlampung.appikceria.ui.informasi.profil_balai;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bkipmlampung.appikceria.R;
import com.bkipmlampung.appikceria.model.AlamatResponse;
import com.bkipmlampung.appikceria.network.ApiService;
import com.bkipmlampung.appikceria.network.Client;
import com.bkipmlampung.appikceria.utils.SharedPreference;

import retrofit2.Call;
import retrofit2.Callback;

public class AlamatFragment extends Fragment implements View.OnClickListener {
    
    CardView cardView;
    TextView textViewAlamat, textViewTelepon, textViewEmail, textViewInstagram,
        textViewTwitter, textViewYoutube;
    ProgressBar progressBar;
    String ll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_alamat, container, false);
        
        cardView = root.findViewById(R.id.cv_item);
        textViewAlamat = root.findViewById(R.id.tv_alamat);
        textViewTelepon = root.findViewById(R.id.tv_nomor_telepon);
        textViewEmail = root.findViewById(R.id.tv_email);
        textViewInstagram = root.findViewById(R.id.tv_instagram);
        textViewTwitter = root.findViewById(R.id.tv_twitter);
        textViewYoutube = root.findViewById(R.id.tv_youtube);
        progressBar = root.findViewById(R.id.progres_bar);
        
        getData();

        textViewAlamat.setOnClickListener(this);
        textViewInstagram.setOnClickListener(this);
        textViewTwitter.setOnClickListener(this);
        textViewYoutube.setOnClickListener(this);
        
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_alamat:
                if (ll != null) {

                    Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=" + ll);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
                break;
            case R.id.tv_instagram:
                if (ll != null) {

                    Uri uri = Uri.parse("https://instagram.com/_u/" + textViewInstagram.getText().toString());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setPackage("com.instagram.android");

                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e){
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://instagram.com/" + textViewInstagram.getText().toString())));
                    }
                }
                break;
            case R.id.tv_twitter:
                if (ll != null) {

                    Uri uri = Uri.parse("twitter://user?screen_name=" + textViewTwitter.getText().toString());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setPackage("com.twitter.android");

                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e){
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://twitter.com/" + textViewTwitter.getText().toString())));
                    }
                }
                break;
            case R.id.tv_youtube:
                if (ll != null) {
                    Uri uri = Uri.parse("https://www.youtube.com/channel/UCikTzvfEPAVk7Bj1T09-65A");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setPackage("com.google.android.youtube");

                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e){
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://www.youtube.com/channel/UCikTzvfEPAVk7Bj1T09-65A")));
                    }
                }
                break;
        }
    }

    private void getData() {

        textViewAlamat.setVisibility(View.GONE);
        textViewTelepon.setVisibility(View.GONE);
        textViewEmail.setVisibility(View.GONE);
        textViewInstagram.setVisibility(View.GONE);
        textViewTwitter.setVisibility(View.GONE);
        textViewYoutube.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        ApiService apiInterface = Client.getClient().create(ApiService.class);


        Call<AlamatResponse> call = apiInterface.dataAlamat(
                SharedPreference.getIdUser(getActivity()),
                SharedPreference.getJwtUser(getActivity())
        );
        call.enqueue(new Callback<AlamatResponse>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<AlamatResponse> call, retrofit2.Response<AlamatResponse> response) {

                Log.d("coment", "onResponse");

                if (response.isSuccessful()) {
                    textViewAlamat.setVisibility(View.VISIBLE);
                    textViewTelepon.setVisibility(View.VISIBLE);
                    textViewEmail.setVisibility(View.VISIBLE);
                    textViewInstagram.setVisibility(View.VISIBLE);
                    textViewTwitter.setVisibility(View.VISIBLE);
                    textViewYoutube.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    Log.d("coment", "isSuccesful");
                    assert response.body() != null;
                    if (response.body().getStatus()){
                        if (isAdded()) {
                            textViewAlamat.setText(response.body().getData().get(0).getAlamat());
                            textViewTelepon.setText(response.body().getData().get(0).getNoHp());
                            ll = response.body().getData().get(0).getLongtitude() + ',' + response.body().getData().get(0).getLatitude();
                        }
                    } else {
                        textViewTelepon.setVisibility(View.GONE);
                        textViewEmail.setVisibility(View.GONE);
                        textViewInstagram.setVisibility(View.GONE);
                        if (isAdded()){
                            textViewAlamat.setText(getString(R.string.akses_ditolak));
                            textViewAlamat.setText(getString(R.string.akses_ditolak));
                            textViewAlamat.setText(getString(R.string.akses_ditolak));
                            textViewAlamat.setText(getString(R.string.akses_ditolak));
                        }
                    }

                } else {
                    Log.d("coment", "onUnResponse");
                    textViewAlamat.setVisibility(View.VISIBLE);
                    textViewTelepon.setVisibility(View.GONE);
                    textViewEmail.setVisibility(View.GONE);
                    textViewInstagram.setVisibility(View.GONE);
                    textViewTwitter.setVisibility(View.GONE);
                    textViewYoutube.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);

                    if (isAdded())
                        textViewAlamat.setText(getString(R.string.terjadi_kesalahan));

                }
            }

            @Override
            public void onFailure(Call<AlamatResponse> call, Throwable t) {
                Log.d("coment", "onFailed");
                textViewAlamat.setVisibility(View.VISIBLE);
                textViewTelepon.setVisibility(View.GONE);
                textViewEmail.setVisibility(View.GONE);
                textViewInstagram.setVisibility(View.GONE);
                textViewTwitter.setVisibility(View.GONE);
                textViewYoutube.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);

                if (isAdded())
                    textViewAlamat.setText(getString(R.string.server_error));
            }
        });
    }
}