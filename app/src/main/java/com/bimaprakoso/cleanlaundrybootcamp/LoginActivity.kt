package com.bimaprakoso.cleanlaundrybootcamp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bimaprakoso.cleanlaundrybootcamp.databinding.ActivityLoginBinding
import com.bimaprakoso.cleanlaundrybootcamp.domain.request.LoginRequest
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.LoginResponse
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.ResponseDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Context
import android.view.View
import com.bimaprakoso.cleanlaundrybootcamp.base.retrofit.InstanceRetro.loginService
import com.bimaprakoso.cleanlaundrybootcamp.presentation.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private var loginViewModel: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences = getSharedPreferences("data_user", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val token = sharedPreferences.getString("token", null)

        if (token != null) {
            moveToMain()
        }

//        initiate()
//        observer()

        binding.btnRegister.setOnClickListener {
            moveToRegister()
        }

        binding.btnLogin.setOnClickListener {
            // Disable the login button and show the ProgressBar
            binding.btnLogin.isEnabled = false
            binding.pbLogin.visibility = View.VISIBLE

            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()

//            loginViewModel?.postLogin(username,password)
            val loginRequest = LoginRequest(username, password)

            loginService.loginRequest(loginRequest).enqueue(object :
                Callback<ResponseDetail<LoginResponse>> {
                    override fun onResponse(call: Call<ResponseDetail<LoginResponse>>, response: Response<ResponseDetail<LoginResponse>>) {
                        if (response.isSuccessful) {
                            editor.putString("token", response.body()?.data?.token.toString())
                            editor.apply()
                            moveToMain()
                            // Enable the login button and hide the ProgressBar
                            binding.btnLogin.isEnabled = true
                            binding.pbLogin.visibility = View.GONE
                        } else {
                            Toast.makeText(this@LoginActivity, "Username atau password tidak sesuai!",Toast.LENGTH_LONG).show()
                            // Enable the login button and hide the ProgressBar
                            binding.btnLogin.isEnabled = true
                            binding.pbLogin.visibility = View.GONE
                        }
                    }

                    override fun onFailure(call: Call<ResponseDetail<LoginResponse>>, t: Throwable) {
                        Log.e("Error", t.message.toString())
                        Toast.makeText(this@LoginActivity, "Gagal menghubungi server!",Toast.LENGTH_SHORT).show()
                        // Enable the login button and hide the ProgressBar
                        binding.btnLogin.isEnabled = true
                        binding.pbLogin.visibility = View.GONE
                    }
                }
            )
        }
    }

//    private fun initiate() {
//        loginViewModel = ViewModelProvider(this, AppModule.loginViewModelFactory)[LoginViewModel::class.java]
//    }
//
//    private fun observer() {
//        loginViewModel?.login?.observeIn(this) {
//            when (it) {
//                is Success -> storeToken(it.data)
//                is Error -> Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
//                is Loading -> showLoader(it.isLoading)
//                is Initiate -> {}
//            }
//        }
//    }
//
//    private fun storeToken(data: User) {
//        lifecycleScope.launch {
//            DataStoreUtils.get().updateData {
//                data
//            }
//            moveToMain()
//        }
//    }
//
    private fun moveToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun moveToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
//
//    private fun showLoader(isLoading: Boolean) {
//        with(binding) {
//            btnLogin.isVisible = !isLoading
//            pbLogin.isVisible = isLoading
//        }
//    }
}