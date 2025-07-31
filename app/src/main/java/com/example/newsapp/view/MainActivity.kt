package com.example.newsapp.view

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.CardAdapter
import com.example.newsapp.R
import com.example.newsapp.model.CardData
import com.example.newsapp.model.HitsResponse
import com.example.newsapp.model.RetrofitInstance
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var cardAdapter: CardAdapter
    private lateinit var searchBar: EditText
    private lateinit var searchButton: Button

    private var currentPage = 0
    private var isLoading = false
    private var isLastPage = false
    private val dataList = mutableListOf<CardData>()
    private var currentQuery = "react"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchBar = findViewById(R.id.searchbar)
        searchButton = findViewById(R.id.searchbtn)
        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        cardAdapter = CardAdapter(dataList)
        recyclerView.adapter = cardAdapter

        searchButton.setOnClickListener {
            val query = searchBar.text.toString().trim()
            if (query.isNotEmpty()) {
                currentQuery = query
                currentPage = 0
                isLastPage = false
                dataList.clear()
                cardAdapter.notifyDataSetChanged()
                fetchNews(currentQuery)
            } else {
                Toast.makeText(this, "Enter something to search", Toast.LENGTH_SHORT).show()
            }
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (!isLoading && !isLastPage) {
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                        firstVisibleItemPosition >= 0) {
                        fetchNews(currentQuery)
                    }
                }
            }
        })

        fetchNews(currentQuery)
    }

    private fun fetchNews(query: String) {
        if (isLoading || isLastPage) return

        isLoading = true
        cardAdapter.showLoading()

        lifecycleScope.launch {
            try {
                val response: HitsResponse = RetrofitInstance.modelClass.getNews(
                    query = query,
                    page = currentPage,
                    hitsPerPage = 10
                )

                val newItems = response.hits

                if (newItems.isEmpty()) {
                    isLastPage = true
                } else {
                    cardAdapter.addAll(newItems)
                    currentPage++
                }

            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("NewsApp", "fetchNews error", e)
            } finally {
                isLoading = false
                cardAdapter.hideLoading()
            }
        }
    }
}

