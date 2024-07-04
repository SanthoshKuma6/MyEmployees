package com.management.myemployees.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.management.myemployees.R
import com.management.myemployees.activity.AddEmployeeActivity
import com.management.myemployees.activity.EmployeeDetails
import com.management.myemployees.activity.apiinterface.ApiResult
import com.management.myemployees.adapter.AdapterEmployees
import com.management.myemployees.databinding.FragmentMyEmployeeBinding
import com.management.myemployees.model.EmployeeListData
import com.management.myemployees.model.testing
import com.management.myemployees.savedPreferences.SavedSharedPreference
import kotlinx.coroutines.launch

class MyEmployeeFragment : BaseFragment() {

    private val fragmentBinding by lazy { FragmentMyEmployeeBinding.inflate(layoutInflater) }
    val empData: ArrayList<testing> = ArrayList()
    val employeeDetails: ArrayList<EmployeeListData.Data?> = ArrayList()
    val SelectedEmployeeDetails: ArrayList<EmployeeListData.Data?> = ArrayList()
    var token = ""
    var selectedToken = ""
    var name = ""
    var department = ""
    var email = ""
    var number = ""
    var address = ""
    var image = ""
    var dob = ""
    var blood = ""
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        token = SavedSharedPreference.getUserData(requireContext()).token.toString()

//
           token =  SavedSharedPreference.getUserData(requireContext()).token.toString()
            employeeApiCall()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        empData.add(testing("Winnie Rios", "Android",R.drawable.images))
//        empData.add(testing("Benjamin Perkins", "Android",R.drawable.images))
//        empData.add(testing("Scott Harmon", "Android",R.drawable.images))
//        empData.add(testing("John Elliott", "Android",R.drawable.images))
//        empData.add(testing("Winnie Rios", "Android",R.drawable.images))
//        empData.add(testing("Winnie Rios", "Android",R.drawable.images))
//        empData.add(testing("Winnie Rios", "Android",R.drawable.images))
//        empData.add(testing("Winnie Rios", "Android",R.drawable.images))

//        fragmentBinding.rvEmployeeItems.adapter = AdapterEmployees(requireContext(),empData)

        fragmentBinding.AddBtn.setOnClickListener {
            startActivity(Intent(requireContext(),AddEmployeeActivity::class.java))
        }


        return fragmentBinding.root
    }

    private fun employeeApiCall(){

        //viewModel.employeeListCall(token).observe(requireActivity(),employeeResponse)
        lifecycleScope.launch {
            viewModel.employeeList(token)
        }
        viewModel.employeeList.observe(requireActivity(), employeeResponse)
    }

    private val employeeResponse = Observer<ApiResult<EmployeeListData>> {
        when (it) {
            is ApiResult.Loading -> {
                it.showLoader!!
            }

            is ApiResult.Success -> {
                Log.d("TAG", "response: ${it.data}")
                employeeDetails.clear()
                it.data?.data?.let { it1 -> employeeDetails.addAll(it1) }


                fragmentBinding.rvEmployeeItems.adapter = AdapterEmployees(requireContext(),it.data?.data,::getItems)


            }

            is ApiResult.Error -> {
                errors("Error", it.errorMessage.toString())
            }
        }
    }

    private fun getItems(token : String){

        Log.d("TAG", "getItems: $token")
        for (i in  employeeDetails){
            if (token == i?.token){
                name = i.name.toString()
                department = i.departmentName.toString()
                email = i.email.toString()
                address = i.address.toString()
                dob = i.dateOfBirth.toString()
                blood = i.bloodGroup.toString()
                number = i.mobileNumber.toString()
                image = i.photo.toString()
                selectedToken = i.token.toString()

            }

        }
        val intent = Intent(requireContext(), EmployeeDetails::class.java)
        intent.putExtra("name",name)
        intent.putExtra("department",department)
        intent.putExtra("email",email)
        intent.putExtra("address",address)
        intent.putExtra("dob",dob)
        intent.putExtra("blood",blood)
        intent.putExtra("number",number)
        intent.putExtra("image",image)
        intent.putExtra("selectedToken",selectedToken)
        startActivity(intent)

        Log.d("TAG", "getItems:$SelectedEmployeeDetails")
    }


}