package com.example.diplom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.example.diplom.databinding.ActivityRatingsBinding

class RatingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRatingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRatingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example data for ratings
        val ratings = listOf(
            "Math - 95",
            "English - 85",
            "Science - 90"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ratings)
        binding.listRatings.adapter = adapter
    }
}
