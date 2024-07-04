package com.management.myemployees.activity.apiinterface

import com.google.gson.JsonObject
import com.management.myemployees.model.AddNewDataClass
import com.management.myemployees.model.DepartmentListData
import com.management.myemployees.model.EmployeeListData
import com.management.myemployees.model.NewLoginData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {

    @POST("login")
    suspend fun loginCall(@Body jsonObject: JsonObject): retrofit2.Response<NewLoginData>

    @GET("employee")
    suspend fun employeesListCall(@Header("Authorization") token: String): retrofit2.Response<EmployeeListData>


    @GET("department")
    suspend fun loadDepartment(@Header("Authorization") token: String): retrofit2.Response<DepartmentListData>

    @POST("employee")
    suspend fun addEmp(@Header("Authorization") token: String,@Body params: JsonObject): retrofit2.Response<AddNewDataClass>


}