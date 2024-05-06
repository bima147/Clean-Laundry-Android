//package com.example.restau
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.viewpager2.widget.ViewPager2
//import com.example.restau.Adapter.MenuPagerAdapter
//import com.example.restau.Model.Struk
//import com.example.restau.Retrofit.InstanceRetro
//import com.example.restau.Service.JenisMenuService
//import com.google.android.material.tabs.TabLayout
//import com.google.android.material.tabs.TabLayoutMediator
//import com.example.restau.databinding.ActivityMenuBinding
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//
//class MenuActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityMenuBinding
//    private lateinit var struk: Struk
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMenuBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//
//        val viewPager: ViewPager2 = binding.viewPager
//        val tabLayout: TabLayout = binding.tabLayout
//
//        val struk = intent.getSerializableExtra("struk") as Struk
//        // Set the text of the TextView to the table number and customer name
//        val tableInfo = "No Tabel : ${struk.idTabel}\nNama Kustomer : ${struk.namaKustomer}"
//        binding.tvTableInfo.text = tableInfo
//
//        createStruk(struk)
//        getJenisMenu(viewPager)
//    }
//
//    private fun createStruk(struk: Struk){
//        CoroutineScope(Dispatchers.IO).launch{
//            val response = InstanceRetro.strukService.createStruk(struk)
//
//            withContext(Dispatchers.Main){
//                if(response.isSuccessful){
//                    println("Request berhasil: ${response.body()}")
//                }else{
//                    println("Request gagal createStruk: ${response.errorBody()}, ${response.body()}")
//                    println("Response code: ${response.code()}")
//                    Toast.makeText(this@MenuActivity,"Meja sedang dipakai!",Toast.LENGTH_LONG).show()
//                    val intent = Intent(this@MenuActivity,MainActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                }
//            }
//        }
//    }
//
//    private fun getJenisMenu(viewPager: ViewPager2) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val response = InstanceRetro.jenisMenuService.getAllJenisMenu()
//
//            withContext(Dispatchers.Main) {
//                if (response.isSuccessful) {
//                    val jenisMenus = response.body()?.data ?: listOf()
//
//                    // Extract the IDs and names of the menu types
//                    val menuTypeIds = jenisMenus.map { it.id }
//                    val menuTypeNames = jenisMenus.map { it.namaJenis }
//
//                    // Set up the ViewPager with the sections adapter.
//                    viewPager.adapter = MenuPagerAdapter(this@MenuActivity, menuTypeIds, struk)
//
//                    // Connect the tab layout with the view pager
//                    TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
//                        tab.text = menuTypeNames[position]
//                    }.attach()
//                } else {
//                    println("Request gagal getjenisMenu: ${response.errorBody()}, ${response.body()}")
//                    println("Response code: ${response.code()}")
//                    Toast.makeText(this@MenuActivity,"Failed to fetch menu types!",Toast.LENGTH_LONG).show()
//                }
//            }
//        }
//    }
//
//}