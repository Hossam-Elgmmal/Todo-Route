package com.route.todo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.route.todo.databinding.ActivityHomeBinding
import com.route.todo.fragments.AddTaskFragment
import com.route.todo.fragments.SettingsFragment
import com.route.todo.fragments.TasksFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    val tasksFragment = TasksFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigate()

    }

    private fun navigate() {
        binding.addBtn.setOnClickListener {
            val bottomSheet = AddTaskFragment()
            bottomSheet.onTaskAddedListener = { date ->
                tasksFragment.tasksByDate(date)
            }
            bottomSheet.show(supportFragmentManager, null)
        }
        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.tasks -> {
                    pushFragment(tasksFragment)
                }

                R.id.settings -> {
                    pushFragment(SettingsFragment())
                }
            }
            return@setOnItemSelectedListener true
        }
        binding.navView.selectedItemId = R.id.tasks
    }

    private fun pushFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(binding.content.fragmentContainer.id, fragment)
            .commit()

    }
}