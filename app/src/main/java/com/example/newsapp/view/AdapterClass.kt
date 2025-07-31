package com.example.newsapp

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.model.CardData
import org.w3c.dom.Text


class CardAdapter(private val dataList: MutableList<CardData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    private var isLoadingAdded = false


    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.textTitle)
        val authorText: TextView = itemView.findViewById(R.id.authorText)
        val commentText: TextView = itemView.findViewById(R.id.commentText)
        val removebtn: TextView = itemView.findViewById(R.id.removeBtn)
        val infobtn: TextView = itemView.findViewById(R.id.infobtn)
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val progressBar: ProgressBar = itemView.findViewById(R.id.ProgressBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_card, parent, false)
            CardViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CardViewHolder) {
            val item = dataList[position]
            holder.titleText.text = item.title ?: "No title"
            holder.authorText.text = "Author: ${item.author ?: "Unknown"}"
            holder.commentText.text = "Comments: ${item.num_comments ?: 0}"
            holder.removebtn.setOnClickListener {
                removeItem(holder.adapterPosition)
            }

            holder.infobtn.setOnClickListener {           //this is for to open the website in browser
                val url = item.url
                if (!url.isEmpty()){
                    val context = holder.itemView.context
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                }
            }

        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoadingAdded && position == dataList.size) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_ITEM
        }
    }
    fun removeItem(position: Int){           //use for to delete the card from list
        if (position >= 0&& position< dataList.size){
            dataList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,dataList.size)
        }
    }

    override fun getItemCount(): Int = dataList.size + if (isLoadingAdded) 1 else 0

    fun showLoading() {
        isLoadingAdded = true
        notifyItemInserted(dataList.size)
    }

    fun hideLoading() {
        isLoadingAdded = false
        notifyItemRemoved(dataList.size)
    }

    fun addAll(newItems: List<CardData>) {
        val startPosition = dataList.size
        dataList.addAll(newItems)

        Log.d("tag", "addAll: ${dataList}")
        notifyItemRangeInserted(startPosition, newItems.size)
    }


}