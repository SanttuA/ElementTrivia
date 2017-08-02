package com.apps.santtualatalo.elementtrivia;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * List adapter for elements
 */
public class ElementListAdapter extends ArrayAdapter<Element>
{

    private Context context;
    private int layoutResourceId;
    private Element data[] = null;

    public ElementListAdapter(Context context, int layoutResourceId, Element[] data)
    {
        super(context, layoutResourceId, data);

        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ElementHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ElementHolder();
            holder.txtSymbol = (TextView)row.findViewById(R.id.symbolText);
            holder.txtName = (TextView)row.findViewById(R.id.nameText);

            row.setTag(holder);
        }
        else
        {
            holder = (ElementHolder)row.getTag();
        }

        Element element = data[position];
        holder.txtSymbol.setText(element.GetSymbol());
        holder.txtName.setText(element.GetName());

        return row;
    }

    private static class ElementHolder
    {
        TextView txtSymbol; //will hold element's symbol string
        TextView txtName;   //will hold element's name string
    }
}
