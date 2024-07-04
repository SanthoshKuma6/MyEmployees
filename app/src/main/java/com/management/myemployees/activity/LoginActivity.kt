package com.management.myemployees.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.gson.JsonObject
import com.management.myemployees.R
import com.management.myemployees.activity.apiinterface.ApiResult
import com.management.myemployees.databinding.ActivityLoginBinding
import com.management.myemployees.model.LoginData
import com.management.myemployees.model.NewLoginData
import com.management.myemployees.savedPreferences.SavedSharedPreference
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginActivity : BaseActivty() {
    var email = ""
    var password = ""

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        data()
        binding.loginBtn.setOnClickListener {

//            startActivity(Intent(this,MainActivity::class.java))

            email = binding.emailValue.text.toString()
             password = binding.passwordValue.text.toString()
            Log.d("TAG", "onCreate: $email")
            Log.d("TAG", "onCreate: $password")

            loginCall()

        }
    }

    private fun loginCall(){
        val jsonObject = JsonObject().apply {
            addProperty("email",  email)
            addProperty("password",  password)
        }

//        viewModel.loginCall(jsonObject = jsonObject).observe(this,loginResponse)
        lifecycleScope.launch {
            viewModel.loginList(jsonObject)
        }
        viewModel.loginListdata.observe(this, loginResponse)
    }

   private val loginResponse = Observer<ApiResult<NewLoginData>>{

        when(it){
            is ApiResult.Loading -> {
                if (it.showLoader!!){

                }else{

                }
            }
//            is ApiResult.Success -> {
//
//                Log.d("TAG", "response: ${it.data}")
//
//                it.data.let {
//                    SavedSharedPreference.setUserData(
//                        applicationContext,
//                        it.name,
//                        it.token,
//                    )
//                }
//
//                Log.d("TAG", "SavedSharedPreference: ${SavedSharedPreference.getUserData(this).token}")
//                Log.d("TAG", "SavedSharedPreference: ${SavedSharedPreference.getUserData(this).name}")
//
//
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//            }

            is ApiResult.Success -> {
                Log.d("TAG", "response: ${it.data}")
                it.data.let {
                    SavedSharedPreference.setUserData(
                        applicationContext,
                        it?.data?.name,
                        it?.data?.token,
                    )
                }
//
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }
            is ApiResult.Error -> {
                errors("Error", it.errorMessage.toString())
            }

        }
   }

    private fun data(){
        if (SavedSharedPreference.getUserData(this).token?.isEmpty()!!){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}