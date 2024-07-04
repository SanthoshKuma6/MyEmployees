package com.management.myemployees.viewModel

import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.management.myemployees.repository.MainRepository

class MainViewModel (private val mainRepository: MainRepository) : ViewModel(){
    val employeeList = mainRepository.employeeList
    val loginListdata = mainRepository.loginList
    val departmentListdata = mainRepository.departmentList
    val addEmpListdata = mainRepository.addEmployeeList

    suspend fun employeeList(token: String){
        mainRepository.employeeList(token)
    }

    suspend fun loginList(jsonObject: JsonObject){
        mainRepository.loginCall(jsonObject)
    }

    suspend fun loadDepartment(token: String){
        mainRepository.loadDepartment(token)
    }

//    suspend fun addEmp(token: String, jsonObject: JsonObject){
//        mainRepository.addEmp(token,jsonObject)
//    }

    suspend fun addEmp(token: String, jsonObject: JsonObject){
        mainRepository.addEmp(token, jsonObject)
    }
}