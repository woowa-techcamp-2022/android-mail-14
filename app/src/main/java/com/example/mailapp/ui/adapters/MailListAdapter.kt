package com.example.mailapp.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mailapp.R
import com.example.mailapp.databinding.ItemViewMailListBinding
import com.example.mailapp.model.MailModel
import com.example.mailapp.toVisible

class MailListAdapter(): RecyclerView.Adapter<MailListAdapter.MailListItemViewHolder>() {

    private val mails: ArrayList<MailModel> = ArrayList()
    fun setList(mails: List<MailModel>){
        this.mails.clear()
        this.mails.addAll(mails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MailListItemViewHolder {
        return MailListItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MailListItemViewHolder, position: Int) {
        holder.bind(mails[position])
    }

    override fun getItemCount(): Int {
        return mails.size
    }

    class MailListItemViewHolder(
        private val vd: ItemViewMailListBinding
    ): RecyclerView.ViewHolder(vd.root){
        companion object {
            fun from(parent: ViewGroup): MailListItemViewHolder{
                return MailListItemViewHolder(
                    ItemViewMailListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
        fun bind(item: MailModel){
            vd.tvSender.text = item.senderId
            vd.tvTitle.text = item.title
            vd.tvContent.text = item.content
            vd.tvSenderChar.visibility = (!item.printId.isNullOrBlank()).toVisible()
            if(!item.printId.isNullOrBlank()) {
                vd.tvSenderChar.text = item.printId
//                vd.ivProfile.setBackgroundResource(0) // not work
                vd.ivProfile.setImageResource(0) // for remove background resource
                vd.ivProfile.setBackgroundColor(Color.parseColor(item.profileColor))
            }else{
                vd.ivProfile.setBackgroundResource(item.defaultProfileImageResId)
            }
        }
    }
}