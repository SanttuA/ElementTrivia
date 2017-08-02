package com.apps.santtualatalo.elementtrivia;

/**
 * Element's info eg name and symbol
 */

public class Element
{
    private String name;    //the name of the element eg Gold
    private String symbol;  //the symbol of the element eg Au

    /**
     * Initializes an element with given name and symbol
     * @param name element's name eg. Helium
     * @param symbol elements symbol eg. He
     */
    public Element(String name, String symbol)
    {
        this.name = name;
        this.symbol = symbol;
    }

    public String GetName()
    { return name; }

    public String GetSymbol()
    { return symbol; }
}
