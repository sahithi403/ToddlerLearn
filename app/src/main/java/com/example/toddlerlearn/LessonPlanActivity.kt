package com.example.toddlerlearn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toddlerlearn.databinding.ActivityLessonPlanBinding

data class Lesson(val number: Int, val title: String, val description: String)

class LessonPlanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLessonPlanBinding

    private val lessons = listOf(
        Lesson(1,  "Colors of the Rainbow",  "Learn red, orange, yellow, green, blue, purple"),
        Lesson(2,  "Animal Friends",          "Meet farm animals and the sounds they make"),
        Lesson(3,  "Shapes All Around Us",    "Circles, squares, triangles and more"),
        Lesson(4,  "Numbers 1 to 10",         "Count along with fun pictures"),
        Lesson(5,  "The Alphabet A–Z",        "Learn every letter with a fun word"),
        Lesson(6,  "Big & Small",             "Explore size comparisons with animals"),
        Lesson(7,  "My Body Parts",           "Head, shoulders, knees and toes"),
        Lesson(8,  "Fruits & Vegetables",     "Colorful foods that are good for you"),
        Lesson(9,  "Day & Night",             "What happens when the sun goes down?"),
        Lesson(10, "Feelings & Emotions",     "Happy, sad, excited — name how you feel")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        binding.rvLessons.layoutManager = LinearLayoutManager(this)
        binding.rvLessons.adapter = LessonAdapter(lessons) { lesson ->
            Toast.makeText(this, "${lesson.title} — Coming Soon!", Toast.LENGTH_SHORT).show()
        }
    }
}

class LessonAdapter(
    private val lessons: List<Lesson>,
    private val onClick: (Lesson) -> Unit
) : RecyclerView.Adapter<LessonAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNumber: TextView = view.findViewById(R.id.tvLessonNumber)
        val tvTitle: TextView = view.findViewById(R.id.tvLessonTitle)
        val tvDesc: TextView = view.findViewById(R.id.tvLessonDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_lesson, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lesson = lessons[position]
        holder.tvNumber.text = lesson.number.toString()
        holder.tvTitle.text = lesson.title
        holder.tvDesc.text = lesson.description
        holder.itemView.setOnClickListener { onClick(lesson) }
    }

    override fun getItemCount() = lessons.size
}
