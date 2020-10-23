package com.bkipmlampung.appikceria.ui.penyakit;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bkipmlampung.appikceria.R;
import com.bkipmlampung.appikceria.adapter.PenyakitIkanAdapter;
import com.bkipmlampung.appikceria.model.PenyakitIkanResponse;
import com.bkipmlampung.appikceria.model.PenyakitIkanResult;
import com.bkipmlampung.appikceria.network.ApiService;
import com.bkipmlampung.appikceria.network.Client;
import com.bkipmlampung.appikceria.utils.SharedPreference;
import com.ferfalk.simplesearchview.SimpleSearchView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class PenyakitFragment extends Fragment implements Toolbar.OnMenuItemClickListener {

    RecyclerView recyclerView;
    ContentLoadingProgressBar progressBar;
    TextView textViewDataKosong;
    PenyakitIkanAdapter mAdapter;
    SimpleSearchView simpleSearchView;
    ArrayList<PenyakitIkanResult> penyakitIkanResults = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_penyakit, container, false);

        Toolbar toolbar = root.findViewById(R.id.topAppBar);
        toolbar.setOnMenuItemClickListener(this);

        recyclerView = root.findViewById(R.id.recycler_view);
        progressBar = root.findViewById(R.id.progres_bar);
        textViewDataKosong = root.findViewById(R.id.tv_data_kosong);

        simpleSearchView = root.findViewById(R.id.searchView);

        simpleSearchView.setOnQueryTextListener(new SimpleSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("SimpleSearchView", "Submit:" + query);
                filterData("");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("SimpleSearchView", "Text changed:" + newText);
                filterData(newText);
                return false;
            }

            @Override
            public boolean onQueryTextCleared() {
                Log.d("SimpleSearchView", "Text cleared");
                filterData("");
                return false;
            }
        });

        simpleSearchView.setOnSearchViewListener(new SimpleSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                Log.d("SimpleSearchView", "onSearchViewShown");
            }

            @Override
            public void onSearchViewClosed() {
                Log.d("SimpleSearchView", "onSearchViewClosed");
                filterData("");
            }

            @Override
            public void onSearchViewShownAnimation() {
                Log.d("SimpleSearchView", "onSearchViewShownAnimation");
            }

            @Override
            public void onSearchViewClosedAnimation() {
                Log.d("SimpleSearchView", "onSearchViewClosedAnimation");
            }
        });
        
        mAdapter = new PenyakitIkanAdapter();

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getData();

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.top_app_bar_search, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.search:
                Log.d("SimpleSearchView", "Current");
                simpleSearchView.showSearch();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void filterData(String string) {

        ArrayList<PenyakitIkanResult> results = new ArrayList<>();

        results.clear();

        if(string.isEmpty()){
            results.addAll(penyakitIkanResults);
        } else{
            for (PenyakitIkanResult metodePengujianResult : penyakitIkanResults){
                if(metodePengujianResult.getNamaPenyakit().toLowerCase().contains(string.toLowerCase()))
                    results.add(metodePengujianResult);
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

        Call<PenyakitIkanResponse> call = apiInterface.dataPenyakitIkan(
                SharedPreference.getIdUser(getActivity()),
                SharedPreference.getJwtUser(getActivity())
        );
        call.enqueue(new Callback<PenyakitIkanResponse>() {

            @Override
            public void onResponse(Call<PenyakitIkanResponse> call, retrofit2.Response<PenyakitIkanResponse> response) {

                Log.d("coment", "onResponse");

                if (response.isSuccessful()) {
                    Log.d("coment", "isSuccesful");
                    assert response.body() != null;
                    if (response.body().getStatus()) {

                        if (response.body().getData()!=null){
                            penyakitIkanResults.addAll(response.body().getData());

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
            public void onFailure(Call<PenyakitIkanResponse> call, Throwable t) {
                Log.d("coment", "onFailed");
                recyclerView.setVisibility(View.GONE);
                textViewDataKosong.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

                if (isAdded())
                    textViewDataKosong.setText(getString(R.string.server_error));
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Log.d("SimpleSearchView", "Current");
                simpleSearchView.showSearch();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}