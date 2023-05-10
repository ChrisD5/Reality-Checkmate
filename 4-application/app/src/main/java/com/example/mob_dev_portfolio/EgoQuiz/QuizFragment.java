package com.example.mob_dev_portfolio.EgoQuiz;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mob_dev_portfolio.EgoQuiz.QuestionAnswer;
import com.example.mob_dev_portfolio.R;

public class QuizFragment extends Fragment implements View.OnClickListener{

    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA, ansB, ansC, ansD;
    Button submitBtn;


    int score=0;
    int totalQuestions = QuestionAnswer.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        totalQuestionsTextView = view.findViewById(R.id.total_questions);
        questionTextView = view.findViewById(R.id.question);
        ansA = view.findViewById(R.id.ans_A);
        ansB = view.findViewById(R.id.ans_B);
        ansC = view.findViewById(R.id.ans_C);
        ansD = view.findViewById(R.id.ans_D);
        submitBtn = view.findViewById(R.id.submit_btn);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        totalQuestionsTextView.setText("Total Questions: " + totalQuestions);

        loadNewQuestion();




        return view;
    }

    @Override
    public void onClick(View view) {

        ansA.setTextColor(Color.WHITE);
        ansB.setTextColor(Color.WHITE);
        ansC.setTextColor(Color.WHITE);
        ansD.setTextColor(Color.WHITE);



        Button clickedButton = (Button) view;
        if(clickedButton.getId()==R.id.submit_btn) {
            if(selectedAnswer.equals(QuestionAnswer.correctAnswers[currentQuestionIndex])){
                score++;
            }
            currentQuestionIndex++;
            loadNewQuestion();

        }else{
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setTextColor(Color.BLACK);
        }


    }

    void loadNewQuestion(){

        if(currentQuestionIndex == totalQuestions){
            finishQuiz();
            return;
        }
        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
        ansA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        ansC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
        ansD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);
    }

    void finishQuiz() {
        String passStatus = "";
        if (score > totalQuestions * 0.79) {
            passStatus = "With a score this high you may as well delete the app!";
        } else if (score > totalQuestions * 0.49) {
            passStatus = "Not bad, your ego could do with some work though";
        } else if (score > totalQuestions * 0.29) {
            passStatus = "You should take a long hard look at yourself";
        } else {
            passStatus = "I have no words, consider therapy?";
        }

        new AlertDialog.Builder(getActivity())
                .setTitle("Quiz Results")
                .setMessage("You have scored " + score + " out of " + totalQuestions + ", " + passStatus)
                .setPositiveButton("Restart", (dialogInterface, i) -> restartQuiz())
                .setCancelable(false)
                .show();

    }

    void restartQuiz(){
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();

    }
}
