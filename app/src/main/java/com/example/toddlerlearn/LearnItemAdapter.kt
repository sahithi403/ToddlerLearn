package com.example.toddlerlearn

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LearnItemAdapter(
    private val items: List<LearnItem>,
    private val onItemClicked: (LearnItem) -> Unit
) : RecyclerView.Adapter<LearnItemAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivEmoji: ImageView = view.findViewById(R.id.ivEmoji)
        val tvLabel: TextView = view.findViewById(R.id.tvLabel)
        val cardLayout: LinearLayout = view.findViewById(R.id.cardLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_learn_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvLabel.text = item.label
        holder.cardLayout.setBackgroundColor(item.cardColor)

        if (item.drawableResId != 0) {
            holder.ivEmoji.setImageResource(item.drawableResId)
        }

        holder.itemView.setOnClickListener {
            bounceAnimate(holder.itemView)
            onItemClicked(item)
        }
    }

    override fun getItemCount() = items.size

    private fun bounceAnimate(view: View) {
        val scaleUpX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.2f)
        val scaleUpY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.2f)
        val scaleDownX = ObjectAnimator.ofFloat(view, "scaleX", 1.2f, 1f)
        val scaleDownY = ObjectAnimator.ofFloat(view, "scaleY", 1.2f, 1f)

        scaleUpX.duration = 100
        scaleUpY.duration = 100
        scaleDownX.duration = 150
        scaleDownY.duration = 150

        val scaleUp = AnimatorSet().apply { playTogether(scaleUpX, scaleUpY) }
        val scaleDown = AnimatorSet().apply { playTogether(scaleDownX, scaleDownY) }

        AnimatorSet().apply {
            playSequentially(scaleUp, scaleDown)
            start()
        }
    }
}
