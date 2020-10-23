package com.bkipmlampung.appikceria.ui.informasi.kegiatan_operasional.laboratorium;

import android.os.Bundle;
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
import com.ferfalk.simplesearchview.SimpleSearchView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LaboratoriumActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    SimpleSearchView simpleSearchView;
    int number;
    Fragment fragmentBerita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratorium);

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
        viewPager.setOffscreenPageLimit(1);
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

    private void setList(String newText) {
        if (number == 0){
            MetodePengujianFragment fragment = (MetodePengujianFragment) getSupportFragmentManager().getFragments().get(0);
            ((MetodePengujianFragment)fragment).filterData(newText);
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
        adapter.addFrag(new MetodePengujianFragment(), getString(R.string.metode_pengujian));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public static class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

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