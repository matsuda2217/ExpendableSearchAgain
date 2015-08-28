package com.example.tt.expendablesearchagain;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener{
    SearchView searchView;
    ExpandableListView expandableListView;
    MyListAdapter myListAdapter;
    ArrayList<Continent> continents = new ArrayList<Continent>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) findViewById(R.id.search);
        expandableListView = (ExpandableListView) findViewById(R.id.expendableList);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnCloseListener(this);
        searchView.setOnQueryTextListener(this);
        displayList();
        expandData();

    }

    public void displayList() {
        loadSomeData();
        myListAdapter = new MyListAdapter(MainActivity.this, continents);
        expandableListView.setAdapter(myListAdapter);
    }
    public void expandData() {
        int count = myListAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            expandableListView.expandGroup(i);
        }


    }

    public void loadSomeData() {
        ArrayList<Country> arrayList = new ArrayList<Country>();
        Country country = new Country("USA", "United state of America", 200000000);
        arrayList.add(country);
        country = new Country("CAN", "CAN", 100000000);
        arrayList.add(country);
        country = new Country("BER", "Bermuda", 50000000);
        arrayList.add(country);
        Continent continent = new Continent("NotrhAmerica", arrayList);
        continents.add(continent);

        arrayList = new ArrayList<Country>();
         country = new Country("VN", "Vietnam", 200000000);
        arrayList.add(country);
        country = new Country("CHN", "China", 100000000);
        arrayList.add(country);
        country = new Country("JP", "Japan", 50000000);
        arrayList.add(country);
        country = new Country("KR", "Korea", 50000000);
        arrayList.add(country);
        continent = new Continent("Asia", arrayList);
        continents.add(continent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onClose() {
       myListAdapter.fillterData("");
        expandData();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
       myListAdapter.fillterData(query);
        expandData();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        myListAdapter.fillterData(newText);
        expandData();
        return false;
    }
}
