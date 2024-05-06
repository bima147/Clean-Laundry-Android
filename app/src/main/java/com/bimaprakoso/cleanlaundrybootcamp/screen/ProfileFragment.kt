package com.bimaprakoso.cleanlaundrybootcamp.screen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bimaprakoso.cleanlaundrybootcamp.LoginActivity
import com.bimaprakoso.cleanlaundrybootcamp.R
import com.bimaprakoso.cleanlaundrybootcamp.adapter.LaundryAdapter
import com.bimaprakoso.cleanlaundrybootcamp.databinding.FragmentProfileBinding
import com.bimaprakoso.cleanlaundrybootcamp.utils.BundleConstant
import android.content.Context
import androidx.lifecycle.lifecycleScope
import com.bimaprakoso.cleanlaundrybootcamp.base.retrofit.InstanceRetro
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private var title: String? = null
    private var button: String? = null
    private var message: String? = null

    private var adapter: LaundryAdapter? = null

    companion object {
        @JvmStatic
        fun newInstance(title: String, button: String, message: String) = ProfileFragment().apply {
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
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = requireActivity().getSharedPreferences("data_user", Context.MODE_PRIVATE)
        val namaLengkap = sharedPreferences.getString("nama", "Nama Lengkap")

        with(binding) {
            tvNama.text = namaLengkap.toString()
//            if (adapter == null) adapter = LaundryAdapter(
//                items = chatList(view.context),
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
            btnLogout.apply {
                setOnClickListener {
                    doLogout()
                }
            }
        }
    }

//    private fun showFormsChat() {
//        ChatDialogFragment.newInstance (actionSubmit = { data ->
//            adapter?.addNewChat(data)
//        }).show(childFragmentManager, ChatDialogFragment::class.java.simpleName)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun doLogout() {
        val sharedPreferences = requireActivity().getSharedPreferences("data_user", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
        sharedPreferences.edit().remove("token").apply()
        val intent = Intent(activity,LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}