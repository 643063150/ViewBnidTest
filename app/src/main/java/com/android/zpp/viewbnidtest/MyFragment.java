package com.android.zpp.viewbnidtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.zpp.viewbnidtest.databinding.MyFragmentViewBinding;

/**
 * @ProjectName: ViewBnidTest
 * @Package: com.android.zpp.viewbnidtest
 * @ClassName: MyFragment
 * @Description:
 * @Author: zpp
 * @CreateDate: 2022/5/7 10:47
 * @UpdateUser:
 * @UpdateDate: 2022/5/7 10:47
 * @UpdateRemark:
 */
public class MyFragment extends Fragment {
    MyFragmentViewBinding myFragmentViewBinding;
    static int position;

    public static MyFragment getInstance(int position) {
        MyFragment myFragment = new MyFragment();
        MyFragment.position = position;
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragmentViewBinding = MyFragmentViewBinding.inflate(inflater, container, false);
        myFragmentViewBinding.textView.setText(position + "");
        intView();
        return myFragmentViewBinding.getRoot();
    }

    private void intView(){
        myFragmentViewBinding.textView.setOnClickListener(v -> {
            Intent intent=new Intent();
            intent.setClass(getActivity(),CameraXTest.class);
            startActivity(intent);
        });
    }
}
