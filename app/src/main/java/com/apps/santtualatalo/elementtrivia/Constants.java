package com.apps.santtualatalo.elementtrivia;

/**
 * Has all the constants for this app
 */

public class Constants
{
    public static String elementsFile = "Elements.json";    //the asset file name where elements are stored

    public static String questionAmountKey = "questions";   //extras bundle key for how many questions per trivia
    public static String questionTypeKey = "type";  //extras bundle key for chosen question type
    public static String correctAnswersKey = "correct"; //extras bundle key for correct answers amount

    public static String jsonNameKey = "name";  //used to get name field in a json asset
    public static String jsonSymbolKey = "symbol";  //used to get symbol field in a json asset

    public static int falseAnswerAmount = 3;    //how many wrong answers there are per question

}
