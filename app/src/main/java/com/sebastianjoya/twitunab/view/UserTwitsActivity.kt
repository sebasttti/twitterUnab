package com.sebastianjoya.twitunab.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sebastianjoya.twitunab.R
import com.sebastianjoya.twitunab.databinding.ActivityUserTwitsBinding
import com.sebastianjoya.twitunab.model.entity.User
import com.sebastianjoya.twitunab.viewmodel.UserTwitsActivityViewModel

class UserTwitsActivity : AppCompatActivity() {

    lateinit var binding:ActivityUserTwitsBinding
    lateinit var viewModel:UserTwitsActivityViewModel
    lateinit var adapter: TwitAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var bundle:Bundle? = intent.extras
        var user:User? = intent.getSerializableExtra("user") as User?

        if (user != null) {
            title = "Bienvenid@ ${user.name}"
        }else{
            title="Bienvenid@"
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_twits)

        viewModel = ViewModelProvider(this)[UserTwitsActivityViewModel::class.java]

        adapter = TwitAdapter(arrayListOf())

        binding.adapter = adapter

        loadData()



    }

    fun loadData(){
        viewModel.twits
    }
}