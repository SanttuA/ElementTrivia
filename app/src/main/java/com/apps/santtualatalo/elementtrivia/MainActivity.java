package com.apps.santtualatalo.elementtrivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private RadioGroup questionAmountGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionAmountGroup = (RadioGroup)findViewById(R.id.questionAmountGroup);
        final RadioGroup questionTypeGroup = (RadioGroup)findViewById(R.id.questionTypeGroup);

        Button startButton = (Button) findViewById(R.id.startTriviaButton);
        startButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //use trivia options to start a trivia game in trivia activity

                //number of questions per trivia
                int questions = Integer.parseInt(GetRadioGroupStringValue(questionAmountGroup));
                //trivia type (name or symbol)
                String type = GetRadioGroupStringValue(questionTypeGroup);

                Intent i = new Intent(getApplicationContext(), TriviaActivity.class);

                i.putExtra(Constants.questionAmountKey,questions);
                i.putExtra(Constants.questionTypeKey,type);
                startActivity(i);
            }
        });

        Button elementsButton = (Button) findViewById(R.id.elementListButton);
        elementsButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), ElementsActivity.class);
                startActivity(i);
            }
        });
    }

    /**
     * Returns the string value of given radio group's selected radio button.
     * @param group radio button group that has a selected radio button.
     * @return string of selected radio button's text.
     */
    private String GetRadioGroupStringValue(RadioGroup group)
    {
        int radioButtonID = group.getCheckedRadioButtonId();
        View radioButton = group.findViewById(radioButtonID);
        int idx = group.indexOfChild(radioButton);
        RadioButton r = (RadioButton)  group.getChildAt(idx);

        return r.getText().toString();
    }


}
