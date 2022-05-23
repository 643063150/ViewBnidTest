package com.android.zpp.viewbnidtest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.zpp.viewbnidtest.databinding.ListLayoutBinding
import com.android.zpp.viewbnidtest.databinding.ListYtemBinding
import java.util.*

/**
 *
 * @ProjectName: ViewBnidTest
 * @Package: com.android.zpp.viewbnidtest
 * @ClassName: Test
 * @Description:
 * @Author: zpp
 * @CreateDate: 2022/5/6 13:55
 * @UpdateUser:
 * @UpdateDate: 2022/5/6 13:55
 * @UpdateRemark:
 */
class Test(val mylist:ArrayList<String>) :RecyclerView.Adapter<Test.BindViewHolder>(){
    class BindViewHolder(var itemBinding: ListYtemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(itemBean: String) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindViewHolder {
        val itemBinding =
            ListYtemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BindViewHolder(itemBinding)


    }

    override fun onBindViewHolder(holder: BindViewHolder, position: Int) {
        var itemBean = mylist.get(position)
        holder.bind(itemBean)
    }

    override fun getItemCount(): Int {
        return mylist.size
    }


}