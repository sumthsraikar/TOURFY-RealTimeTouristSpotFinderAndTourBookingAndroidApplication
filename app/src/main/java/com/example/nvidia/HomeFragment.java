package com.example.nvidia;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

public class HomeFragment extends Fragment {

    private ViewPager2 viewPager;
    private Handler handler;
    private Runnable runnable;
    private int currentPage = 0; // To keep track of the current page
    private int[] images = {
            R.drawable.whatsapp5, // Replace these with your drawable resources
            R.drawable.whatsapp1,
            R.drawable.whatsapp2,
            R.drawable.whatsapp3
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize ViewPager2
        viewPager = view.findViewById(R.id.imageSlider);

        // Set up the ImageSliderAdapter
        ImageSliderAdapter adapter = new ImageSliderAdapter(images, requireActivity());
        viewPager.setAdapter(adapter);

        // Initialize marquee text
        TextView newsMarqueeText = view.findViewById(R.id.newsMarqueeText);
        newsMarqueeText.setSelected(true); // Enable marquee scrolling

        // Set up auto-slide functionality
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == images.length) {
                    currentPage = 0; // Reset to the first image when at the end
                }
                viewPager.setCurrentItem(currentPage++, true); // Slide to the next image
                handler.postDelayed(this, 3000); // Change image every 3 seconds
            }
        };
        handler.postDelayed(runnable, 3000); // Start the auto-slide after 3 seconds

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Remove the callback to prevent memory leaks
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
