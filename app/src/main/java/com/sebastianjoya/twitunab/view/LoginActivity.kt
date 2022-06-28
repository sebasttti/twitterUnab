package com.sebastianjoya.twitunab.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

        //Evento del boton de SignUp
        binding.buLoginSignup.setOnClickListener{
            val intentSignUp = Intent(applicationContext,SignUpActivity::class.java)
            startActivity(intentSignUp)
        }

        //Evento del login
        binding.buLoginLogin.setOnClickListener{

            viewModel.login().observe(this){

                it?.let {
                    Toast.makeText(applicationContext,"Login correcto", Toast.LENGTH_SHORT).show()

                    val intentSignUp = Intent(applicationContext, UserTwitsActivity::class.java)

                    intentSignUp.apply{
                        putExtra("user",it)
                    }

                    startActivity(intentSignUp)



                }?:run{
                    Toast.makeText(applicationContext,"Login incorrecto", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }




}