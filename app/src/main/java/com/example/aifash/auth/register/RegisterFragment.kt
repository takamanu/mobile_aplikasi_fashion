package com.example.aifash.auth.register

import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.aifash.R
import com.example.aifash.Result
import com.google.android.material.snackbar.Snackbar

class RegisterFragment: Fragment() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_register, container, false)
        etName = rootView.findViewById(R.id.ed_register_name)
        etEmail = rootView.findViewById(R.id.ed_register_email)
        etPassword = rootView.findViewById(R.id.ed_register_password)
        btnRegister = rootView.findViewById(R.id.btnRegister)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().length < 8) {
                    etPassword.setError("Password tidak boleh kurang dari 8 karakter", null)
                } else {
                    etPassword.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })



        btnRegister.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val role = 0

            if (isValidEmail(email)) {
                viewModel.register(name, email, password, role)
            } else {
                val message = "Registration failed: Invalid email address!"
                showSnackBar(message, RED)
            }
        }

        viewModel.registerResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
//                    val registerResponse = result.data
//                    val username = registerResponse?.username
////                    val role = registerResponse?.role
                    val message = "Hey, ${result.data?.user?.name}! Your registration is succesful!"
                    showSnackBar(message, GREEN)


                }
                is Result.Error -> {
                    val exception = result.exception
                    // Handle registration error
                    val message = "Registration failed: ${exception.message}"
                    showSnackBar(message, RED)

                }
                else -> {
                    val message = "Registration failed: Contact admin!"
                    showSnackBar(message, RED)

                }
            }
        }
    }
    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun showSnackBar(argSatu: String, argDua: String) {
        val snackBar =
            view?.let { Snackbar.make(it, argSatu, Snackbar.LENGTH_LONG) }
        val snackBarView = snackBar?.view
        val textView = snackBarView?.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView?.setTypeface(textView?.typeface, Typeface.BOLD)
        if (argDua == "red") {
            snackBar?.setBackgroundTint(resources.getColor(R.color.red))
            snackBar?.show()
        } else {
            snackBar?.setBackgroundTint(resources.getColor(R.color.green))
            snackBar?.show()
        }
    }

    companion object {
        private const val TAG = "RegisterFragment"
        private const val RED = "red"
        private const val GREEN = "green"
    }

}
