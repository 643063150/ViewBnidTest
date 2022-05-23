package com.android.zpp.viewbnidtest;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.zpp.viewbnidtest.databinding.ListLayoutBinding;

import java.util.ArrayList;

/**
 * @ProjectName: ViewBnidTest
 * @Package: com.android.zpp.viewbnidtest
 * @ClassName: ListTestActivity
 * @Description:
 * @Author: zpp
 * @CreateDate: 2022/5/6 10:53
 * @UpdateUser:
 * @UpdateDate: 2022/5/6 10:53
 * @UpdateRemark:
 */
public class ListTestActivity extends AppCompatActivity {
    ListLayoutBinding listLayoutBinding;
    MyAdapter myAdapter;
    Test test;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listLayoutBinding = ListLayoutBinding.inflate(getLayoutInflater());
        setContentView(listLayoutBinding.getRoot());
        setMyAdapter();
    }

    public void setMyAdapter() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            arrayList.add(i + "");
        }
        myAdapter=new MyAdapter(arrayList,ListTestActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listLayoutBinding.lstTest.setLayoutManager(layoutManager);
        listLayoutBinding.lstTest.setAdapter(myAdapter);
    }
}
