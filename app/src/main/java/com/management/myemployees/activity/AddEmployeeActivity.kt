package com.management.myemployees.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.gson.JsonObject
import com.management.myemployees.activity.apiinterface.ApiResult
import com.management.myemployees.databinding.ActivityAddEmployeeBinding
import com.management.myemployees.fragment.MyEmployeeFragment
import com.management.myemployees.model.AddNewDataClass
import com.management.myemployees.model.DepartmentListData
import com.management.myemployees.savedPreferences.SavedSharedPreference
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

class AddEmployeeActivity : BaseActivty() {
    private val addBinding by lazy { ActivityAddEmployeeBinding.inflate(layoutInflater) }
    val departmentDetails: ArrayList<DepartmentListData.Data?> = ArrayList()
    val nameDetails: ArrayList<String?> = ArrayList()
    var bearerToken = ""
    var name = ""
    var email = ""
    var department = ""
    var number = ""
    var dob = ""
    var blood = ""
    var address = ""
    var departmentToken = ""
    var selectedValue = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(addBinding.root)

        bearerToken =  SavedSharedPreference.getUserData(this).token.toString()
        loadDepartment(bearerToken)

        addBinding.calender.setOnClickListener {
            startDatePicker()
        }


        addBinding.addButton.setOnClickListener {

                name = addBinding.employeeName.text.toString()
                email = addBinding.emailAddress.text.toString()
                department = addBinding.departmentText.text.toString()
                dob = addBinding.dateOfBirth.text.toString()
                blood = addBinding.bloodGroup.text.toString()
                number = addBinding.contactNumber.text.toString()
                address = addBinding.addressEdit.text.toString()

            for (i in departmentDetails){
                if (selectedValue == i?.name){
                    departmentToken = i.token.toString()
                }
            }
            addCall()



        }

    }


    private fun loadDepartment(token : String){
        lifecycleScope.launch {
            viewModel.loadDepartment(token)
        }
        viewModel.departmentListdata.observe(this,departmentList)
    }

    private fun addCall(){
        val jsonObject = JsonObject().apply {
            addProperty("name",  name)
            addProperty("email",  email)
            addProperty("department_token",  departmentToken)
            addProperty("date_of_birth",  dob)
            addProperty("mobile_number",  number)
            addProperty("address",  address)
            addProperty("blood_group",  blood)
        }

        Log.d("TAG", "addCall: $jsonObject")


        lifecycleScope.launch {
            viewModel.addEmp(bearerToken,jsonObject)
        }
        viewModel.addEmpListdata.observe(this,addResponse)

    }


        private fun startDatePicker()  {


            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(

                this, { _, year, monthOfYear, dayOfMonth ->

                    val dateType = "" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth
                    val parser = SimpleDateFormat("yyyy-MM-dd")
                    val formatter = SimpleDateFormat("yyyy-MM-dd")
                    val output = formatter.format(parser.parse(dateType))
                    addBinding.dateOfBirth.text = output

                }, year, month, day
            )
            datePickerDialog.show()



        }

    private val departmentList = Observer<ApiResult<DepartmentListData>>{
        when (it) {
            is ApiResult.Loading -> {

            }

            is ApiResult.Success -> {
                Log.d("TAG", "Department_response: ${it.data}")

                departmentDetails.clear()
                it.data?.data?.let { it1 -> departmentDetails.addAll(it1) }
                Log.d("TAG", "departmentDetails: $departmentDetails")
                nameDetails.clear()
                for (i in departmentDetails){
                    nameDetails.add(i?.name)
                }
                val arrayAdapter = ArrayAdapter(
                    this, androidx.transition.R.layout.support_simple_spinner_dropdown_item, nameDetails
                )
                addBinding.department.adapter = arrayAdapter
                spinner()

            }

            is ApiResult.Error -> {
                errors("Error", it.errorMessage.toString())
            }
        }
    }

    private fun spinner() {
        addBinding.department.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    selectedValue = parent?.getItemAtPosition(position).toString()
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }


    private val addResponse = Observer<ApiResult<AddNewDataClass>>{
        when (it) {
            is ApiResult.Loading -> {

            }

            is ApiResult.Success -> {
                Log.d("TAG", "Add_response: ${it.data}")

                startActivity(Intent(this,MainActivity::class.java))

            }

            is ApiResult.Error -> {
                errors("Error", it.errorMessage.toString())
            }
        }
    }
}