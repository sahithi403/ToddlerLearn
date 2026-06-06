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

    private val people = listOf(
        Triple("👶", "పిల్లవాడు", R.drawable.emoji_people_baby),
        Triple("👧", "అమ్మాయి",   R.drawable.emoji_people_girl),
        Triple("👦", "అబ్బాయి",   R.drawable.emoji_people_boy),
        Triple("👩", "అమ్మ",      R.drawable.emoji_people_mother),
        Triple("👨", "నాన్న",     R.drawable.emoji_people_father),
        Triple("👵", "నాయనమ్మ",  R.drawable.emoji_people_grandmother),
        Triple("👴", "తాతయ్య",   R.drawable.emoji_people_grandfather),
        Triple("👩‍⚕️", "డాక్టర్", R.drawable.emoji_people_doctor),
        Triple("👨‍🏫", "టీచర్",   R.drawable.emoji_people_teacher),
        Triple("👩‍🍳", "వంటవాడు", R.drawable.emoji_people_cook),
        Triple("👮", "పోలీసు",   R.drawable.emoji_people_police),
        Triple("👩‍🌾", "రైతు",    R.drawable.emoji_people_farmer),
    )

    private val objects = listOf(
        Triple("🏠", "ఇల్లు",     R.drawable.emoji_objects_house),
        Triple("🚗", "కారు",      R.drawable.emoji_objects_car),
        Triple("📚", "పుస్తకం",   R.drawable.emoji_objects_book),
        Triple("✏️", "పెన్సిల్", R.drawable.emoji_objects_pencil),
        Triple("🎒", "బ్యాగు",   R.drawable.emoji_objects_bag),
        Triple("⚽", "బంతి",      R.drawable.emoji_objects_ball),
        Triple("🍎", "ఆపిల్",    R.drawable.emoji_objects_apple),
        Triple("🌸", "పువ్వు",   R.drawable.emoji_objects_flower),
        Triple("☀️", "సూర్యుడు", R.drawable.emoji_objects_sun),
        Triple("🌙", "చంద్రుడు", R.drawable.emoji_objects_moon),
        Triple("⭐", "నక్షత్రం", R.drawable.emoji_objects_star),
        Triple("🌧️", "వర్షం",   R.drawable.emoji_objects_rain),
    )

    private val animals = listOf(
        Triple("🐶", "కుక్క",          R.drawable.emoji_animals_dog),
        Triple("🐱", "పిల్లి",         R.drawable.emoji_animals_cat),
        Triple("🐮", "ఆవు",            R.drawable.emoji_animals_cow),
        Triple("🐷", "పంది",           R.drawable.emoji_animals_pig),
        Triple("🐸", "కప్ప",           R.drawable.emoji_animals_frog),
        Triple("🦁", "సింహం",          R.drawable.emoji_animals_lion),
        Triple("🐘", "ఏనుగు",          R.drawable.emoji_animals_elephant),
        Triple("🐧", "పెంగ్విన్",      R.drawable.emoji_animals_penguin),
        Triple("🦊", "నక్క",           R.drawable.emoji_animals_fox),
        Triple("🐰", "కుందేలు",        R.drawable.emoji_animals_rabbit),
        Triple("🐻", "భల్లూకం",        R.drawable.emoji_animals_bear),
        Triple("🦋", "సీతాకోకచిలుక",  R.drawable.emoji_animals_butterfly),
    )

    private val colors = listOf(
        Triple("🔴", "ఎరుపు",      R.drawable.emoji_colors_red),
        Triple("🟠", "నారింజ",     R.drawable.emoji_colors_orange),
        Triple("🟡", "పసుపు",      R.drawable.emoji_colors_yellow),
        Triple("🟢", "ఆకుపచ్చ",   R.drawable.emoji_colors_green),
        Triple("🔵", "నీలం",       R.drawable.emoji_colors_blue),
        Triple("🟣", "ఊదా",        R.drawable.emoji_colors_purple),
        Triple("⚫", "నలుపు",      R.drawable.emoji_colors_black),
        Triple("⚪", "తెలుపు",     R.drawable.emoji_colors_white),
        Triple("🟤", "గోధుమ",      R.drawable.emoji_colors_brown),
        Triple("🩷", "గులాబీ",     R.drawable.emoji_colors_pink),
        Triple("🩵", "లేత నీలం",  R.drawable.emoji_colors_light_blue),
        Triple("🟩", "చీకటి పచ్చ", R.drawable.emoji_colors_dark_green),
    )

    private val shapes = listOf(
        Triple("⭕", "వృత్తం",         R.drawable.emoji_shapes_circle),
        Triple("🔷", "వజ్రం",          R.drawable.emoji_shapes_diamond),
        Triple("🔺", "త్రిభుజం",       R.drawable.emoji_shapes_triangle),
        Triple("⬛", "చతురస్రం",       R.drawable.emoji_shapes_square),
        Triple("🔶", "షట్భుజి",        R.drawable.emoji_shapes_hexagon),
        Triple("⭐", "నక్షత్రం",       R.drawable.emoji_shapes_star),
        Triple("❤️", "గుండె",         R.drawable.emoji_shapes_heart),
        Triple("🔘", "అండాకారం",      R.drawable.emoji_shapes_oval),
        Triple("📐", "దీర్ఘచతురస్రం", R.drawable.emoji_shapes_rectangle),
        Triple("🌙", "చంద్రవంక",      R.drawable.emoji_shapes_crescent),
        Triple("💠", "స్ఫటికం",        R.drawable.emoji_shapes_crystal),
        Triple("🔣", "సిలువ",          R.drawable.emoji_shapes_cross),
    )

    // ─────────────────────────────────────────────────────────────────────────

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tts = TextToSpeech(this, this)

        // Default to People
        loadCategory(people)

        binding.btnPeople.setOnClickListener  { loadCategory(people)   }
        binding.btnObjects.setOnClickListener { loadCategory(objects)  }
        binding.btnAnimals.setOnClickListener { loadCategory(animals)  }
        binding.btnColors.setOnClickListener  { loadCategory(colors)   }
        binding.btnShapes.setOnClickListener  { loadCategory(shapes)   }
    }

    private fun loadCategory(data: List<Triple<String, String, Int>>) {
        val items = data.mapIndexed { index, (emoji, label, resId) ->
            LearnItem(emoji, label, cardColors[index % cardColors.size], resId)
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
            val telugu = Locale("te", "IN")
            val result = tts.setLanguage(telugu)
            ttsReady = result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED
        }
    }

    override fun onDestroy() {
        tts.shutdown()
        super.onDestroy()
    }
}
