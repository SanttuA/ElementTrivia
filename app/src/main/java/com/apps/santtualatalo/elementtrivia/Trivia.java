package com.apps.santtualatalo.elementtrivia;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Trivia game helper class
 */
public class Trivia
{

    /**
     * Returns a random element from given element list.
     *
     * @param elementList an ArrayList of elements that has to have at least one element.
     * @return a random element from the given list or null if elementList doesn't contain any elements.
     */
    public static Element GetRandomElement(ArrayList<Element> elementList)
    {
        if(elementList.size() > 0)
        {
            Random r = new Random();
            return elementList.get(r.nextInt(elementList.size()));
        }
        else
            return null;
    }

    /**
     * Gets random "false" elements that are not duplicates of each other.
     *
     * @param falseAnswers amount of false elements to return.
     * @param elementList the element ArrayList where to select the false answers from. Has to contain
     *                    more than falseAnswers amount of elements.
     * @return ArrayList of false elements or null if elementList doesn't contain enough elements.
     */
    public static ArrayList<Element> GetRandomFalseElements(int falseAnswers, ArrayList<Element> elementList)
    {
        if(elementList.size() > falseAnswers)
        {
            //the list of false elements to be returned
            ArrayList<Element> falseList = new ArrayList<>(falseAnswers);

            //store all the indexes of all the elements in a list that will be used for picking
            //random elements from the given elementList.
            ArrayList<Integer> numbers = new ArrayList<>();
            for(int i = 0; i < elementList.size(); i++)
            {
                numbers.add(i);
            }
            //pick random index numbers for false answers list.
            //Remove each chosen index number from random number mix to avoid duplicate elements.
            Random r = new Random();
            int randomIndex;
            for(int i = 0; i < falseAnswers; i++)
            {
                randomIndex = r.nextInt(numbers.size());
                falseList.add(elementList.get(numbers.get(randomIndex)));
                numbers.remove(randomIndex);
            }

            return falseList;
        }
        else
            return null;
    }

    /**
     * Retrieves an element ArrayList with given fileName.
     * @param activity the calling activity.
     * @param fileName the asset file name of the json element list.
     * @return an ArrayList of elements.
     */
    public static ArrayList<Element> GetElementList(Activity activity, String fileName)
    {
        return JsonAssetReader.GetElementsFromJson(activity, fileName);
    }

}

/**
 * Handles reading json asset files
 */
class JsonAssetReader
{

    /**
     * Reads a specified asset file and returns it in string format
     * @param activity the calling activity
     * @param fileName the name of the json asset file eg "yourJsonFile.json"
     * @return the asset file in string format
     */
    private static String ReadAssetFile(Activity activity, String fileName)
    {
        String json = null;
        try {
            InputStream is = activity.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    /**
     * Gets elements from a specified json asset file and returns them in an ArrayList<Element>
     * @param activity the calling activity
     * @param fileName the asset file where the elements are located
     * @return all elements from elements json file in an ArrayList<Element>
     */
    public static ArrayList<Element> GetElementsFromJson(Activity activity, String fileName)
    {
        ArrayList<Element> elementsList = new ArrayList<>();
        try {
            //read the json file to json array
            JSONArray elementsArray = new JSONArray(ReadAssetFile(activity, fileName));

            //init the elements in json array and add them in elementsList
            for (int i = 0; i < elementsArray.length(); i++)
            {
                JSONObject jsonElement = elementsArray.getJSONObject(i);
                Element element = new Element(jsonElement.getString(Constants.jsonNameKey),
                        jsonElement.getString(Constants.jsonSymbolKey));
                elementsList.add(element);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return elementsList;
    }
}
