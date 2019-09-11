package com.example.swipequiz

data class QuizItem (
    var question: String,
    var correctPosition: Int,
    var questionText: CharSequence = ""
)