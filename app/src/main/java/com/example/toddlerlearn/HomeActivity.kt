package com.example.toddlerlearn

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.toddlerlearn.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardFlashCards.setOnClickListener {
            startActivity(Intent(this, FlashCardsActivity::class.java))
        }

        binding.cardLessonPlan.setOnClickListener {
            startActivity(Intent(this, LessonPlanActivity::class.java))
        }

        binding.cardStories.setOnClickListener {
            Toast.makeText(this, "Stories — Coming Soon! 📖", Toast.LENGTH_SHORT).show()
        }

        binding.cardGames.setOnClickListener {
            Toast.makeText(this, "Games — Coming Soon! 🎮", Toast.LENGTH_SHORT).show()
        }
    }
}
