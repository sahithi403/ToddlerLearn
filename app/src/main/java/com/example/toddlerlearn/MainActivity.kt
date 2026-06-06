package com.example.toddlerlearn

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.toddlerlearn.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tts: TextToSpeech
    private var ttsReady = false

    // Card background colors (cycling through them)
    private val cardColors by lazy {
        listOf(
            R.color.card_1, R.color.card_2, R.color.card_3,
            R.color.card_4, R.color.card_5, R.color.card_6,
            R.color.card_7, R.color.card_8, R.color.card_9
        ).map { ContextCompat.getColor(this, it) }
    }

    // ── Data ─────────────────────────────────────────────────────────────────

    private val animals = listOf(
        "🐶" to "Dog",   "🐱" to "Cat",    "🐮" to "Cow",
        "🐷" to "Pig",   "🐸" to "Frog",   "🦁" to "Lion",
        "🐘" to "Elephant", "🐧" to "Penguin", "🦊" to "Fox",
        "🐰" to "Rabbit", "🐻" to "Bear",  "🦋" to "Butterfly"
    )

    private val colors = listOf(
        "🔴" to "Red",   "🟠" to "Orange", "🟡" to "Yellow",
        "🟢" to "Green", "🔵" to "Blue",   "🟣" to "Purple",
        "⚫" to "Black", "⚪" to "White",   "🟤" to "Brown",
        "🩷" to "Pink",  "🩵" to "Light Blue", "🟩" to "Dark Green"
    )

    private val shapes = listOf(
        "⭕" to "Circle",   "🔷" to "Diamond",  "🔺" to "Triangle",
        "⬛" to "Square",   "🔶" to "Hexagon",  "⭐" to "Star",
        "❤️" to "Heart",   "🔘" to "Oval",     "📐" to "Rectangle",
        "🌙" to "Crescent", "💠" to "Crystal",  "🔣" to "Cross"
    )

    // ─────────────────────────────────────────────────────────────────────────

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tts = TextToSpeech(this, this)

        // Default to Animals
        loadCategory(animals)

        binding.btnAnimals.setOnClickListener { loadCategory(animals) }
        binding.btnColors.setOnClickListener  { loadCategory(colors)  }
        binding.btnShapes.setOnClickListener  { loadCategory(shapes)  }
    }

    private fun loadCategory(data: List<Pair<String, String>>) {
        val items = data.mapIndexed { index, (emoji, label) ->
            LearnItem(emoji, label, cardColors[index % cardColors.size])
        }

        val adapter = LearnItemAdapter(items) { item ->
            speak(item.label)
        }

        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerView.adapter = adapter
    }

    private fun speak(text: String) {
        if (ttsReady) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        } else {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale.US
            ttsReady = true
        }
    }

    override fun onDestroy() {
        tts.shutdown()
        super.onDestroy()
    }
}
