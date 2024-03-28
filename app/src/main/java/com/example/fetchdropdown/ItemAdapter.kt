package com.example.fetchdropdown

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adapter class for the RecyclerView to display items
class ItemAdapter(private val items: List<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    // Inner class representing the ViewHolder for each item
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.itemNameId)
    }

    // Called when RecyclerView needs a new ViewHolder for an item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_name, parent, false)
        return ItemViewHolder(view)
    }

    // Called by RecyclerView to display data at a specific position
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = items[position]
        holder.textViewName.text = currentItem.name
    }

    // Returns the total number of items in the dataset
    override fun getItemCount() = items.size
}
