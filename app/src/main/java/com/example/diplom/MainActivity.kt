package com.example.diplom

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.diplom.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
        val adapter = DayPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = days[position]
        }.attach()

        highlightCurrentDay(days)

        binding.btnViewRatings.setOnClickListener {
            val intent = Intent(this, RatingsActivity::class.java)
            startActivity(intent)
        }
    }

    private inner class DayPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = 6

        override fun createFragment(position: Int): Fragment {
            val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
            return DayFragment.newInstance(days[position])
        }
    }

    private fun highlightCurrentDay(days: List<String>) {
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val todayIndex = when (dayOfWeek) {
            Calendar.MONDAY -> 0
            Calendar.TUESDAY -> 1
            Calendar.WEDNESDAY -> 2
            Calendar.THURSDAY -> 3
            Calendar.FRIDAY -> 4
            Calendar.SATURDAY -> 5
            else -> 0 // Default to Monday if it's Sunday
        }
        binding.tabLayout.getTabAt(todayIndex)?.select()
    }
}
