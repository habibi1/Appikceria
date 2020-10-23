package com.bkipmlampung.appikceria.ui.informasi.berita;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bkipmlampung.appikceria.R;
import com.bkipmlampung.appikceria.adapter.BeritaAdapter;
import com.bkipmlampung.appikceria.model.BeritaResponse;
import com.bkipmlampung.appikceria.model.BeritaResult;
import com.bkipmlampung.appikceria.network.ApiService;
import com.bkipmlampung.appikceria.network.Client;
import com.bkipmlampung.appikceria.utils.SharedPreference;
import com.ferfalk.simplesearchview.SimpleSearchView;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class BeritaActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ContentLoadingProgressBar progressBar;
    TextView textViewDataKosong;
    BeritaAdapter mAdapter;
    ArrayList<BeritaResult> beritaResults = new ArrayList<>();
    SimpleSearchView simpleSearchView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita);

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

        simpleSearchView = findViewById(R.id.searchView);

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

        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progres_bar);
        textViewDataKosong = findViewById(R.id.tv_data_kosong);

        mAdapter = new  BeritaAdapter();

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(BeritaActivity.this));

        getData();

    }

    public void filterData(String string) {

        ArrayList<BeritaResult> results = new ArrayList<>();

        results.clear();

        if(string.isEmpty()){
            results.addAll(beritaResults);
        } else{
            for (BeritaResult beritaResult : beritaResults){
                if(beritaResult.getSubjek().toLowerCase().contains(string.toLowerCase()))
                    results.add(beritaResult);
            }
        }

        if (results.isEmpty()){
            textViewDataKosong.setText(getString(R.string.data_kosong));

            recyclerView.setVisibility(View.GONE);
            textViewDataKosong.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else {

            mAdapter.setList(results, BeritaActivity.this);
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

        Call<BeritaResponse> call = apiInterface.dataBerita(
                SharedPreference.getIdUser(BeritaActivity.this),
                SharedPreference.getJwtUser(BeritaActivity.this)
        );
        call.enqueue(new Callback<BeritaResponse>() {

            @Override
            public void onResponse(Call<BeritaResponse> call, retrofit2.Response<BeritaResponse> response) {

                Log.d("coment", "onResponse");

                if (response.isSuccessful()) {
                    Log.d("coment", "isSuccesful");
                    assert response.body() != null;
                    if (response.body().getStatus()) {

                        if (response.body().getData()!=null){
                            beritaResults.clear();
                            beritaResults.addAll(response.body().getData());

                            filterData("");
                        } else {
                            recyclerView.setVisibility(View.GONE);
                            textViewDataKosong.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);

                            textViewDataKosong.setText(getString(R.string.data_kosong));
                        }

                    } else {
                        recyclerView.setVisibility(View.GONE);
                        textViewDataKosong.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);

                        textViewDataKosong.setText(getString(R.string.akses_ditolak));
                    }

                } else {
                    Log.d("coment", "onUnResponse");
                    recyclerView.setVisibility(View.GONE);
                    textViewDataKosong.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    textViewDataKosong.setText(getString(R.string.terjadi_kesalahan));

                }
            }

            @Override
            public void onFailure(Call<BeritaResponse> call, Throwable t) {
                Log.d("coment", "onFailed");
                recyclerView.setVisibility(View.GONE);
                textViewDataKosong.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

                textViewDataKosong.setText(getString(R.string.server_error));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.search:
                simpleSearchView.showSearch();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}