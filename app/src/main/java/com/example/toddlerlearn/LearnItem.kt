package com.example.toddlerlearn

data class LearnItem(
    val emoji: String,
    val label: String,
    val cardColor: Int,
    val drawableResId: Int = 0
)
