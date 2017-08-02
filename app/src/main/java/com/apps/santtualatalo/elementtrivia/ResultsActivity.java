package com.apps.santtualatalo.elementtrivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    private int correctAnswers; //how many questions user got right
    private int questionsAmount;    //how many questions user answered
    private String questionType;    //did user guess symbols or names

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            questionsAmount = extras.getInt(Constants.questionAmountKey);
            correctAnswers = extras.getInt(Constants.correctAnswersKey);
            questionType = extras.getString(Constants.questionTypeKey);
        }

        //setup feedback text based on correct answers ratio to total questions
        //1 = perfect, 0.75 = good, etc.
        TextView resultFeedbackText = (TextView) findViewById(R.id.feedbackText);
        float resultPercent = (float)correctAnswers/(float)questionsAmount;
        if(resultPercent == 1)
            resultFeedbackText.setText(R.string.result_perfect);
        else if(resultPercent >= 0.75 && resultPercent < 1)
            resultFeedbackText.setText(R.string.result_good);
        else if(resultPercent >= 0.50 && resultPercent < 0.75)
            resultFeedbackText.setText(R.string.result_ok);
        else
            resultFeedbackText.setText(R.string.result_bad);

        //setup score text
        TextView resultsScoreText = (TextView) findViewById(R.id.resultsScoreText);
        resultsScoreText.setText(correctAnswers + "/" + questionsAmount);

        //setup the navigation buttons "retry" and "main menu"
        Button retryButton = (Button) findViewById(R.id.retryButton);
        retryButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //use the trivia options to start a trivia game in trivia activity
                Intent i = new Intent(getApplicationContext(), TriviaActivity.class);
                i.putExtra(Constants.questionAmountKey, questionsAmount);
                i.putExtra(Constants.questionTypeKey, questionType);
                startActivity(i);
                finish();
            }
        });

        Button mainMenuButton = (Button) findViewById(R.id.mainMenuButton);
        mainMenuButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //calling only finish because main activity is the only other activity in activity stack.
                finish();
            }
        });
    }
}
