package com.example.diplom

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.diplom.databinding.FragmentDayBinding
import kotlinx.coroutines.launch

class DayFragment : Fragment() {

    private lateinit var binding: FragmentDayBinding
    private lateinit var db: AppDatabase
    private lateinit var scheduleDao: ScheduleDao

    companion object {
        private const val ARG_DAY = "day"

        fun newInstance(day: String): DayFragment {
            val fragment = DayFragment()
            val args = Bundle()
            args.putString(ARG_DAY, day)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDayBinding.inflate(inflater, container, false)
        db = AppDatabase.getDatabase(requireContext())
        scheduleDao = db.scheduleDao()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val day = arguments?.getString(ARG_DAY) ?: return

        highlightCurrentDay(day)
        loadSchedule(day)

        binding.btnSave.setOnClickListener {
            saveSchedule(day)
        }
    }

    private fun saveSchedule(day: String) {
        val scheduleItems = listOf(
            ScheduleItem(day = day, timeSlot = "8:00-9:35", subject = binding.subject1.text.toString(), room = binding.room1.text.toString()),
            ScheduleItem(day = day, timeSlot = "9:45-11:20", subject = binding.subject2.text.toString(), room = binding.room2.text.toString()),
            ScheduleItem(day = day, timeSlot = "11:50-13:25", subject = binding.subject3.text.toString(), room = binding.room3.text.toString()),
            ScheduleItem(day = day, timeSlot = "13:35-15:10", subject = binding.subject4.text.toString(), room = binding.room4.text.toString()),
            ScheduleItem(day = day, timeSlot = "15:20-16:55", subject = binding.subject5.text.toString(), room = binding.room5.text.toString()),
            ScheduleItem(day = day, timeSlot = "17:05-18:40", subject = binding.subject6.text.toString(), room = binding.room6.text.toString()),
            ScheduleItem(day = day, timeSlot = "18:50-20:15", subject = binding.subject7.text.toString(), room = binding.room7.text.toString())
        )

        lifecycleScope.launch {
            scheduleItems.forEach { scheduleDao.insertSchedule(it) }
        }
    }

    private fun loadSchedule(day: String) {
        lifecycleScope.launch {
            val scheduleItems = scheduleDao.getScheduleForDay(day)

            scheduleItems.forEach {
                when (it.timeSlot) {
                    "8:00-9:35" -> {
                        binding.subject1.setText(it.subject)
                        binding.room1.setText(it.room)
                    }
                    "9:45-11:20" -> {
                        binding.subject2.setText(it.subject)
                        binding.room2.setText(it.room)
                    }
                    "11:50-13:25" -> {
                        binding.subject3.setText(it.subject)
                        binding.room3.setText(it.room)
                    }
                    "13:35-15:10" -> {
                        binding.subject4.setText(it.subject)
                        binding.room4.setText(it.room)
                    }
                    "15:20-16:55" -> {
                        binding.subject5.setText(it.subject)
                        binding.room5.setText(it.room)
                    }
                    "17:05-18:40" -> {
                        binding.subject6.setText(it.subject)
                        binding.room6.setText(it.room)
                    }
                    "18:50-20:15" -> {
                        binding.subject7.setText(it.subject)
                        binding.room7.setText(it.room)
                    }
                }
            }
        }
    }

    private fun highlightCurrentDay(day: String) {
        val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
        val textViews = listOf(
            binding.dayMonday, binding.dayTuesday, binding.dayWednesday,
            binding.dayThursday, binding.dayFriday, binding.daySaturday
        )

        days.forEachIndexed { index, d ->
            if (d.equals(day, true)) {
                textViews[index].isSelected = true
            } else {
                textViews[index].isSelected = false
            }
        }
    }
}
