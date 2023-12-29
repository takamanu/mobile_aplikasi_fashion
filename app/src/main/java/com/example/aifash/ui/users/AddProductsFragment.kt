package com.example.aifash.ui.users

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.aifash.R
import com.example.aifash.databinding.FragmentAddProductsBinding
import com.example.aifash.datamodel.ProductFashion
import com.example.aifash.auth.User
import com.example.aifash.trade.SupItemsFragment
import com.example.aifash.trade.SupItemsManualFragment
import com.google.gson.Gson
import org.tensorflow.lite.Interpreter
import java.io.File
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder


class AddProductsFragment : Fragment() {

    private var _binding: FragmentAddProductsBinding? = null
    private lateinit var sharedPreferences: SharedPreferences
    private val binding get() = _binding!!
    private var getFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProductsBinding.inflate(inflater, container, false)
//        sharedPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.pbScan.visibility = View.GONE
        binding.cvWarning.visibility = View.VISIBLE
        binding.btnSubmit.visibility = View.GONE
        binding.btnWrongPredict.visibility = View.GONE

        getPermission()

        binding.btnCamera.setOnClickListener { startCameraX() }
        binding.btnGallery.setOnClickListener { startGallery() }
        binding.btnUpload.setOnClickListener { uploadImage() }
        binding.btnSubmit.setOnClickListener {
            submitData(productJson, getFile)
        }
        binding.btnWrongPredict.setOnClickListener {
            manualSubmitData(productJson, getFile)
            }

    }

    private fun manualSubmitData(productJson: String?, file: File?) {
        val fragment = productJson?.let { SupItemsManualFragment.newInstance(it, file) }

        binding.btnUpload.visibility = View.VISIBLE
        binding.btnCamera.visibility = View.VISIBLE
        binding.btnGallery.visibility = View.VISIBLE
        binding.btnSubmit.visibility = View.GONE
        binding.btnWrongPredict.visibility = View.GONE
        if (fragment != null) {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, fragment)
                .addToBackStack("AddProductsFragmentTag")
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getPermission() {
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(),
            it
        ) == PackageManager.PERMISSION_GRANTED
    }



    private fun submitData(productJson: String?, file: File?) {
        Log.d(TAG, "Submit data: $productJson")
        Log.d(TAG, "Submit data: $file")

        val fragment = productJson?.let { SupItemsFragment.newInstance(it, file) }

        binding.btnUpload.visibility = View.VISIBLE
        binding.btnCamera.visibility = View.VISIBLE
        binding.btnGallery.visibility = View.VISIBLE
        binding.btnSubmit.visibility = View.GONE
        binding.btnWrongPredict.visibility = View.GONE



        if (fragment != null) {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, fragment)
                .addToBackStack("AddProductsFragmentTag")
                .commit()
        }
    }


    private fun startCameraX() {
        launcherIntentCameraX.launch(Intent(requireContext(), CameraActivity::class.java))
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        if (activityResult.resultCode == CAMERA_X_RESULT) {
            val myFile = activityResult.data?.getSerializableExtra("picture") as File
            val isRearCamera = activityResult.data?.getBooleanExtra("isRearCamera", true) as Boolean

            getFile = myFile
            val bitmapResult =
                Utils.rotateImage(
                    BitmapFactory.decodeFile(getFile?.path),
                    isRearCamera
                )

            // Update the ImageView or handle the bitmap as needed
//            binding.cvWarning.visibility = View.GONE
            binding.tvWarning.text = "Click predict to get the result!"
            binding.ivAddProducts.setImageBitmap(bitmapResult)
        }
    }


    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = Utils.uriToFile(selectedImg, requireContext())
            getFile = myFile
            binding.tvWarning.text = "Click predict to get the result!"
            binding.ivAddProducts.setImageURI(selectedImg)
        }
    }

    private fun uploadImage() {
        if (getFile != null) {
            binding.pbScan.visibility = View.VISIBLE
            binding.cvWarning.visibility = View.GONE
            binding.btnUpload.visibility = View.GONE
            binding.btnCamera.visibility = View.GONE
            binding.btnGallery.visibility = View.GONE
            binding.btnSubmit.visibility = View.VISIBLE
            binding.btnWrongPredict.visibility = View.VISIBLE

            // Load the TensorFlow Lite model
            val interpreter = Interpreter(loadModelFile(requireContext().assets, "lite_model.tflite"))

            // Load the image file and resize it to match the expected input size
            val originalBitmap = BitmapFactory.decodeFile(getFile!!.path)
            val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 28, 28, false)

            // Convert the resized bitmap to a ByteBuffer
            val byteBuffer = convertBitmapToByteBuffer(resizedBitmap)

            // Perform inference
            val result = Array(1) { FloatArray(10) } // Assuming NUM_CLASSES is 10
            interpreter.run(byteBuffer, result)

            // Find the predicted class
            val (predictedClass, points) = findPredictedClass(result)

            binding.pbScan.visibility = View.GONE
            binding.tvProductsSelected.text = "Predicted Class: $predictedClass"
            binding.tvTotalPointsGiven.text = "$points points"
            val carbon = points.toFloat().div(100)
            binding.tvTotalCarbon.text = "Carbon Reduction Value: $carbon kg Co2e"

            // Set the product name and points based on prediction
            val dummyUser = User(
                id = 1,
                name = "john_doe",
                email = "john_doe@example.com",
                password = "password_hash",
                role = 1,
                points = 0,
                fashion = null,
                userVouchers = null,
                deletedAt = "aaa",
                updatedAt = "nnn",
                createdAt = "kkk"
            )

            val dummyProduct = ProductFashion(
                user = dummyUser,
                categoryId = 1,
                name = predictedClass, // Set the predicted class as the name
                points = points // Set the predicted points
            )

            val gson = Gson()
            val productJson = gson.toJson(dummyProduct)
            Companion.productJson = productJson
            Log.d(TAG, "$productJson")

        } else {
            Utils.showToast(requireContext(), getString(R.string.no_file))
        }
    }

    // Load the TensorFlow Lite model from the assets folder
    private fun loadModelFile(assetManager: AssetManager, modelFileName: String): ByteBuffer {
        val inputStream: InputStream = assetManager.open(modelFileName)
        val modelSize = inputStream.available()
        val buffer = ByteBuffer.allocateDirect(modelSize)
        buffer.order(ByteOrder.nativeOrder())
        inputStream.use { input ->
            val bytes = ByteArray(modelSize)
            input.read(bytes)
            buffer.put(bytes)
        }
        buffer.rewind()
        return buffer
    }



    // Convert a bitmap to a ByteBuffer
    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(4 * 28 * 28 * 1) // Replace 28 and 1 with your input size and channels
        byteBuffer.order(ByteOrder.nativeOrder())
        val intValues = IntArray(28 * 28)
        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        for (pixelValue in intValues) {
            val normalizedValue = (pixelValue shr 16 and 0xFF) / 255.0f
            byteBuffer.putFloat(normalizedValue)
        }
        return byteBuffer
    }

    // Find the predicted class (you can adapt this based on your model's output)
    private fun findPredictedClass(result: Array<FloatArray>): Pair<String, Int> {
        // Replace this logic with your model-specific logic to find the predicted class
        val maxIndex = result[0].indices.maxByOrNull { result[0][it] } ?: 0
        val classNames = arrayOf(
            "T-shirt/top", "Trouser", "Pullover", "Dress", "Coat",
            "Sandal", "Shirt", "Sneaker", "Bag", "Ankle boot"
        )
        val predictedClassName = classNames[maxIndex]
        val points = when (maxIndex) {
            0 -> 210
            1 -> 550
            2 -> 360
            3 -> 490
            4 -> 1330
            5 -> 70
            6 -> 330
            7 -> 320
            8 -> 230
            9 -> 260
            else -> 70 // Default points
        }

        return Pair(predictedClassName, points)
    }

    companion object {
        const val TAG = "AddProductsFragment"
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        var productJson: String? = null
    }

}
