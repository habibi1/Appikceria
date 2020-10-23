package com.bkipmlampung.appikceria.ui.informasi.kegiatan_operasional.lalu_lintas;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bkipmlampung.appikceria.R;
import com.bkipmlampung.appikceria.adapter.LaluLintasAdapter;
import com.bkipmlampung.appikceria.model.LaluLintasResponse;
import com.bkipmlampung.appikceria.model.LaluLintasResult;
import com.bkipmlampung.appikceria.network.ApiService;
import com.bkipmlampung.appikceria.network.Client;
import com.bkipmlampung.appikceria.utils.SharedPreference;
import com.bkipmlampung.appikceria.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class LaluLintasDomestikFragment extends Fragment {

    RecyclerView recyclerView;
    ContentLoadingProgressBar progressBar;
    TextView textViewDataKosong;
    private LaluLintasAdapter mAdapter;
    ArrayList<String> listTahun;
    List<LaluLintasResult> listData;
    LinearLayout viewFilter;

    String[] BULAN;

    AutoCompleteTextView actBulan, actTahun;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_lalu_lintas_domestik, container, false);

        recyclerView = root.findViewById(R.id.recycler_view);
        progressBar = root.findViewById(R.id.progres_bar);
        textViewDataKosong = root.findViewById(R.id.tv_data_kosong);
        actBulan = root.findViewById(R.id.act_bulan);
        actTahun = root.findViewById(R.id.act_tahun);
        viewFilter = root.findViewById(R.id.view_filter);

        BULAN = StringUtils.getArrayFromMenu(getActivity(), R.menu.bulan_menu);

        mAdapter = new LaluLintasAdapter();
        listTahun = new ArrayList<String>();

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getData();

        return root;
    }

    private void filterData() {

        checkChoosed();

        ArrayList<LaluLintasResult> results = new ArrayList<>();
        String tahun = actTahun.getText().toString();
        String bulan = actBulan.getText().toString();

        if (isAdded()){
            if(tahun.equals(getString(R.string.semua)) && bulan.equals(getString(R.string.semua))){
                results.addAll(listData);
            } else if(tahun.equals(getString(R.string.semua)) && !bulan.equals(getString(R.string.semua))){
                for (LaluLintasResult laluLintasResult : listData){
                    if(laluLintasResult.getBulan().equals(bulan))
                        results.add(laluLintasResult);
                }
            } else if(!tahun.equals(getString(R.string.semua)) && bulan.equals(getString(R.string.semua))){
                for (LaluLintasResult laluLintasResult : listData){
                    if(laluLintasResult.getTahun().equals(tahun))
                        results.add(laluLintasResult);
                }
            } else {
                for (LaluLintasResult laluLintasResult : listData){
                    if(laluLintasResult.getTahun().equals(tahun) && laluLintasResult.getBulan().equals(bulan))
                        results.add(laluLintasResult);
                }
            }
        }

        if (results.isEmpty()){
            if (isAdded())
                textViewDataKosong.setText(getString(R.string.data_kosong));

            recyclerView.setVisibility(View.GONE);
            textViewDataKosong.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            viewFilter.setVisibility(View.VISIBLE);
        } else {

            mAdapter.setList(results, getActivity());
            mAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(mAdapter);

            recyclerView.setVisibility(View.VISIBLE);
            textViewDataKosong.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            viewFilter.setVisibility(View.VISIBLE);
        }
    }

    private void checkChoosed() {
        actTahun.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterData();
            }
        });

        actBulan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterData();
            }
        });
    }

    private void getData() {

        recyclerView.setVisibility(View.GONE);
        textViewDataKosong.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        viewFilter.setVisibility(View.GONE);

        ApiService apiInterface = Client.getClient().create(ApiService.class);

        Call<LaluLintasResponse> call = apiInterface.dataDomestik(
                SharedPreference.getIdUser(getActivity()),
                SharedPreference.getJwtUser(getActivity())
        );
        call.enqueue(new Callback<LaluLintasResponse>() {

            @Override
            public void onResponse(Call<LaluLintasResponse> call, retrofit2.Response<LaluLintasResponse> response) {

                Log.d("coment", "onResponse");

                if (response.isSuccessful()) {
                    Log.d("coment", "isSuccesful");
                    assert response.body() != null;
                    if (response.body().getStatus()) {

                        if (response.body().getData()!=null){

                            if (isAdded()){
                                listTahun.add(getString(R.string.semua));
                            }

                            for(LaluLintasResult laluLintasResult : response.body().getData()){
                                if(!listTahun.contains(laluLintasResult.getTahun()))
                                    listTahun.add(laluLintasResult.getTahun());
                            }

                            ArrayAdapter<String> adapterTahun =
                                    new ArrayAdapter<>(
                                            requireActivity(),
                                            R.layout.dropdown_menu_popup_item,
                                            listTahun);

                            ArrayAdapter<String> adapterBulan =
                                    new ArrayAdapter<>(
                                            requireActivity(),
                                            R.layout.dropdown_menu_popup_item,
                                            BULAN);

                            listData = response.body().getData();

                            actTahun.setText(adapterTahun.getItem(0), false);
                            actTahun.setAdapter(adapterTahun);

                            actBulan.setText(adapterBulan.getItem(0), false);
                            actBulan.setAdapter(adapterBulan);

                            filterData();
                        } else {
                            recyclerView.setVisibility(View.GONE);
                            textViewDataKosong.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            viewFilter.setVisibility(View.GONE);

                            if (isAdded())
                                textViewDataKosong.setText(getString(R.string.data_kosong));
                        }
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        textViewDataKosong.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        viewFilter.setVisibility(View.GONE);

                        if (isAdded())
                            textViewDataKosong.setText(getString(R.string.akses_ditolak));
                    }

                } else {
                    Log.d("coment", "onUnResponse");
                    recyclerView.setVisibility(View.GONE);
                    textViewDataKosong.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    viewFilter.setVisibility(View.GONE);

                    if (isAdded())
                        textViewDataKosong.setText(getString(R.string.terjadi_kesalahan));

                }
            }

            @Override
            public void onFailure(Call<LaluLintasResponse> call, Throwable t) {
                Log.d("coment", "onFailed");
                recyclerView.setVisibility(View.GONE);
                textViewDataKosong.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                viewFilter.setVisibility(View.GONE);

                if (isAdded())
                    textViewDataKosong.setText(getString(R.string.server_error));
            }
        });
    }
}