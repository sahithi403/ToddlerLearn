package com.example.toddlerlearn

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.toddlerlearn.databinding.ActivityMainBinding
import java.util.Locale

class FlashCardsActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

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
        Triple("Baby",        "పిల్లవాడు", R.drawable.emoji_people_baby),
        Triple("Girl",        "అమ్మాయి",   R.drawable.emoji_people_girl),
        Triple("Boy",         "అబ్బాయి",   R.drawable.emoji_people_boy),
        Triple("Mother",      "అమ్మ",      R.drawable.emoji_people_mother),
        Triple("Father",      "నాన్న",     R.drawable.emoji_people_father),
        Triple("Grandmother", "నాయనమ్మ",  R.drawable.emoji_people_grandmother),
        Triple("Grandfather", "తాతయ్య",   R.drawable.emoji_people_grandfather),
        Triple("Doctor",      "డాక్టర్",  R.drawable.emoji_people_doctor),
        Triple("Teacher",     "టీచర్",    R.drawable.emoji_people_teacher),
        Triple("Cook",        "వంటవాడు",  R.drawable.emoji_people_cook),
        Triple("Police",      "పోలీసు",   R.drawable.emoji_people_police),
        Triple("Farmer",      "రైతు",     R.drawable.emoji_people_farmer),
    )

    private val objects = listOf(
        Triple("House",   "ఇల్లు",     R.drawable.emoji_objects_house),
        Triple("Car",     "కారు",      R.drawable.emoji_objects_car),
        Triple("Book",    "పుస్తకం",   R.drawable.emoji_objects_book),
        Triple("Pencil",  "పెన్సిల్", R.drawable.emoji_objects_pencil),
        Triple("Bag",     "బ్యాగు",   R.drawable.emoji_objects_bag),
        Triple("Ball",    "బంతి",      R.drawable.emoji_objects_ball),
        Triple("Apple",   "ఆపిల్",    R.drawable.emoji_objects_apple),
        Triple("Flower",  "పువ్వు",   R.drawable.emoji_objects_flower),
        Triple("Sun",     "సూర్యుడు", R.drawable.emoji_objects_sun),
        Triple("Moon",    "చంద్రుడు", R.drawable.emoji_objects_moon),
        Triple("Star",    "నక్షత్రం", R.drawable.emoji_objects_star),
        Triple("Rain",    "వర్షం",    R.drawable.emoji_objects_rain),
    )

    private val animals = listOf(
        Triple("Dog",       "కుక్క",         R.drawable.emoji_animals_dog),
        Triple("Cat",       "పిల్లి",        R.drawable.emoji_animals_cat),
        Triple("Cow",       "ఆవు",           R.drawable.emoji_animals_cow),
        Triple("Pig",       "పంది",          R.drawable.emoji_animals_pig),
        Triple("Frog",      "కప్ప",          R.drawable.emoji_animals_frog),
        Triple("Lion",      "సింహం",         R.drawable.emoji_animals_lion),
        Triple("Elephant",  "ఏనుగు",         R.drawable.emoji_animals_elephant),
        Triple("Penguin",   "పెంగ్విన్",     R.drawable.emoji_animals_penguin),
        Triple("Fox",       "నక్క",          R.drawable.emoji_animals_fox),
        Triple("Rabbit",    "కుందేలు",       R.drawable.emoji_animals_bunty),
        Triple("Bear",      "ఎలుగుబంటి",     R.drawable.emoji_animals_bear),
        Triple("Butterfly", "సీతాకోకచిలుక", R.drawable.emoji_animals_butterfly),
    )

    private val colors = listOf(
        Triple("Red",        "ఎరుపు",       R.drawable.emoji_colors_red),
        Triple("Orange",     "నారింజ",      R.drawable.emoji_colors_orange),
        Triple("Yellow",     "పసుపు",       R.drawable.emoji_colors_yellow),
        Triple("Green",      "ఆకుపచ్చ",    R.drawable.emoji_colors_green),
        Triple("Blue",       "నీలం",        R.drawable.emoji_colors_blue),
        Triple("Purple",     "ఊదా",         R.drawable.emoji_colors_purple),
        Triple("Black",      "నలుపు",       R.drawable.emoji_colors_black),
        Triple("White",      "తెలుపు",      R.drawable.emoji_colors_white),
        Triple("Brown",      "గోధుమ",       R.drawable.emoji_colors_brown),
        Triple("Pink",       "గులాబీ",      R.drawable.emoji_colors_pink),
        Triple("Light Blue", "లేత నీలం",   R.drawable.emoji_colors_light_blue),
        Triple("Dark Green", "చీకటి పచ్చ", R.drawable.emoji_colors_dark_green),
    )

    private val shapes = listOf(
        Triple("Circle",    "వృత్తం",         R.drawable.emoji_shapes_circle),
        Triple("Diamond",   "వజ్రం",          R.drawable.emoji_shapes_diamond),
        Triple("Triangle",  "త్రిభుజం",       R.drawable.emoji_shapes_triangle),
        Triple("Square",    "చతురస్రం",       R.drawable.emoji_shapes_square),
        Triple("Hexagon",   "షట్భుజి",        R.drawable.emoji_shapes_hexagon),
        Triple("Star",      "నక్షత్రం",       R.drawable.emoji_shapes_star),
        Triple("Heart",     "గుండె",          R.drawable.emoji_shapes_heart),
        Triple("Oval",      "అండాకారం",       R.drawable.emoji_shapes_oval),
        Triple("Rectangle", "దీర్ఘచతురస్రం", R.drawable.emoji_shapes_rectangle),
        Triple("Crescent",  "చంద్రవంక",       R.drawable.emoji_shapes_crescent),
        Triple("Crystal",   "స్ఫటికం",        R.drawable.emoji_shapes_crystal),
        Triple("Cross",     "సిలువ",          R.drawable.emoji_shapes_cross),
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
        val items = data.mapIndexed { index, (english, telugu, resId) ->
            LearnItem(english, telugu, cardColors[index % cardColors.size], resId)
        }

        val adapter = LearnItemAdapter(items) { item ->
            speak(item.teluguLabel)
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
