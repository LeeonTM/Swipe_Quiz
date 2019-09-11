package com.example.swipequiz

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val questions = arrayListOf<QuizItem>()
    private val quizAdapter = QuizAdapter(questions)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        // Init the recyclerView
        rvQuiz.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        rvQuiz.adapter = quizAdapter

        rvQuiz.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))

        // Add swipe functions to answer the Quiz Questions
        createItemTouchHelper().attachToRecyclerView(rvQuiz)

        // Add questions to the Quiz
        val questionStrings: Array<String> = resources.getStringArray(R.array.questions)
        questions.add(QuizItem("", 0, getText(R.string.quizTitle)))
        questions.add(QuizItem(questionStrings[0], 4))
        questions.add(QuizItem(questionStrings[1], 8))
        questions.add(QuizItem(questionStrings[2], 4))

        quizAdapter.notifyDataSetChanged()
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        var callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val currentQuestion = questions[position]

                    if (currentQuestion.correctPosition != 0) { // Dont check the title
                        if (direction == currentQuestion.correctPosition) {
                            Snackbar.make(rvQuiz, getString(R.string.correct, if (currentQuestion.correctPosition == 4) "true" else "false"), Snackbar.LENGTH_SHORT).show()
                        } else {
                            Snackbar.make(rvQuiz, getString(R.string.incorrect, if (currentQuestion.correctPosition == 4) "true" else "false"), Snackbar.LENGTH_SHORT).show()
                        }
                    }

                    quizAdapter.notifyItemChanged(position)
                }
            }

        return ItemTouchHelper(callback)
    }
}
