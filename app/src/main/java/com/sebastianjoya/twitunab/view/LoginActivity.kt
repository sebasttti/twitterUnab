package com.sebastianjoya.twitunab.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sebastianjoya.twitunab.R
import com.sebastianjoya.twitunab.databinding.ActivityLoginBinding
import com.sebastianjoya.twitunab.viewmodel.LoginActivityViewModel

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this)[LoginActivityViewModel::class.java]

        binding.viewModel = viewModel
    }
}