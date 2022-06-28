package com.sebastianjoya.twitunab.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.sebastianjoya.twitunab.R
import com.sebastianjoya.twitunab.databinding.ActivitySignUpBinding
import com.sebastianjoya.twitunab.viewmodel.SignUpActivityViewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding
    lateinit var viewModel: SignUpActivityViewModel
    lateinit var resultCamera: ActivityResultLauncher<Intent>
    var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "REGISTRO DE USUARIO"
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        viewModel = ViewModelProvider(this)[SignUpActivityViewModel::class.java]

        binding.viewModel = viewModel

        //Logica del boton de cerrar
        binding.buSignUpReturn.setOnClickListener{
            finish()
        }

        //logica del boton de aceptar
        binding.buSignUpConfirm.setOnClickListener{
            viewModel.signUp(photoUri).observe(this){

                Toast.makeText(applicationContext,"Usuario creado con exito", Toast.LENGTH_SHORT).show()

//                it?.let{
//                    Toast.makeText(applicationContext,"Usuario creado con exito",Toast.LENGTH_SHORT).show()
//                }?:run{
//                    Toast.makeText(applicationContext,"No se pudo crear el usuario",Toast.LENGTH_SHORT).show()
//                }
            }
        }

        //Logica de la camara

        resultCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK) run {
                Glide.with(applicationContext).load(photoUri).into(binding.ivSignUpImg)
            }

        }

        binding.ibSignUpCamera.setOnClickListener{
            val cameraItem = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            var photoFile: File? = null
            try{
                photoFile = createImage()
            }catch(e: IOException){

            }

            photoFile?.let{
                photoUri = FileProvider.getUriForFile(applicationContext,"com.sebastianjoya.twitunab.fileprovider",photoFile)
                cameraItem.putExtra(MediaStore.EXTRA_OUTPUT,photoUri)
                resultCamera.launch(cameraItem)
            }
        }
    }

    private fun createImage(): File? {
        var timeStamp = SimpleDateFormat("yyyy-MM-dd-HHmmss", Locale.US).format(Date())
        val storeAppDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(timeStamp, ".jpg",storeAppDir)
    }
}