package com.bkipmlampung.appikceria.ui.informasi;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bkipmlampung.appikceria.R;
import com.bkipmlampung.appikceria.ui.informasi.data_kepegawaian.data_pegawai.DataPegawaiFragment;
import com.bkipmlampung.appikceria.ui.informasi.data_kepegawaian.kegiatan_kepegawaian.KegiatanKepegawaianFragment;
import com.bkipmlampung.appikceria.ui.informasi.data_kepegawaian.pegawai_teladan.PegawaiTeladanFragment;
import com.ferfalk.simplesearchview.SimpleSearchView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataKepegawaianActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    SimpleSearchView simpleSearchView;
    int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_kepegawaian);

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
        viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);
        setupViewPager(viewPager);

        simpleSearchView.setTabLayout((TabLayout) findViewById(R.id.tab_layout));

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        simpleSearchView.setOnQueryTextListener(new SimpleSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("SimpleSearchView", "Submit:" + query);
                setList("");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("SimpleSearchView", "Text changed:" + newText);
                setList(newText);
                return false;
            }

            @Override
            public boolean onQueryTextCleared() {
                Log.d("SimpleSearchView", "Text cleared");
                setList("");
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
                setList("");
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
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void setList(String newText) {
        if (number == 0){
            PegawaiTeladanFragment fragment = (PegawaiTeladanFragment) getSupportFragmentManager().getFragments().get(0);
            ((PegawaiTeladanFragment)fragment).filterData(newText);
        } else if (number == 1){
            DataPegawaiFragment fragment = (DataPegawaiFragment) getSupportFragmentManager().getFragments().get(1);
            ((DataPegawaiFragment)fragment).filterData(newText);
        } else if (number == 2){
            KegiatanKepegawaianFragment fragment = (KegiatanKepegawaianFragment) getSupportFragmentManager().getFragments().get(2);
            ((KegiatanKepegawaianFragment)fragment).filterData(newText);
        }
    }

    @Override
    public void onBackPressed() {
        if (simpleSearchView.onBackPressed()) {
            setList("");
            return;
        }

        super.onBackPressed();
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
                Log.d("SimpleSearchView", "Current:" + viewPager.getCurrentItem());
                number = viewPager.getCurrentItem();
                simpleSearchView.showSearch();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new PegawaiTeladanFragment(), getString(R.string.pegawai_teladan));
        adapter.addFrag(new DataPegawaiFragment(), getString(R.string.data_pegawai));
        adapter.addFrag(new KegiatanKepegawaianFragment(), getString(R.string.kegiatan_kepegawaian));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    static class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}