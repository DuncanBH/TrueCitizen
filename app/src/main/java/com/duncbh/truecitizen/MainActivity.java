package com.duncbh.truecitizen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.duncbh.truecitizen.databinding.ActivityMainBinding;
import com.duncbh.truecitizen.model.Question;
import com.google.android.material.snackbar.Snackbar;

import java.text.MessageFormat;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private int currentQuestionIndex = 0;
    private int correctAnswerCount = 0;

    private Question[] questionBank = new Question[]{
            new Question(R.string.question_amendments, false),
            new Question(R.string.question_constitution, true),
            new Question(R.string.question_declaration, true),
            new Question(R.string.question_independence_rights, true),
            new Question(R.string.question_religion, true),
            new Question(R.string.question_government, false),
            new Question(R.string.question_government_feds, false),
            new Question(R.string.question_government_senators, true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.questionTextView.setText(MessageFormat.format("Question #{0}: {1}", currentQuestionIndex + 1, getString(questionBank[currentQuestionIndex].getAnswerResId())));

        binding.nextButton.setOnClickListener(view -> {
            //Log.d("Main", "onCreate: " + questionBank[currentQuestionIndex++].getAnswerResId());
            currentQuestionIndex = (currentQuestionIndex+1) % questionBank.length;
            updateQuestion();
        });
        binding.prevButton.setOnClickListener(view -> {
            //Log.d("Main", "onCreate: " + questionBank[currentQuestionIndex++].getAnswerResId());
            if (currentQuestionIndex > 0) {
                currentQuestionIndex = (currentQuestionIndex - 1) % questionBank.length;
                updateQuestion();
            }
        });

        binding.firstButton.setOnClickListener(view -> {
            currentQuestionIndex = 0;
            updateQuestion();
        });
        binding.lastButton.setOnClickListener(view -> {
            currentQuestionIndex = questionBank.length - 1;
            updateQuestion();
        });

        binding.trueButton.setOnClickListener(view -> checkAnswer(true));
        binding.falseButton.setOnClickListener(view -> checkAnswer(false));

        binding.submitButton.setOnClickListener(view -> {
            binding.scoreText.setText(String.format("Score: %d/%d", correctAnswerCount, questionBank.length));
        });
    }

    private void checkAnswer(boolean userChoseCorrect) {
        boolean answerIsCorrect = questionBank[currentQuestionIndex].isAnswerTrue();
        int messageId;
        if (answerIsCorrect == userChoseCorrect) {
            messageId = R.string.correct_answer;
            correctAnswerCount++;
        }
        else {
            messageId = R.string.wrong_answer;
        }
        Snackbar.make(binding.imageView3, messageId, Snackbar.LENGTH_SHORT).show();
        //disable answer buttons
        binding.trueButton.setEnabled(false);
        binding.falseButton.setEnabled(false);
    }

    private void updateQuestion() {
        binding.questionTextView.setText(String.format("Question #%d: %s", currentQuestionIndex + 1, getString(questionBank[currentQuestionIndex].getAnswerResId())));
        Log.d("TEST", String.valueOf(questionBank[currentQuestionIndex].getAnswerResId()));
        //reenable answer buttons
        binding.trueButton.setEnabled(true);
        binding.falseButton.setEnabled(true);
    }

}