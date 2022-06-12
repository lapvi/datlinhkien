package Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import Fragment.TrangChuFragment;
import Fragment.LichSuFragment;
import Fragment.TrangCaNhanFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new TrangChuFragment();
            case 1:
                return new LichSuFragment();
            case 2:
                return new TrangCaNhanFragment();
            default:
                return new TrangChuFragment();

        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Trang chu";
                break;
            case 1:
                title = "Lịch Sử";
                break;
            case 2:
                title = "Trang cá nhân";
                break;
        }
        return title;
    }
}
