package com.management.myemployees.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.management.myemployees.R
import com.management.myemployees.activity.LoginActivity
import com.management.myemployees.databinding.FragmentProfileBinding
import com.management.myemployees.savedPreferences.SavedSharedPreference


class ProfileFragment : BaseFragment() {

private val profileBinding by lazy { FragmentProfileBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        profileBinding.const3.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(requireContext())
            dialogBuilder.setMessage("Are you sure you want to Logut?")
                .setCancelable(false)
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                .setPositiveButton("Logout", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                    this.let { it1 -> SavedSharedPreference.setUserData(requireActivity(), "","") }
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                    activity?.finishAffinity()
                })
                .show()
        }


        return profileBinding.root
    }

}