package com.management.myemployees.activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.management.myemployees.R
import com.management.myemployees.activity.apiinterface.ApiClient
import com.management.myemployees.internet.NetworkHelper
import com.management.myemployees.repository.MainRepository
import com.management.myemployees.viewModel.MainViewModel
import com.management.myemployees.viewModel.ViewModelFactory

abstract class BaseActivty : AppCompatActivity() {
    private lateinit var networkHelper: NetworkHelper
    var dialog: AlertDialog? = null
    var isRetry = MutableLiveData<Boolean>()
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelFactory(MainRepository(ApiClient.apis)))[MainViewModel::class.java]



    }


    fun AppCompatActivity.launchActivity(
        javaClass: Class<out AppCompatActivity>,
        bundle: Bundle? = null,
        isClearPreviousTask: Boolean = false
    ) {
        Intent(this, javaClass).apply {
            if (bundle != null)
                putExtras(bundle)

            if (isClearPreviousTask)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(this)
        }
    }

    fun isNetworkConnected(context: Context) : Boolean {
        networkHelper = NetworkHelper(context)
        return networkHelper.isNetworkConnected()
    }

    open val loader = Observer<Boolean> {
        if (it) {
            val view = layoutInflater.inflate(R.layout.loader, null)
//            val customLayout = layoutInflater.inflate(R.layout.loader, null)
            dialog = AlertDialog.Builder(this).setView(view).setCancelable(false).show()
            dialog!!.window!!.setLayout(260, ViewGroup.LayoutParams.WRAP_CONTENT)
//            Glide.with(this).load(R.raw.load).into(view.findViewById(R.id.ProgressBar))

        } else {
            dialog!!.dismiss()
        }
    }

    fun errors(title: String, message: String) {
        if (message == getString(R.string.nointernet)) {
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE)
                .setBackgroundTint(ContextCompat.getColor(baseContext, R.color.batchColor))
                .setTextColor(ContextCompat.getColor(baseContext, R.color.white)).setAction(getString(R.string.ok)) {
                    isRetry.value = true
                }.show()
        } else {


            val alert = AlertDialog.Builder(this).setMessage(message).setCancelable(false)
                .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                        dialog, id -> dialog.dismiss()
                }.apply {
                    titleColor = ContextCompat.getColor(applicationContext, R.color.black)
                }).create()
            alert.setTitle(title)
            alert.show()
        }
    }


}