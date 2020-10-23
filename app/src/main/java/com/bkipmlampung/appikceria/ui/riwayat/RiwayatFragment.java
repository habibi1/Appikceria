package com.bkipmlampung.appikceria.ui.riwayat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bkipmlampung.appikceria.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class RiwayatFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_riwayat, container, false);

        viewPager = root.findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(4);
        setupViewPager(viewPager);

        tabLayout = root.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        return root;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new RiwayatKeluhanPenyakitFragment(), getString(R.string.keluhan_penyakit));
        adapter.addFrag(new RiwayatSurveiKepuasanFragment(), getString(R.string.survei_kepuasan));
        adapter.addFrag(new RiwayatPengaduanFragment(), getString(R.string.pengaduan_layanan));
        adapter.addFrag(new RiwayatPengajuanPKLFragment(), getString(R.string.pengajuan_pkl));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
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