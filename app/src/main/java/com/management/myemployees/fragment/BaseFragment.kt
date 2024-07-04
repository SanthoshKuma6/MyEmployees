package com.management.myemployees.fragment

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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

abstract  class BaseFragment : Fragment(){

    var dialog: AlertDialog? = null
    var isRetry = MutableLiveData<Boolean>()
    private lateinit var networkHelper: NetworkHelper
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelFactory(MainRepository(ApiClient.apis)))[MainViewModel::class.java]


    }

    fun errors(title: String, message: String) {
        if (message == getString(R.string.nointernet)) {
            Snackbar.make(requireActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE)
                .setBackgroundTint(ContextCompat.getColor(requireActivity(), R.color.batchColor))
                .setTextColor(ContextCompat.getColor(requireActivity(), R.color.white)).setAction(getString(
                    R.string.ok)) {
                    isRetry.value = true
                }.show()
        } else {


            val alert = AlertDialog.Builder(requireActivity()).setMessage(message).setCancelable(false)
                .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                        dialog, id -> dialog.dismiss()
                }.apply {
                    requireActivity().titleColor = ContextCompat.getColor(requireActivity(), R.color.black)
                }).create()
            alert.setTitle(title)
            alert.show()
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
            dialog = AlertDialog.Builder(requireContext()).setView(view).setCancelable(false).show()
            dialog!!.window!!.setLayout(260, ViewGroup.LayoutParams.WRAP_CONTENT)
//            Glide.with(this).load(R.raw.load).into(view.findViewById(R.id.ProgressBar))

        } else {
            dialog!!.dismiss()
        }
    }
}