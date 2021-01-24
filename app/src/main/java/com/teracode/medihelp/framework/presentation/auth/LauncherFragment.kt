package com.teracode.medihelp.framework.presentation.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.teracode.medihelp.R
import kotlinx.android.synthetic.main.fragment_launcher.*

class LauncherFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_launcher, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        launcher_login.setOnClickListener {
            findNavController().navigate(R.id.action_launcherFragment_to_loginFragment)
        }
        launcher_register.setOnClickListener {
            findNavController().navigate(R.id.action_launcherFragment_to_registerFragment)
        }
        launcher_forgot.setOnClickListener {
            findNavController().navigate(R.id.action_launcherFragment_to_forgotPasswordFragment)
        }


    }
}