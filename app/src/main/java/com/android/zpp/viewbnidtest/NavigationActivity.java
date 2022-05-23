package com.android.zpp.viewbnidtest;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.android.zpp.viewbnidtest.databinding.NavigationLayoutBinding;

/**
 * @ProjectName: ViewBnidTest
 * @Package: com.android.zpp.viewbnidtest
 * @ClassName: NavigationActivity
 * @Description:
 * @Author: zpp
 * @CreateDate: 2022/5/7 10:15
 * @UpdateUser:
 * @UpdateDate: 2022/5/7 10:15
 * @UpdateRemark:
 */
public class NavigationActivity extends AppCompatActivity {
    NavigationLayoutBinding navigationLayoutBinding;
    MyPageAdapter myPageAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigationLayoutBinding=NavigationLayoutBinding.inflate(getLayoutInflater());
        setContentView(navigationLayoutBinding.getRoot());
        myPageAdapter=new MyPageAdapter(NavigationActivity.this);
        navigationLayoutBinding.viewpage.setAdapter(myPageAdapter);
        navigationLayoutBinding.designNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.one:
                    navigationLayoutBinding.viewpage.setCurrentItem(0);
                    break;
                case R.id.five:
                    navigationLayoutBinding.viewpage.setCurrentItem(1);
                    break;
                case R.id.two:
                    navigationLayoutBinding.viewpage.setCurrentItem(2);
                    break;
                case R.id.three:
                    navigationLayoutBinding.viewpage.setCurrentItem(3);
                    break;
                case R.id.four:
                    navigationLayoutBinding.viewpage.setCurrentItem(4);
                    break;
                default:
                    break;
            }
            return true;
        });
        navigationLayoutBinding.viewpage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                navigationLayoutBinding.designNavigationView.getMenu().getItem(position).setChecked(true);
            }
        });
        navigationLayoutBinding.viewpage.setCurrentItem(2,false);
    }

    public class MyPageAdapter extends FragmentStateAdapter{

        public MyPageAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:

                case 1:

                case 2:

                case 4:

                case 3:
                    return MyFragment.getInstance(position);
                default:
                    break;
            }
            return null;
        }

        @Override
        public int getItemCount() {
            return 5;
        }
    }
}
