package com.example.sentient_eyecpfc.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sentient_eyecpfc.Data.DBProduct;
import com.example.sentient_eyecpfc.Database.DatabaseUsage;
import com.example.sentient_eyecpfc.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;


public class StatisticFragment extends Fragment {
    private ArrayList<Double> mProducts;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        GraphView calGraph = view.findViewById(R.id.caloriesGist);
        DatabaseUsage dBUs = new DatabaseUsage(getContext());
        mProducts = dBUs.setData(getContext());
        DataPoint[] points = new DataPoint[mProducts.size()];
        for (int day = 0; day < mProducts.size(); day++) {
            points[day] = new DataPoint(day, mProducts.get(day));
        }
        BarGraphSeries<DataPoint> calorieGist = new BarGraphSeries<>(points);

        calorieGist.setColor(Color.argb(100, 34, 139, 34));
        calGraph.addSeries(calorieGist);

        // title
        calGraph.setTitle("Calories gist");
        calGraph.getGridLabelRenderer().setVerticalAxisTitle("Amount");
        calGraph.getGridLabelRenderer().setHorizontalAxisTitle("Date");

        // set manual X bounds
        calGraph.getViewport().setXAxisBoundsManual(true);
        calGraph.getViewport().setMinX(1);
        calGraph.getViewport().setMaxX(10);

        // enable scrolling
        calGraph.getViewport().setScrollable(true);

        return view;
    }
}
