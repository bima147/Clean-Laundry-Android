package com.bimaprakoso.cleanlaundrybootcamp

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bimaprakoso.cleanlaundrybootcamp.base.retrofit.InstanceRetro
import com.bimaprakoso.cleanlaundrybootcamp.base.retrofit.InstanceRetro.registerService
import com.bimaprakoso.cleanlaundrybootcamp.databinding.ActivityRegisterBinding
import com.bimaprakoso.cleanlaundrybootcamp.domain.request.RegisterRequest
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.LoginResponse
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.ResponseDetail
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Instant
import java.time.ZoneId


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var dateOfBirth: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            // Disable the login button and show the ProgressBar
            binding.btnRegister.isEnabled = false
            binding.pbRegister.visibility = View.VISIBLE


            val email = binding.edtEmail.text.toString()
            val username = binding.edtUsername.text.toString()
            val address = binding.edtAlamat.text.toString()
            val password = binding.edtPassword.text.toString()
            val name = binding.edtNamaLengkap.text.toString()
            val phone = binding.edtPhone.text.toString()
            if (email.isNotEmpty() && address.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty() && phone.isNotEmpty()) {
                val registerData = RegisterRequest(name, address, dateOfBirth, phone, username, email, password)

                registerService.registerRequest(registerData).enqueue(object :
                    Callback<ResponseDetail<String>> {
                    override fun onResponse(call: Call<ResponseDetail<String>>, response: Response<ResponseDetail<String>>) {
                        if (response.isSuccessful) {
                            moveToLogin()
                            // Enable the login button and hide the ProgressBar
                            binding.btnRegister.isEnabled = true
                            binding.pbRegister.visibility = View.GONE
                        } else {
                            Toast.makeText(this@RegisterActivity, response.body()?.message.toString(),
                                Toast.LENGTH_LONG).show()
                            // Enable the login button and hide the ProgressBar
                            binding.btnRegister.isEnabled = true
                            binding.pbRegister.visibility = View.GONE
                        }
                    }

                    override fun onFailure(call: Call<ResponseDetail<String>>, t: Throwable) {
                        Log.e("Error", t.message.toString())
                        Toast.makeText(this@RegisterActivity, "Gagal menghubungi server!", Toast.LENGTH_SHORT).show()
                        // Enable the login button and hide the ProgressBar
                        binding.btnRegister.isEnabled = true
                        binding.pbRegister.visibility = View.GONE
                    }
                }
                )
            } else {
                Toast.makeText(this@RegisterActivity, "All fields are required", Toast.LENGTH_LONG).show()
                // Enable the login button and hide the ProgressBar
                binding.btnRegister.isEnabled = true
                binding.pbRegister.visibility = View.GONE
            }
        }

        binding.btnLogin.setOnClickListener {
            moveToLogin()
        }
    }

    fun moveToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun showDatePickerDialog(v: View?) {
        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setTitleText("Select a date")
        val picker = builder.build()
        picker.show(supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener { selection: Long? ->
            // Convert the selection to a LocalDate object
            val date =
                Instant.ofEpochMilli(selection!!).atZone(ZoneId.systemDefault())
                    .toLocalDate()
            // Update the text field
            val dateField = binding.edtDateOfBirth
            dateOfBirth = dateField.toString()
            dateField.setText(date.toString())
        }
    }
}