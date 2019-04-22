package com.example.cashcontrol;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import static com.example.cashcontrol.Predict.food;
import static com.example.cashcontrol.Predict.others;

public class GraphOthers extends AppCompatActivity {
    LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_others);
        int y,x;
        x = 0;
        y = 0;
        GraphView graph = findViewById(R.id.graph3);
        series = new LineGraphSeries<DataPoint>();
        int n  = others.size();
        for(int i = -1;i<n;i++) {
            x = x + 1;
            if(i == -1)
                y = 0;
            else
                y = others.get(i);
            series.appendData(new DataPoint(x,y), true,n);
        }
        graph.addSeries(series);
        series.setTitle("Others Expenditure");
        series.setColor(Color.parseColor("#8BC34A"));
        series.setDataPointsRadius(10);
        series.setThickness(10);
        series.setDrawDataPoints(true);
    }
}
