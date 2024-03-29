package com.example.aifash.auth.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.aifash.MainActivity
import com.example.aifash.MainActivity2
import com.example.aifash.R
import com.example.aifash.ResultAsync
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LoginFragment :Fragment() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var viewModel: LoginViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)
        etEmail = rootView.findViewById(R.id.ed_login_email)
        etPassword = rootView.findViewById(R.id.ed_login_password)
        btnLogin = rootView.findViewById(R.id.btn_login)
        sharedPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]


        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            lifecycleScope.launch {
                delay(2000) // 2000 milliseconds (2 seconds)
                val intent = Intent(activity, MainActivity2::class.java)
                startActivity(intent)
                activity?.finish()
            }

            if (isValidEmail(email)) {
//                val intent = Intent(activity, MainActivity2::class.java)
//                startActivity(intent)
//                activity?.finish()
                viewModel.login(email, password)
            } else {
                val message = "Invalid email address!"
                showSnackBar(message, RED)
            }



        }

        viewModel.loginResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultAsync.Success -> {
                    val loginResponse = result.data

                    val gson = Gson()
                    val loginResponseJson = gson.toJson(loginResponse)
                    sharedPreferences.edit().putString("loginResponse", loginResponseJson).apply()

                    val token = loginResponse?.user?.token?.accessToken
                    val user = loginResponse?.user?.name
//                    val role = loginResponse?.userrole

                    Log.d(TAG, "Login response: $loginResponse")
                    Log.d(TAG, "Token: $token")
                    Log.d(TAG, "User: $user")
//                    Log.d(TAG, "Role: $role")
                    Log.d(TAG, "Kalo disimpen jadi string: $loginResponseJson")

                    val message = "Login success"
                    showSnackBar(message, GREEN)

//                    if (role == 1) {
//                        lifecycleScope.launch {
//                            delay(2000) // 2000 milliseconds (2 seconds)
//                            val intent = Intent(activity, MainActivity::class.java)
//                            startActivity(intent)
//                            activity?.finish()
//                        }
//                    } else {
//                        }
                        lifecycleScope.launch {
                            delay(2000) // 2000 milliseconds (2 seconds)
                            val intent = Intent(activity, MainActivity2::class.java)
                            startActivity(intent)
                            activity?.finish()
                    }
                }
                is ResultAsync.Error -> {
                    val exception = result.exception
                    val message = "Login failed: ${exception.message}"
                    showSnackBar(message, RED)

                }
                else -> {
                    // Handle login error
                    val message = "Login failed: Contact Admin!"
                    showSnackBar(message, RED)

                }
            }
        }
    }

    private fun showSnackBar(argSatu: String, argDua: String) {
        val snackBar =
            view?.let { Snackbar.make(it, argSatu, Snackbar.LENGTH_LONG) }
        val snackBarView = snackBar?.view
        val textView = snackBarView?.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView?.setTypeface(textView.typeface, Typeface.BOLD)
        if (argDua == "red") {
            snackBar?.setBackgroundTint(resources.getColor(R.color.red))
            snackBar?.show()
        } else {
            snackBar?.setBackgroundTint(resources.getColor(R.color.green))
            snackBar?.show()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    companion object {
        private const val TAG = "LoginFragment"
        private const val RED = "red"
        private const val GREEN = "green"
    }
}

