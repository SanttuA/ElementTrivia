package com.apps.santtualatalo.elementtrivia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class ElementsActivity extends AppCompatActivity {

    private ListView elementListView;   //will hold the list of all the elements

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elements);

        //getting the element array which will populate a list view
        ArrayList<Element> elementList = Trivia.GetElementList(this, Constants.elementsFile);
        Element[] elements = elementList.toArray(new Element[elementList.size()]);

        ElementListAdapter adapter = new ElementListAdapter(this, R.layout.element_item_row, elements);
        elementListView = (ListView) findViewById(R.id.elementListView);

        //adding a header for the list view
        View header = getLayoutInflater().inflate(R.layout.element_header_row, null);
        elementListView.addHeaderView(header);
        //adding the element list adapter to the list view
        elementListView.setAdapter(adapter);
    }
}
