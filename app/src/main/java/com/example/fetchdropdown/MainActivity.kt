package com.example.fetchdropdown

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import kotlin.text.toIntOrNull

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var listIdRecyclerView: RecyclerView
    private lateinit var instructionsTextView: TextView
    private var itemList = mutableListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize RecyclerViews and TextView
        recyclerView = findViewById(R.id.recyclerItemView)
        listIdRecyclerView = findViewById(R.id.recyclerIdView)
        instructionsTextView = findViewById(R.id.instructionsTextView)
        fetchDataFromUrl()
    }

    // Function to fetch JSON data from URL
    private fun fetchDataFromUrl() {
        val url = "https://fetch-hiring.s3.amazonaws.com/hiring.json"

        // Use coroutines for network operation in a background thread
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val inputStream = URL(url).openStream()
                val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                val stringBuilder = StringBuilder()
                var line: String?
                while (bufferedReader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }
                bufferedReader.close()

                val jsonArray = JSONArray(stringBuilder.toString())
                parseJson(jsonArray)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun parseJson(jsonArray: JSONArray) {
        itemList.clear()

        // Loop through JSON array to extract items
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val id = jsonObject.getInt("id")
            val listId = jsonObject.getInt("listId")
            val name = jsonObject.getString("name")
            // Check for null or blank names before adding to the list
            if (!jsonObject.isNull("name") && !name.isNullOrBlank()) {
                itemList.add(Item(id, listId, name))
            }
        }
        itemList.sortBy { it.listId } // Sort items by listId

        // Update UI on the main thread
        runOnUiThread {
            displayListId()
        }
    }

    // Function to extract numbers from a string
    private fun extractNumber(name: String): Int {
        val numberRegex = "\\d+".toRegex()
        val matchResult = numberRegex.find(name)
        return matchResult?.value?.toIntOrNull() ?: 0
    }

    private fun displayListId() {
        var selectedPosition = -1 // Track selected item position
        val groupedItems = itemList.groupBy { it.listId } // Group items by listId

        // Set up ListIdAdapter for listIdRecyclerView
        val listIdAdapter = ListIdAdapter(groupedItems.keys.toList())
        listIdRecyclerView.adapter = listIdAdapter
        listIdRecyclerView.layoutManager = LinearLayoutManager(this)

        // Set item click listener for listId items
        listIdAdapter.setOnItemClickListener { position ->
            val selectedListId = groupedItems.keys.toList()[position]

            // Change the background color of the previously selected item back to default
            if (selectedPosition != -1) {
                val prevView = listIdRecyclerView.findViewHolderForAdapterPosition(selectedPosition)
                prevView?.itemView?.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            }

            // Change the background color of the clicked item
            val currentView = listIdRecyclerView.findViewHolderForAdapterPosition(position)
            currentView?.itemView?.setBackgroundColor(ContextCompat.getColor(this, R.color.listView_clicked))
            selectedPosition = position // Update the selected position

            displayItems(groupedItems[selectedListId] ?: emptyList())
        }
    }

    private fun displayItems(selectedList: List<Item>) {

        // sort the names according to the integer present in the string. Eg Item 2 comes before Item 11
        val nameComparator = Comparator<Item> { item1, item2 ->
            val number1 = extractNumber(item1.name)
            val number2 = extractNumber(item2.name)
            number1.compareTo(number2)
        }

        val sortedGroupedItems = selectedList.sortedWith(nameComparator)

        val adapter = ItemAdapter(sortedGroupedItems)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}

// Data class to represent an item
data class Item(val id: Int, val listId: Int, val name: String)
