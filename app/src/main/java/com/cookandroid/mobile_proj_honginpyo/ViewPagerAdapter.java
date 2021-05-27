package com.cookandroid.mobile_proj_honginpyo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    //프레그먼트 교체를 보여주는 처리 구현
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return Frag1.newinstance();
            case 1:
                return Frag2.newinstance();
            case 2:
                return Frag3.newinstance();
            case 3:
                return Frag4.newinstance();
            case 4:
                return Frag5.newinstance();
            case 5:
                return Frag6.newinstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 6;
    }

    //탭레이아웃 상단에 글씨
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "1";
            case 1:
                return "2";
            case 2:
                return "3";
            case 3:
                return "4";
            case 4:
                return "5";
            case 5:
                return "6";
            default:
                return null;
        }
    }
}
