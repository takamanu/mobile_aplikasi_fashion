package com.example.aifash.trade

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.aifash.R
import com.example.aifash.api.ApiConfig
import com.example.aifash.api.ApiService
import com.example.aifash.auth.login.LoginResponse
import com.example.aifash.databinding.FragmentSupItemsManualBinding
import com.example.aifash.datamodel.ProductFashion
import com.example.aifash.ui.users.AddProductsViewModel
import com.example.aifash.ui.users.Utils
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class SupItemsManualFragment : Fragment() {
    private var _binding: FragmentSupItemsManualBinding? = null
    private val binding get() = _binding!!
    private var getFile: File? = null
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var storyViewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSupItemsManualBinding.inflate(inflater, container, false)

        return binding.root
    }

    private fun getUserData(): LoginResponse {
        sharedPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)

        val loginResponseJson = sharedPreferences.getString("loginResponse", null)
        val gson = Gson()

        return gson.fromJson(loginResponseJson, LoginResponse::class.java)
    }

    private fun getProductData(): ProductFashion {
        val productJson = arguments?.getString(SupItemsFragment.ARG_PRODUCT_JSON)

        return Gson().fromJson(productJson, ProductFashion::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storyViewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        val filePath = arguments?.getString(ARG_FILE)
        getFile = if (filePath != null) File(filePath) else null

        val userName = getUserData().user?.name

        binding.tvUser.text = String.format("User: %s", userName)

        binding.modalContent.translationY = binding.modalCard.height.toFloat()
        binding.modalContent.animate()
            .translationY(0f)
            .setDuration(500)
            .start()

        binding.btnCancel.setOnClickListener {
            val fragmentManager = parentFragmentManager
            fragmentManager.popBackStack("AddProductsFragmentTag", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }


        binding.btnDonate.setOnClickListener {

            val fragmentManager = parentFragmentManager
            fragmentManager.popBackStack("AddProductsFragmentTag", FragmentManager.POP_BACK_STACK_INCLUSIVE)

            lifecycleScope.launch {

                uploadImage(getFile, requireContext())
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun uploadImage(getFile: File?, context: Context) {
        if (getFile != null) {

            val token = getUserData().user?.token?.accessToken

            val file = Utils.compressImage(getFile)

            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart = MultipartBody.Part.createFormData(
                "attachment",
                file.name,
                requestImageFile
            )
            val productRevisionName = binding.edModalFashionName.text.toString()

            val utils = object : Utils.ApiCallbackString {
                override fun onResponse(success: Boolean, message: String) {
                    Utils.showToast(context, message)
                }
            }

                if (token != null) {
                    storyViewModel.uploadImage(
                        productRevisionName,
                        0,
                        imageMultipart,
                        token,
                        utils)
                }

        } else {
            Utils.showToast(requireContext(), getString(R.string.no_file))
        }
    }


    companion object {
        const val TAG = "SupItemsManualFragment"
        const val ARG_PRODUCT_JSON = "arg_product_json"
        const val ARG_FILE = "arg_file"

        fun newInstance(productJson: String, file: File?): SupItemsManualFragment {
            val fragment = SupItemsManualFragment()
            val args = Bundle()
            args.putString(ARG_PRODUCT_JSON, productJson)
            if (file != null) {
                args.putString(ARG_FILE, file.absolutePath) // Store the file path as a String
            }
            fragment.arguments = args
            return fragment
        }
    }
}