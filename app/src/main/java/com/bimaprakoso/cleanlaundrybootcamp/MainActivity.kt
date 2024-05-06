package com.bimaprakoso.cleanlaundrybootcamp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bimaprakoso.cleanlaundrybootcamp.adapter.MainPagerAdapter
import com.bimaprakoso.cleanlaundrybootcamp.base.retrofit.InstanceRetro.homeService
import com.bimaprakoso.cleanlaundrybootcamp.databinding.ActivityMainBinding
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.ResponseDetail
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.HomeResponse
import com.bimaprakoso.cleanlaundrybootcamp.presentation.viewmodel.HomeViewModel
import com.bimaprakoso.cleanlaundrybootcamp.presentation.viewmodel.LoginViewModel
import com.bimaprakoso.cleanlaundrybootcamp.screen.HomeFragment
import com.bimaprakoso.cleanlaundrybootcamp.screen.ProfileFragment
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var homeViewModel: HomeViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences = getSharedPreferences("data_user", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)

        if (token != null) {
            val editor = sharedPreferences.edit()

            homeViewModel?.getData("Bearer $token")

            homeService.getHome("Bearer $token").enqueue(object :
                Callback<ResponseDetail<HomeResponse>> {
                override fun onResponse(call: Call<ResponseDetail<HomeResponse>>, response: Response<ResponseDetail<HomeResponse>>) {
                    if (response.isSuccessful) {
                        val userResponse = response.body()
                        if (userResponse?.success == true) {
                            val userData = userResponse.data
                            if(userData != null) {
                                Toast.makeText(this@MainActivity, "Selamat datang kembali, ${userData.namaLengkap?.capitalize()}!",Toast.LENGTH_LONG).show()
                                editor.putString("nama", userData.namaLengkap?.toString())
                                editor.putString("email", userData.email?.toString())
                                editor.putString("totalLaundry", userData.totalLaundry?.toString())
                                editor.putString("totalPaid", userData.totalPaid?.toString())
                                editor.putString("totalDone", userData.totalDone?.toString())
                                editor.putString("totalTaken", userData.totalTaken?.toString())
                                editor.apply()
                            } else {
                                sharedPreferences.edit().remove("token").apply()
                                Toast.makeText(this@MainActivity, "Sesi login anda telah habis, silahkan login kembali!",Toast.LENGTH_LONG).show()
                                val intent = Intent(this@MainActivity,LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            sharedPreferences.edit().remove("token").apply()
                            val intent = Intent(this@MainActivity,LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        sharedPreferences.edit().remove("token").apply()
                        Toast.makeText(this@MainActivity, "Sesi login anda telah habis, silahkan login kembali!",Toast.LENGTH_LONG).show()
                        val intent = Intent(this@MainActivity,LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onFailure(call: Call<ResponseDetail<HomeResponse>>, t: Throwable) {
                    sharedPreferences.edit().remove("token").apply()
                    Toast.makeText(this@MainActivity, "Gagal menghubungi server, silahkan coba logi kembali!",Toast.LENGTH_LONG).show()
                    val intent = Intent(this@MainActivity,LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            })

            initializeToolbar()
            initializeViewPager()
        } else {
            // Token doesn't exist or is null
            // Handle this case accordingly
            sharedPreferences.edit().remove("token").apply()
            val intent = Intent(this@MainActivity,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initializeViewPager() {
        val adapter = MainPagerAdapter(this)
        adapter.addFragment(HomeFragment.newInstance("Home Screen", "Klik Chat", "Toast Dari Home Screen"))
        adapter.addFragment(ProfileFragment.newInstance("Profile Screen", "Klik Phone", "Toast Dari Phone Screen"))
        with(binding) {
            vpMain.adapter = adapter
            TabLayoutMediator(tabMain, vpMain) { tab, position ->
                when(position) {
                    0 -> {
                        tab.text = getString(R.string.label_home)
                        tab.icon = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_home)
                    }
                    1 -> {
                        tab.text = getString(R.string.label_profile)
                        tab.icon = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_profile)
                    }
                }
            }.attach()
        }
    }

    private fun initializeToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false) // Disable the original title
            setDisplayHomeAsUpEnabled(false)

            // Inflate the layout and set it as the custom view
            val toolbarLayout = layoutInflater.inflate(R.layout.toolbar_layout, null)
            setCustomView(toolbarLayout)

            // Set the color of the title
            val toolbarTitle =  toolbarLayout.findViewById<TextView>(R.id.toolbar_title)
            toolbarTitle.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.primary))

            val toolbarTitleSecondary = toolbarLayout.findViewById<TextView>(R.id.toolbar_title_secondary)
            toolbarTitleSecondary.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.secondary))
        }
    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
        toast.show()

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            toast.cancel() // Tutup toast setelah 5 detik
        }, 3000) // 5000 milliseconds = 5 detik
    }
}