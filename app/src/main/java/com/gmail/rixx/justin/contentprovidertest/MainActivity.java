package com.gmail.rixx.justin.contentprovidertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.gmail.rixx.justin.contentprovidertest.Adapters.CarRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CarRecyclerViewAdapter mAdapter;
    private List<Car> mCars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeCars();

        setUpRecyclerView();
    }

    private void initializeCars() {
        mCars = new ArrayList<>();

        mCars.add(new Car("Chevy", "Corvette", 2005));
        mCars.add(new Car("VW", "Beetle", 1965));
        mCars.add(new Car("Porsche", "911", 2011));
        mCars.add(new Car("Ford", "Escort", 1986));
        mCars.add(new Car("Honda", "Civic", 1992));
        mCars.add(new Car("Toyota", "Camry", 2005));

    }

    private void setUpRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.cars_recyclerview);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new CarRecyclerViewAdapter(mCars);

        mRecyclerView.setAdapter(mAdapter);
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
}
