package com.example.fetchdropdown

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adapter class for the RecyclerView to display listIds
class ListIdAdapter(private val listIds: List<Int>) : RecyclerView.Adapter<ListIdAdapter.ListIdViewHolder>() {
    private var onItemClick: ((Int) -> Unit)? = null

    // Inner class representing the ViewHolder for each listId item
    inner class ListIdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewListId: TextView = itemView.findViewById(R.id.textViewListId)

        init {  // Set click listener for the item view
            itemView.setOnClickListener {
                onItemClick?.invoke(adapterPosition)
            }
        }
    }

    // Called when RecyclerView needs a new ViewHolder for a listId item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListIdViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_listid, parent, false)
        return ListIdViewHolder(view)
    }

    // Called by RecyclerView to display data at a specific position
    override fun onBindViewHolder(holder: ListIdViewHolder, position: Int) {
        val currentListId = listIds[position]
        holder.textViewListId.text = "ListId: $currentListId"
    }

    // Returns the total number of listIds in the dataset
    override fun getItemCount() = listIds.size

    // Method to set the item click listener
    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClick = listener
    }
}
