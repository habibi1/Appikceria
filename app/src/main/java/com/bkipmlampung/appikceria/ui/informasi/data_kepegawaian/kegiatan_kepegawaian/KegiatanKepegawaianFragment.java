package com.bkipmlampung.appikceria.ui.informasi.data_kepegawaian.kegiatan_kepegawaian;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bkipmlampung.appikceria.R;
import com.bkipmlampung.appikceria.adapter.KegiatanKepegawaianAdapter;
import com.bkipmlampung.appikceria.model.DataPegawaiResponse;
import com.bkipmlampung.appikceria.model.KegiatanKepegawaianResponse;
import com.bkipmlampung.appikceria.model.KegiatanKepegawaianResult;
import com.bkipmlampung.appikceria.network.ApiService;
import com.bkipmlampung.appikceria.network.Client;
import com.bkipmlampung.appikceria.utils.SharedPreference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class KegiatanKepegawaianFragment extends Fragment {

    RecyclerView recyclerView;
    ContentLoadingProgressBar progressBar;
    TextView textViewDataKosong;
    KegiatanKepegawaianAdapter mAdapter;
    ArrayList<KegiatanKepegawaianResult> kegiatanKepegawaianResults = new ArrayList<>();
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_kegiatan_kepegawaian, container, false);

        recyclerView = root.findViewById(R.id.recycler_view);
        progressBar = root.findViewById(R.id.progres_bar);
        textViewDataKosong = root.findViewById(R.id.tv_data_kosong);

        mAdapter = new KegiatanKepegawaianAdapter();

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getData();
        
        return root;
    }

    public void filterData(String string) {

        ArrayList<KegiatanKepegawaianResult> results = new ArrayList<>();

        results.clear();

        if(string.isEmpty()){
            results.addAll(kegiatanKepegawaianResults);
        } else{
            for (KegiatanKepegawaianResult kegiatanKepegawaianResult : kegiatanKepegawaianResults){
                if(kegiatanKepegawaianResult.getSubjek().toLowerCase().contains(string.toLowerCase()))
                    results.add(kegiatanKepegawaianResult);
            }
        }

        if (results.isEmpty()){
            if (isAdded())
                textViewDataKosong.setText(getString(R.string.data_kosong));

            recyclerView.setVisibility(View.GONE);
            textViewDataKosong.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else {

            mAdapter.setList(results, getActivity());
            mAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(mAdapter);

            recyclerView.setVisibility(View.VISIBLE);
            textViewDataKosong.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void getData() {

        recyclerView.setVisibility(View.GONE);
        textViewDataKosong.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        ApiService apiInterface = Client.getClient().create(ApiService.class);

        Call<KegiatanKepegawaianResponse> call = apiInterface.dataKegiatanKepegawaian(
                SharedPreference.getIdUser(getActivity()),
                SharedPreference.getJwtUser(getActivity())
        );
        call.enqueue(new Callback<KegiatanKepegawaianResponse>() {

            @Override
            public void onResponse(Call<KegiatanKepegawaianResponse> call, retrofit2.Response<KegiatanKepegawaianResponse> response) {

                Log.d("coment", "onResponse");

                if (response.isSuccessful()) {
                    Log.d("coment", "isSuccesful");
                    assert response.body() != null;
                    if (response.body().getStatus()) {

                        if (response.body().getData()!=null){
                            kegiatanKepegawaianResults.clear();
                            kegiatanKepegawaianResults.addAll(response.body().getData());

                            filterData("");
                        } else {
                            recyclerView.setVisibility(View.GONE);
                            textViewDataKosong.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);

                            if (isAdded())
                                textViewDataKosong.setText(getString(R.string.data_kosong));
                        }

                    } else {
                        recyclerView.setVisibility(View.GONE);
                        textViewDataKosong.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);

                        if (isAdded())
                            textViewDataKosong.setText(getString(R.string.akses_ditolak));
                    }

                } else {
                    Log.d("coment", "onUnResponse");
                    recyclerView.setVisibility(View.GONE);
                    textViewDataKosong.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    if (isAdded())
                        textViewDataKosong.setText(getString(R.string.terjadi_kesalahan));

                }
            }

            @Override
            public void onFailure(Call<KegiatanKepegawaianResponse> call, Throwable t) {
                Log.d("coment", "onFailed");
                recyclerView.setVisibility(View.GONE);
                textViewDataKosong.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

                if (isAdded())
                    textViewDataKosong.setText(getString(R.string.server_error));
            }
        });
    }
}