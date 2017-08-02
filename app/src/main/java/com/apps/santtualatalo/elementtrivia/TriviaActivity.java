package com.apps.santtualatalo.elementtrivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class TriviaActivity extends AppCompatActivity {

    private int questionsAmount;    //how many questions are there in this trivia
    private int questionsAnswered;  //how many questions have been answered thus far
    private int correctAnswers; //how many questions were answered correctly
    private String questionType;    //are we asking for symbols or names

    private Element correctElement; //the element we want the answer for
    private ArrayList<Element> answerOptionsList;   //the correct + false answers

    private TextView questionsAnsweredText; //questions answered eg "4/10"
    private TextView currentQuestionText;   //the element player has to know the answer to
    private RadioGroup answerOptionsGroup;  //the radio group holding the answer options
    private Button answerButton;    //answers the current question and moves to the next question

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        //setup ui elements
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            questionsAmount = extras.getInt(Constants.questionAmountKey);
            questionType = extras.getString(Constants.questionTypeKey);
        }

        TextView questionText = (TextView) findViewById(R.id.questionText);

        if(questionType.equals(getString(R.string.element_name)))
            questionText.setText(getString(R.string.question_symbol));
        else if(questionType.equals(getString(R.string.element_symbol) ))
            questionText.setText(getString(R.string.question_name));

        questionsAnsweredText = (TextView) findViewById(R.id.numberText);
        answerOptionsGroup = (RadioGroup) findViewById(R.id.questionOptionsGroup);
        currentQuestionText = (TextView) findViewById(R.id.elementInQuestion);
        answerButton = (Button) findViewById(R.id.answerButton);
        answerButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //check if selection was correct and move the game forward
                CheckAnswer();
            }
        });

        //start the trivia game
        StartTriviaGame();
    }

    /**
     * Handles starting the trivia game.
     */
    private void StartTriviaGame()
    {
        UpdateQuestionAnswered();
        PullNewQuestion();
    }

    /**
     * Pulls a new trivia question and changes the UI for the question.
     */
    private void PullNewQuestion()
    {
        //change the question to the current element (name or symbol based on user choice)

        //get a list of all the elements and pick a new correct element
        ArrayList<Element> elementList = Trivia.GetElementList(this, Constants.elementsFile);
        correctElement = Trivia.GetRandomElement(elementList);

        //remove correct element from element list to avoid getting duplicates in answers
        elementList.remove(correctElement);

        //set the UI to ask for the correct element
        if(questionType.equals(getString(R.string.element_symbol)))
        {
            currentQuestionText.setText(correctElement.GetSymbol());
        }
        else if(questionType.equals(getString(R.string.element_name)))
        {
            currentQuestionText.setText(correctElement.GetName());
        }

        //get false answers and setup the answer options
        answerOptionsList = Trivia.GetRandomFalseElements(Constants.falseAnswerAmount, elementList);
        answerOptionsList.add(correctElement);
        Collections.shuffle(answerOptionsList);

        for(int i = 0; i < answerOptionsGroup.getChildCount(); i++)
        {
            TextView answerOption = (TextView) answerOptionsGroup.getChildAt(i);
            if(questionType.equals(getString(R.string.element_symbol)))
                answerOption.setText(answerOptionsList.get(i).GetName());
            else
                answerOption.setText(answerOptionsList.get(i).GetSymbol());
        }

    }

    /**
     * Checks user's answer and moves the game forward by either pulling a new question or ending the game.
     */
    private void CheckAnswer()
    {
        int radioButtonID = answerOptionsGroup.getCheckedRadioButtonId();
        if (radioButtonID == -1)
        {
            // no radio buttons are checked
        }
        else
        {
            // get the answer user selected and check if its the correct one
            View radioButton = answerOptionsGroup.findViewById(radioButtonID);
            int idx = answerOptionsGroup.indexOfChild(radioButton);
            if(correctElement == answerOptionsList.get(idx))
            {
                //user answered correctly
                correctAnswers++;
            }
            else
            {
                //user didn't answer correctly
            }

            //increase answered questions by one to keep track of how many questions there have
            // been already.
            questionsAnswered++;

            //if questions answered equals to max questions end game, else pull new question
            //could animate correct answer before pulling a new question
            if(questionsAnswered < questionsAmount)
            {
                //update UI
                UpdateQuestionAnswered();
                //pull a new question
                PullNewQuestion();
            }
            else
            {
                //end trivia and show results
                EndTrivia();
            }

        }
    }

    /**
     * Updates the trivia UI by changing how many questions have been answered thus far
     */
    private void UpdateQuestionAnswered()
    {
        questionsAnsweredText.setText(questionsAnswered + "/" + questionsAmount);
    }

    /**
     * Ends the trivia game by changing activity to the result activity
     */
    private void EndTrivia()
    {
        //go to result activity and pass results in extras
        Intent i = new Intent(getApplicationContext(), ResultsActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        i.putExtra(Constants.questionAmountKey,questionsAmount);
        i.putExtra(Constants.correctAnswersKey, correctAnswers);
        i.putExtra(Constants.questionTypeKey, questionType);
        startActivity(i);
        //remove this activity from activity stack to avoid user getting back to this activity from results activity
        finish();
    }
}
