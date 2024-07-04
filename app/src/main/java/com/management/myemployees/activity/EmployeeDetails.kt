package com.management.myemployees.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.management.myemployees.R
import com.management.myemployees.databinding.ActivityEmployeeDetailsBinding

class EmployeeDetails : BaseActivty() {
    private val employeesBinding by lazy { ActivityEmployeeDetailsBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(employeesBinding.root)

        employeesBinding.back.setOnClickListener {
            onBackPressed()
        }
        employeesBinding.backText.setOnClickListener {
            onBackPressed()
        }

        val intentValue = intent

        if (intentValue.hasExtra("selectedToken")) {

            Log.d("TAG", "onCreate: ${intentValue.extras?.getString("name").toString()}")
            employeesBinding.name.text = intentValue.extras?.getString("name").toString()
            employeesBinding.department.text = intentValue.extras?.getString("department").toString()
            employeesBinding.emilAdd.text = intentValue.extras?.getString("email").toString()
            employeesBinding.addressValue.text = intentValue.extras?.getString("address").toString()
            employeesBinding.dateOfBirthValue.text = intentValue.extras?.getString("dob").toString()
            employeesBinding.bloodGroup.text = intentValue.extras?.getString("blood").toString()
            employeesBinding.callAdd.text = intentValue.extras?.getString("number").toString()
//            intentValue.extras?.getString("image")
//                ?.let { employeesBinding.circleImage.setImageResource( it.toInt()) }

            Glide.with(this).load( intentValue.extras?.getString("image")).apply(RequestOptions.circleCropTransform()).into(employeesBinding.circleImage)
        }
    }
}