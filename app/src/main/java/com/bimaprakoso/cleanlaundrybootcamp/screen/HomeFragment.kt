package com.bimaprakoso.cleanlaundrybootcamp.screen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bimaprakoso.cleanlaundrybootcamp.adapter.LaundryAdapter
import com.bimaprakoso.cleanlaundrybootcamp.adapter.LaundryItem
import com.bimaprakoso.cleanlaundrybootcamp.base.retrofit.InstanceRetro.listTransaction
import com.bimaprakoso.cleanlaundrybootcamp.databinding.FragmentHomeBinding
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.ListItemResponse
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.ResponseList
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.TransactionResponse
import com.bimaprakoso.cleanlaundrybootcamp.utils.BundleConstant
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var title: String? = null
    private var button: String? = null
    private var message: String? = null

    private var page: Int = 0
    private var sort: String = "asc"
    private var sortBy: String = "id"
    private var filterBy: String = ""
    private var value: String = ""
    private var size: Int = 100


    private lateinit var transactionListResponse: List<ResponseList<ListItemResponse<TransactionResponse>>>

    companion object {
        @JvmStatic
        fun newInstance(title: String, button: String, message: String) = HomeFragment().apply {
            arguments = Bundle().apply {
                putString(BundleConstant.BUNDLE_TITLE, title)
                putString(BundleConstant.BUNDLE_BUTTON, button)
                putString(BundleConstant.BUNDLE_MESSAGE, message)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            title = args.getString(BundleConstant.BUNDLE_TITLE)
            button = args.getString(BundleConstant.BUNDLE_BUTTON)
            message = args.getString(BundleConstant.BUNDLE_MESSAGE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = requireActivity().getSharedPreferences("data_user", Context.MODE_PRIVATE)
        val totalLaundry = sharedPreferences.getString("totalLaundry", "0")
        val totalPaid = sharedPreferences.getString("totalPaid", "0")
        val totalDone = sharedPreferences.getString("totalDone", "0")
        val totalTaken = sharedPreferences.getString("totalTaken", "0")
        val token = sharedPreferences.getString("token", null)

        with(binding) {
            tvLaundry.text = totalLaundry.toString()
            tvLaundryPaid.text = totalPaid.toString()
            tvLaundryDone.text = totalDone.toString()
            tvLaundryTaken.text = totalTaken.toString()

            lifecycleScope.launch {
                try {
                    // Call the getAllTransaction method
                    val response = listTransaction.getAllTransaction("Bearer $token", page, sort, sortBy, filterBy, value, size)

                    if (response.isSuccessful && response.body() != null) {
                        val transactionListResponse = response.body()?.data?.content
                        println("Data transaksi : ${transactionListResponse}")
                        if(transactionListResponse?.isEmpty() == false) {
                            updateRecyclerView(binding.rvLaundry, transactionListResponse)
                        }

                        // Assuming you have a method in your adapter to update the data
//                        adapter?.updateData(transactionList ?: listOf())
                    } else {
                        // Handle the case when the response is not successful or the body is null
                        println("Error: ${response}")
                    }
                } catch (e: Exception) {
                    // Handle any exceptions here
                    println("Error: ${e.message}")
//                e.printStackTrace()
                }
            }

//            if (adapter == null) adapter = LaundryAdapter(
//                data = chatList(view.context),
//                onLongClick = { position ->
//                    showAlertDialog(position)
//                }
//            )
//            listChat.apply {
//                setHasFixedSize(true)
//                layoutManager = LinearLayoutManager(context)
//                adapter = this@ChatFragment.adapter
//            }
//
//            buttonAdd.apply {
//                isVisible = title?.contains("Chat") ?: false
//                setOnClickListener {
//                    showFormsChat()
//                }
//            }
        }
    }

//    private fun showFormsChat() {
//        ChatDialogFragment.newInstance (actionSubmit = { data ->
//            adapter?.addNewChat(data)
//        }).show(childFragmentManager, ChatDialogFragment::class.java.simpleName)
//    }

    private fun updateRecyclerView(layout: RecyclerView, transaksi: List<TransactionResponse>) {
        val adapter = binding.rvLaundry.adapter as LaundryAdapter
        val newLaundryItems = transaksi.map { transaksiResponse ->
            LaundryItem(
                transaksiResponse.transactionUUID!!,

//                "${bookingDTO.tanggal.dayOfMonth} " +
//                        "${bookingDTO.tanggal.month.name.lowercase().replaceFirstChar { it.uppercase() }} " +
//                        "${bookingDTO.tanggal.year}",
//
//                "${bookingDTO.jamMulaiBooking.hour}:00 - " +
//                        "${bookingDTO.jamSelesaiBooking.hour}:00"
            )
        }
        adapter.addItems(newLaundryItems!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}