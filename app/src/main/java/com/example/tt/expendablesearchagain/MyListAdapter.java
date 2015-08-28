package com.example.tt.expendablesearchagain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by TT
 */
public class MyListAdapter extends BaseExpandableListAdapter {
    Context context;
    ArrayList<Continent> continentList;
    ArrayList<Continent> originalList;

    public MyListAdapter(Context context, ArrayList<Continent> continentList) {
        this.context = context;
        this.continentList = new ArrayList<Continent>();
        this.continentList.addAll(continentList);
        this.originalList = new ArrayList<Continent>();
        this.originalList.addAll(continentList);
    }

    @Override
    public int getGroupCount() {
        return continentList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return continentList.get(groupPosition).getCountryList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        Continent continent = continentList.get(groupPosition);
        return continent;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<Country> countries =  continentList.get(groupPosition).getCountryList();

        return countries.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                      Continent continent = continentList.get(groupPosition);
        if (convertView==null) {
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_row, null);
        }
        TextView head = (TextView) convertView.findViewById(R.id.head);
        head.setText(continent.getName().trim());


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
      Country country =  (Country)  getChild(groupPosition, childPosition);
        if (convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_row, null);
        }
        TextView code = (TextView) convertView.findViewById(R.id.code);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView population = (TextView) convertView.findViewById(R.id.population);

        code.setText(country.getCode().trim());
        name.setText(country.getName().trim());
        population.setText(NumberFormat.getNumberInstance(Locale.US).format(country.getPopulation()));

        return convertView;
        }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void fillterData(String query) {
        query = query.toLowerCase();
        continentList.clear();
        if (query.isEmpty()) {
            continentList.addAll(originalList);
        } else {
            for (Continent continent : originalList) {
                ArrayList<Country> contryList = continent.getCountryList();
                ArrayList<Country> tempList = new ArrayList<Country>();
                for (Country country : contryList) {
                    if (country.getName().toLowerCase().contains(query) || country.getCode().toLowerCase().contains(query))
                    {
                        tempList.add(country);
                    }
                }
                if (tempList.size() > 0) {
                    Continent nContitent = new Continent(continent.getName(), tempList);
                    continentList.add(nContitent);
                }
            }

            }

        notifyDataSetChanged();
        }

    }

