package com.gmail.rixx.justin.contentprovidertest.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmail.rixx.justin.contentprovidertest.Car;
import com.gmail.rixx.justin.contentprovidertest.R;

import java.util.List;

/**
 * Created by justin on 8/28/15.
 */
public class CarRecyclerViewAdapter extends RecyclerView.Adapter<CarRecyclerViewAdapter.CarViewHolder> {

    List<Car> cars;

    public CarRecyclerViewAdapter(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public CarViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_car, viewGroup, false);

        return new CarViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CarViewHolder viewHolder, int position) {

        Car item = cars.get(position);

        viewHolder.textView.setText(item.getMake() + " " + item.getModel() + " "
                + String.valueOf(item.getYear()));
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public TextView textView;

        public CarViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;
            textView = (TextView) itemView.findViewById(R.id.car_textview);
        }
    }
}
