package com.inferno.mobile.bedon_waseet.ui.signup

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.inferno.graphql.GraphqlQuery
import com.inferno.graphql.QueryParameter
import com.inferno.mobile.bedon_waseet.databinding.SignupFragmentBinding
import com.inferno.mobile.bedon_waseet.responses.Response
import com.inferno.mobile.bedon_waseet.utils.UserType
import com.inferno.mobile.bedon_waseet.utils.logIn
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.inferno.mobile.bedon_waseet.activities.SplashActivity
import com.inferno.mobile.bedon_waseet.responses.AvatarResult
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.createCircleAnnotationManager
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class SignUpFragment : Fragment() {
    private lateinit var binding: SignupFragmentBinding
    private val viewModel: SignUpViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SignupFragmentBinding.inflate(inflater, container, false)

        binding.selectImage.setOnClickListener {
            if (requireContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED
            ) {
                requireActivity().requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    101
                )
            } else openImageChooser()
        }



        binding.signupBtn.setOnClickListener {
            val username = binding.usernameFiled.editableText.toString()
            val password = binding.passwordFiled.editableText.toString()
            val email = binding.emailFiled.editableText.toString()
            val type: UserType = if (binding.isOwner.isChecked)
                UserType.Owner
            else UserType.Customer
//            val mutation = createRegisterMutation(username, password, email, type)
            viewModel.register(username, password, email, type)
                .observe(requireActivity(), this::registerResponse)

        }
        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finishAffinity()
                }
            })
    }

    private fun openImageChooser() {

        var chooseFile = Intent(Intent.ACTION_GET_CONTENT)
        chooseFile.type = "*/*"
        chooseFile = Intent.createChooser(chooseFile, "Choose a file")
        startActivityForResult(chooseFile, 101)
    }

    private fun registerResponse(rr: Response) {
        Toast.makeText(requireContext(), rr.message, Toast.LENGTH_LONG).show()
        println(rr.code.toString())
        if (rr.code == 200) {
            logIn(requireContext(), rr.data!!, rr.type)
            val _intent = Intent(requireActivity(), SplashActivity::class.java)
            requireActivity().finish()
            startActivity(_intent)
        }
    }


    private fun createRegisterMutation(
        username: String,
        password: String,
        email: String,
        type: UserType
    ): String {
        return GraphqlQuery.Builder()
            .startMutation()
            .addMutation(
                mutationName = "response:register", selectedFields = arrayOf(
                    "code", "msg", "data"
                ), args = arrayOf(
                    QueryParameter(key = "user_name", value = username),
                    QueryParameter(key = "password", value = password),
                    QueryParameter(key = "email", value = email),
                    QueryParameter(key = "type", value = type.name),
                )
            )
            .end()
            .build()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101) {
            if (permissions.isNotEmpty() && permissions[0]
                == Manifest.permission.READ_EXTERNAL_STORAGE
            ) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) openImageChooser()
                else Toast.makeText(
                    requireContext(),
                    "This Permission required for image.", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && requestCode == 101 && data.data != null) {
            val uri = data.data
            if (uri != null) {
                val path = getPath(requireContext(), uri)
                Glide.with(requireContext())
                    .load(File(path))
                    .into(binding.selectImage)

            }
            /*   viewModel.uploadAvatar(requireContext(), uri!!)
                   .observe(requireActivity(), this::avatarUploadResult)*/
        }
    }

    private fun avatarUploadResult(result: AvatarResult) {
        Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
        println(result.data.name)
    }


    private fun getFile(context: Context, contentUri: Uri): File? {
        return try {
            val bitmap =
                MediaStore.Images.Media.getBitmap(context.contentResolver, contentUri)
            val imageType: String = context.contentResolver.getType(contentUri)!!
            val extension = imageType.substring(imageType.indexOf("/") + 1)
            val f = File(context.cacheDir, "filename.$extension")
            f.createNewFile()
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 75, bos)
            val bitmapData = bos.toByteArray()
            val fos = FileOutputStream(f)
            fos.write(bitmapData)
            fos.flush()
            fos.close()
            f
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun getPath(context: Context, uri: Uri): String {
        val file = getFile(context, uri)
        return if (file != null)
            file.path
        else ""
    }


}