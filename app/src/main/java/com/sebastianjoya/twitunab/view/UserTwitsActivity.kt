package com.sebastianjoya.twitunab.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sebastianjoya.twitunab.R
import com.sebastianjoya.twitunab.databinding.ActivityUserTwitsBinding
import com.sebastianjoya.twitunab.model.entity.Twit
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

        viewModel.user = user!!

        adapter = TwitAdapter(arrayListOf())

        binding.adapter = adapter

        loadData()

    }

    override fun onResume() {
        viewModel.loadData()
        super.onResume()
    }

    private fun loadData(){
        viewModel.twits.observe(this){
            adapter.refresh(it as ArrayList<Twit>)
        }
    }
}