package com.example.mailapp.ui.fragments

import android.content.Context
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mailapp.R
import com.example.mailapp.databinding.FragmentMailListBinding
import com.example.mailapp.model.MailModel
import com.example.mailapp.toVisible
import com.example.mailapp.ui.adapters.MailListAdapter
import com.example.mailapp.util.showToast
import com.example.mailapp.viewmodels.MailListViewModel

class MailListFragment: BaseFragment<FragmentMailListBinding, MailListViewModel>() {
    override val layoutResId: Int
        get() = R.layout.fragment_mail_list
    override val viewModel: MailListViewModel by activityViewModels()

    private val mailListAdapter = MailListAdapter()
    private var mailSelectActivity: MailTypeSelectActivity? = null

    private val onBackPressedCallback = object: OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            Log.d("TAG", "onBackPressed at mail list fragment")
            viewModel.onBackPressed()
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MailTypeSelectActivity)
            mailSelectActivity = context
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onDetach() {
        super.onDetach()
        mailSelectActivity = null
        onBackPressedCallback.remove()
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchMailList()
    }

    override fun initData() {

    }

    override fun initView(view: View) {
        setRecyclerView()
    }

    private fun setRecyclerView(){
        vd.recyclerView.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mailListAdapter
        }
    }

    override fun bind() {
        viewModel.progress.observe(this){
            vd.progress.visibility = it.toVisible()
        }

        viewModel.finishViewEvent.observe(this){ event ->
            event.getContentIfNotHandled()?.let {
                if(it.isNotBlank())
                    showToast(it)
                requireActivity().finish()
            }
        }

        viewModel.mailTypeData.observe(this){
            vd.tvType.text = it.toString()
        }

        viewModel.mailList.observe(this){
            mailListAdapter.setList(it)
            mailListAdapter.notifyDataSetChanged()
        }

        viewModel.changeMailTypeEvent.observe(this){ event ->
            event.getContentIfNotHandled()?.let {
                mailSelectActivity?.selectMailType(it)
            }
        }

        mailSelectActivity?.mailType?.observe(this){
            viewModel.changeMailType(it)
        }
    }

}