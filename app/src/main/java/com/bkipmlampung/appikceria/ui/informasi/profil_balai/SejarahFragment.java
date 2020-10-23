package com.bkipmlampung.appikceria.ui.informasi.profil_balai;

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
import com.bkipmlampung.appikceria.adapter.SejarahAdapter;
import com.bkipmlampung.appikceria.model.SejarahResponse;
import com.bkipmlampung.appikceria.network.ApiService;
import com.bkipmlampung.appikceria.network.Client;
import com.bkipmlampung.appikceria.utils.SharedPreference;

import retrofit2.Call;
import retrofit2.Callback;

public class SejarahFragment extends Fragment {

    RecyclerView recyclerView;
    ContentLoadingProgressBar progressBar;
    TextView textViewDataKosong;
    SejarahAdapter mAdapter;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sejarah, container, false);

        recyclerView = root.findViewById(R.id.recycler_view);
        progressBar = root.findViewById(R.id.progres_bar);
        textViewDataKosong = root.findViewById(R.id.tv_data_kosong);

        mAdapter = new SejarahAdapter();

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getData();
        
        return root;
    }

    private void getData() {

        recyclerView.setVisibility(View.GONE);
        textViewDataKosong.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        ApiService apiInterface = Client.getClient().create(ApiService.class);

        Call<SejarahResponse> call = apiInterface.dataSejarah(
                SharedPreference.getIdUser(getActivity()),
                SharedPreference.getJwtUser(getActivity())
        );
        call.enqueue(new Callback<SejarahResponse>() {

            @Override
            public void onResponse(Call<SejarahResponse> call, retrofit2.Response<SejarahResponse> response) {

                Log.d("coment", "onResponse");

                if (response.isSuccessful()) {
                    Log.d("coment", "isSuccesful");
                    assert response.body() != null;
                    if (response.body().getStatus()) {

                        if (response.body().getData()!=null){
                            mAdapter.setList(response.body().getData(), getActivity());
                            mAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(mAdapter);

                            recyclerView.setVisibility(View.VISIBLE);
                            textViewDataKosong.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
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
            public void onFailure(Call<SejarahResponse> call, Throwable t) {
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