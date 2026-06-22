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

    private val family = listOf(
        Triple("Amma",     "అమ్మ",     R.drawable.emoji_family_amma),
        Triple("Nanna",    "నాన్న",    R.drawable.emoji_family_nanna),
        Triple("Papa",     "పాప",      R.drawable.emoji_family_papa),
        Triple("Babu",     "బాబు",     R.drawable.emoji_family_babu),
        Triple("Maternal Grandma", "అమ్మమ్మ", R.drawable.emoji_family_ammamma),
        Triple("Thatha",   "తాత",      R.drawable.emoji_family_tata),
        Triple("Paternal Grandma", "నాన్నమ్మ", R.drawable.emoji_family_nannamma),
        Triple("Thatha",   "తాత",      R.drawable.emoji_family_thatha),
        Triple("Aunt",     "పిన్ని",   R.drawable.emoji_family_pinni),
        Triple("Uncle",    "బాబాయి",   R.drawable.emoji_family_babai),
        Triple("Aunt",     "పెద్దమ్మ", R.drawable.emoji_family_peddamma),
        // Triple("Pedda Nanna", "పెద్దనాన్న", R.drawable.emoji_family_peddananna), // TODO: add image
    )

    private val objects = listOf(
        // Triple("Milk",   "పాలు",    R.drawable.emoji_objects_milk),   // TODO: add image
        // Triple("Water",  "నీళ్ళు",  R.drawable.emoji_objects_water),  // TODO: add image
        // Triple("Rice",   "అన్నం",   R.drawable.emoji_objects_rice),   // TODO: add image
        Triple("Ball",    "బంతి",      R.drawable.emoji_objects_ball),
        // Triple("Toy",    "బొమ్మ",   R.drawable.emoji_objects_toy),    // TODO: add image
        // Triple("Bed",    "మంచం",    R.drawable.emoji_objects_bed),    // TODO: add image
        // Triple("Spoon",  "చెంచా",   R.drawable.emoji_objects_spoon),  // TODO: add image
        // Triple("Shoe",   "చెప్పు",  R.drawable.emoji_objects_shoe),   // TODO: add image
        // Triple("Tree",   "చెట్టు",  R.drawable.emoji_objects_tree),   // TODO: add image
        Triple("Flower",  "పువ్వు",   R.drawable.emoji_objects_flower),
        Triple("House",   "ఇల్లు",    R.drawable.emoji_objects_house),
        // Triple("Cloud",  "మేఘం",    R.drawable.emoji_objects_cloud),  // TODO: add image
        Triple("Rain",    "వర్షం",    R.drawable.emoji_objects_rain),
        Triple("Sun",     "సూర్యుడు", R.drawable.emoji_objects_sun),
        Triple("Moon",    "చంద్రుడు", R.drawable.emoji_objects_moon),
        Triple("Star",    "నక్షత్రం", R.drawable.emoji_objects_star),
        Triple("Book",    "పుస్తకం",  R.drawable.emoji_objects_book),
    )

    private val animals = listOf(
        Triple("Cow",       "ఆవు",           R.drawable.emoji_animals_cow),
        // Triple("Buffalo", "గేదె",          R.drawable.emoji_animals_buffalo),   // TODO: add image
        // Triple("Bull",    "ఎద్దు",         R.drawable.emoji_animals_bull),      // TODO: add image
        // Triple("Goat",    "మేక",           R.drawable.emoji_animals_goat),      // TODO: add image
        // Triple("Sheep",   "గొర్రె",        R.drawable.emoji_animals_sheep),     // TODO: add image
        Triple("Cat",       "పిల్లి",        R.drawable.emoji_animals_cat),
        Triple("Dog",       "కుక్క",         R.drawable.emoji_animals_dog),
        Triple("Fox",       "నక్క",          R.drawable.emoji_animals_fox),
        // Triple("Horse",   "గుర్రం",        R.drawable.emoji_animals_horse),     // TODO: add image
        // Triple("Monkey",  "కోతి",          R.drawable.emoji_animals_monkey),    // TODO: add image
        // Triple("Deer",    "జింక",          R.drawable.emoji_animals_deer),      // TODO: add image
        // Triple("Fish",    "చేప",           R.drawable.emoji_animals_fish),      // TODO: add image
        // Triple("Snake",   "పాము",          R.drawable.emoji_animals_snake),     // TODO: add image
        Triple("Pig",       "పంది",          R.drawable.emoji_animals_pig),
        Triple("Frog",      "కప్ప",          R.drawable.emoji_animals_frog),
        Triple("Tiger",     "పులి",          R.drawable.emoji_animals_tiger),
        Triple("Lion",      "సింహం",         R.drawable.emoji_animals_lion),
        Triple("Elephant",  "ఏనుగు",         R.drawable.emoji_animals_elephant),
        Triple("Rabbit",    "కుందేలు",       R.drawable.emoji_animals_rabbit),
        Triple("Bear",      "ఎలుగుబంటి",     R.drawable.emoji_animals_bear),
        Triple("Butterfly", "సీతాకోకచిలుక", R.drawable.emoji_animals_butterfly),
    )

    //private val birds = listOf(
        // Triple("Crow",    "కాకి",   R.drawable.emoji_birds_crow),    // TODO: add image
        // Triple("Hen",     "కోడి",   R.drawable.emoji_birds_hen),     // TODO: add image
        // Triple("Duck",    "బాతు",   R.drawable.emoji_birds_duck),    // TODO: add image
        // Triple("Sparrow", "పిట్ట",  R.drawable.emoji_birds_sparrow), // TODO: add image
        // Triple("Eagle",   "గద్ద",   R.drawable.emoji_birds_eagle),   // TODO: add image
        // Triple("Swan",    "హంస",    R.drawable.emoji_birds_swan),    // TODO: add image
        // Triple("Crane",   "కొంగ",   R.drawable.emoji_birds_crane),   // TODO: add image
        // Triple("Parrot",  "చిలుక",  R.drawable.emoji_birds_parrot),  // TODO: add image
        // Triple("Peacock", "నెమలి",  R.drawable.emoji_birds_peacock), // TODO: add image
        // Triple("Cuckoo",  "కోయిల",  R.drawable.emoji_birds_cuckoo),  // TODO: add image
        // Triple("Pigeon",  "పావురం", R.drawable.emoji_birds_pigeon),  // TODO: add image
        // Triple("Owl",     "గుడ్లగూబ", R.drawable.emoji_birds_owl),   // TODO: add image
    //)

    //private val fruits = listOf(
        // Triple("Banana",       "అరటిపండు",    R.drawable.emoji_fruits_banana),      // TODO: add image
        // Triple("Watermelon",   "పుచ్చకాయ",    R.drawable.emoji_fruits_watermelon),  // TODO: add image
        // Triple("Mango",        "మామిడిపండు",  R.drawable.emoji_fruits_mango),       // TODO: add image
        // Triple("Grapes",       "ద్రాక్షపండ్లు", R.drawable.emoji_fruits_grapes),   // TODO: add image
        // Triple("Orange",       "నారింజపండు",  R.drawable.emoji_fruits_orange),      // TODO: add image
        // Triple("Guava",        "జామపండు",     R.drawable.emoji_fruits_guava),       // TODO: add image
        // Triple("Lemon",        "నిమ్మకాయ",    R.drawable.emoji_fruits_lemon),       // TODO: add image
        // Triple("Fig",          "మేడిపండు",    R.drawable.emoji_fruits_fig),         // TODO: add image
        // Triple("Jujube",       "రేగుపండు",    R.drawable.emoji_fruits_jujube),      // TODO: add image
        // Triple("Papaya",       "బొప్పాయిపండు", R.drawable.emoji_fruits_papaya),    // TODO: add image
        // Triple("Pomegranate",  "దానిమ్మపండు", R.drawable.emoji_fruits_pomegranate), // TODO: add image
        // Triple("Coconut",      "కొబ్బరికాయ",  R.drawable.emoji_fruits_coconut),     // TODO: add image
        // Triple("Jackfruit",    "పనసపండు",     R.drawable.emoji_fruits_jackfruit),   // TODO: add image
        // Triple("Sapota",       "సపోటపండు",    R.drawable.emoji_fruits_sapota),      // TODO: add image
    //)

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

        // Default to Family
        loadCategory(family)

        binding.btnPeople.setOnClickListener  { loadCategory(family)   }
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
