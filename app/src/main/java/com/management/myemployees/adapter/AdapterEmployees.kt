package com.management.myemployees.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.management.myemployees.databinding.EmployeeAdapterBinding
import com.management.myemployees.model.EmployeeListData

class AdapterEmployees(var context: Context, var data: List<EmployeeListData.Data?>?, private val getItem: (String) -> Unit):RecyclerView.Adapter<AdapterEmployees.MyViewHolder>() {

    inner class MyViewHolder(private val employeesItemBinding: EmployeeAdapterBinding): RecyclerView.ViewHolder(employeesItemBinding.root){
        fun bindItem(task : EmployeeListData.Data){
//            task.photo?.let { employeesItemBinding.imageView.setImageResource(it) }
//            employeesItemBinding.name.text = task.name
//            employeesItemBinding.department.text = task.department
            Glide.with(itemView.context).load(task.photo).apply(RequestOptions.circleCropTransform()).into(employeesItemBinding.imageView)
//            Glide.with(context).load(task.photo).apply(RequestOptions.circleCropTransform()).into(adapterEmployeeListBinding.imageView)
//            task.photo?.let { employeesItemBinding.imageView.setImageResource(it.toInt()) }
            employeesItemBinding.name.text = task.name
            employeesItemBinding.department.text = task.departmentName

            employeesItemBinding.cardView.setOnClickListener {
                task.token?.let { it1 -> getItem(it1) }
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val employeeListAdapter = EmployeeAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(employeeListAdapter)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        data?.get(position)?.let { holder.bindItem(it) }
    }
}