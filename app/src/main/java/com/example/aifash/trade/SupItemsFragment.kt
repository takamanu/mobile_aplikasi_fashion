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
import androidx.navigation.findNavController
import com.example.aifash.MainActivity2
import com.example.aifash.R
import com.example.aifash.api.ApiConfig
import com.example.aifash.api.ApiService
import com.example.aifash.auth.login.LoginResponse
import com.example.aifash.databinding.FragmentSupItemsBinding
import com.example.aifash.datamodel.ProductFashion
import com.example.aifash.ui.users.AddProductsViewModel
import com.example.aifash.ui.users.Utils
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class SupItemsFragment : Fragment() {
    private var _binding: FragmentSupItemsBinding? = null
    private val binding get() = _binding!!
    private var getFile: File? = null
    private var result: Bitmap? = null
    private val apiService: ApiService = ApiConfig.createApiService()

    private lateinit var sharedPreferences: SharedPreferences
    private val addProductsViewModel: AddProductsViewModel by viewModels()
    private lateinit var storyViewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSupItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storyViewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        val productJson = arguments?.getString(ARG_PRODUCT_JSON)
        val product = Gson().fromJson(productJson, ProductFashion::class.java)

        val filePath = arguments?.getString(ARG_FILE)
        getFile = if (filePath != null) File(filePath) else null

        Log.d(TAG, "File = $getFile")

        sharedPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        val loginResponseJson = sharedPreferences.getString("loginResponse", null)
        val gson = Gson()

        val loginResponse = gson.fromJson(loginResponseJson, LoginResponse::class.java)
        val userName = loginResponse.user?.name


        binding.tvName.text = product.name
//        binding.tvCategory.text = "Category: " + product.categoryId.toString()
        binding.tvAmount.text = "Amount: ${product.points}"

        binding.tvUser.text = "User: $userName"

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
            val productJson = Gson().toJson(product)

            val navController = (requireActivity() as MainActivity2).findNavController(R.id.nav_host_fragment_activity_main2)
//            addProductsViewModel.fetchProducts()
            Log.d("SupItemsFragment", "This is the data sent: $productJson")

            val fragmentManager = parentFragmentManager
            fragmentManager.popBackStack("AddProductsFragmentTag", FragmentManager.POP_BACK_STACK_INCLUSIVE)



            lifecycleScope.launch {
                // Pass the file to the uploadImage function
                Log.d(TAG, "File pas mau masuk view model = $getFile")

                uploadImage(getFile, requireContext())
            }

            // Navigate to NotificationsFragment
//            navController.navigate(R.id.navigation_notifications)
        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun uploadImage(getFile: File?, context: Context) {
        if (getFile != null) {
            val productJson = arguments?.getString(ARG_PRODUCT_JSON)
            val product = Gson().fromJson(productJson, ProductFashion::class.java)

            sharedPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
            val loginResponseJson = sharedPreferences.getString("loginResponse", null)
            val gson = Gson()

            val loginResponse = gson.fromJson(loginResponseJson, LoginResponse::class.java)
            val userId = loginResponse.user?.id

            val file = Utils.compressImage(getFile)

            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart = MultipartBody.Part.createFormData(
                "fashion_url_image",
                file.name,
                requestImageFile
            )

            Log.d("SupItemsFragment", "Name: ${product.name}, Points: ${product.points}, Image $file")


            // Directly call the ApiService function

            Log.d(TAG, "Requesting API: ${product.name}, ${product.points}, 1, $imageMultipart")

            product.name?.let {
                product.points?.let { it1 ->
                    if (userId != null) {
                        storyViewModel.uploadImage(
                            it,
                            it1,
                            imageMultipart,
                            userId,
                            object : Utils.ApiCallbackString {
                                override fun onResponse(success: Boolean, message: String) {
                                    //                        showAlertDialog(success, message)
                                    Utils.showToast(context, message)
                                    // Navigate back to the AddProductsFragment


                                    //                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                }
                            })
                    }
                }
            }

        } else {
            Utils.showToast(requireContext(), getString(R.string.no_file))
        }
    }


    companion object {
        const val TAG = "SupItemsFragment"
        const val ARG_PRODUCT_JSON = "arg_product_json"
        const val ARG_FILE = "arg_file"

        fun newInstance(productJson: String, file: File?): SupItemsFragment {
            val fragment = SupItemsFragment()
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

