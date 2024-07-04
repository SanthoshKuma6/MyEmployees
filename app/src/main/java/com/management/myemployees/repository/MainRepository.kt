package com.management.myemployees.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.management.myemployees.activity.apiinterface.ApiInterface
import com.management.myemployees.activity.apiinterface.ApiResult
import com.management.myemployees.model.AddNewDataClass
import com.management.myemployees.model.DepartmentListData
import com.management.myemployees.model.EmployeeListData
import com.management.myemployees.model.NewLoginData

class MainRepository (private val apis: ApiInterface) {
    val employeeList = MutableLiveData<ApiResult<EmployeeListData>>()
    val departmentList = MutableLiveData<ApiResult<DepartmentListData>>()
    val loginList = MutableLiveData<ApiResult<NewLoginData>>()
//    val addEmpList = MutableLiveData<ApiResult<AddNewDataClass>>()
    val addEmployeeList = MutableLiveData<ApiResult<AddNewDataClass>>()

    /*suspend fun loginCall(jsonObject: JsonObject) = apis.loginCall(jsonObject)

    suspend fun employeeListCall(token: String) = apis.employeesListCall("Bearer $token")*/

    suspend fun employeeList(token: String){
        employeeList.value = ApiResult.Loading(true)
        try {
            val data = apis.employeesListCall("Bearer $token")
            if (data.isSuccessful){
                employeeList.value = ApiResult.Loading(false)
                employeeList.value = ApiResult.Success(data.body())
            }else{
                employeeList.value = ApiResult.Loading(false)
                employeeList.value = ApiResult.Error(data.message())
            }
        }catch (exception: Exception){
            Log.d("Error", exception.toString())
        }
    }

    suspend fun loginCall(jsonObject: JsonObject){
        loginList.value = ApiResult.Loading(true)
        try {
            val data = apis.loginCall(jsonObject)
            if (data.isSuccessful){
                loginList.value = ApiResult.Loading(false)
                loginList.value = ApiResult.Success(data.body())
            }else{
                loginList.value = ApiResult.Loading(false)
                loginList.value = ApiResult.Error(data.message())
            }
        }catch (exception: Exception){
            Log.d("Error", exception.toString())
        }
    }


    suspend fun loadDepartment(token: String){
        employeeList.value = ApiResult.Loading(true)
        try {
            val data = apis.loadDepartment("Bearer $token")
            if (data.isSuccessful){
                departmentList.value = ApiResult.Loading(false)
                departmentList.value = ApiResult.Success(data.body())
            }else{
                departmentList.value = ApiResult.Loading(false)
                departmentList.value = ApiResult.Error(data.message())
            }
        }catch (exception: Exception){
            Log.d("Error", exception.toString())
        }
    }

    suspend fun addEmp(token: String, jsonObject: JsonObject){
        employeeList.value = ApiResult.Loading(true)
        try {
            val data = apis.addEmp("Bearer $token",jsonObject)
            if (data.isSuccessful){
                addEmployeeList.value = ApiResult.Loading(false)
                addEmployeeList.value = ApiResult.Success(data.body())
            }else{
                addEmployeeList.value = ApiResult.Loading(false)
                addEmployeeList.value = ApiResult.Error(data.message())
            }
        }catch (exception: Exception){
            Log.d("Error", exception.toString())
        }
    }

}