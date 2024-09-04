package vn.edu.usth.usthweather;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ForecastFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);

        // Find the LinearLayout
        LinearLayout linearLayout = view.findViewById(R.id.linear_layout_forecast);

        // Set the background color of the LinearLayout
        linearLayout.setBackgroundColor(Color.parseColor("#20FF0000")); // Set initial background color

        // Find the TextView and ImageView
        TextView textView = view.findViewById(R.id.text_view_day);
        ImageView imageView = view.findViewById(R.id.image_view_weather);

        // Optional: Set text and image dynamically if needed
        textView.setText("Thursday"); // Example of setting text
        imageView.setImageResource(R.drawable.cloudy_08); // Example of setting image resource

        return view;
    }
}
