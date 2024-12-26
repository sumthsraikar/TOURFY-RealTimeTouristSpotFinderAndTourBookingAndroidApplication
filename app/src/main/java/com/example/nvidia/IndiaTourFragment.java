package com.example.nvidia;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class IndiaTourFragment extends Fragment {

    CardView karnatakaCard ,maharashtraCard,goaCard,keralaCard,tamil_naduCard,andhra_pradeshCard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_india_tour, container, false);

        // Initialize the CardView
        karnatakaCard = rootView.findViewById(R.id.karnatakaCard);
        maharashtraCard = rootView.findViewById(R.id.maharashtraCard);
        goaCard = rootView.findViewById(R.id.goaCard);
        keralaCard = rootView.findViewById(R.id.keralaCard);
        tamil_naduCard = rootView.findViewById(R.id.tamil_naduCard);
        andhra_pradeshCard = rootView.findViewById(R.id.andhra_pradeshCard);

        // Set OnClickListener for the CardView
        karnatakaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), karnatakaTour.class);
                startActivity(intent);
            }
        });

        // Set OnClickListener for Rajasthan Card
        maharashtraCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), maharashtraTour.class);
                startActivity(intent);
            }
        });

        // Set OnClickListener for Rajasthan Card
        keralaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), keralaTour.class);
                startActivity(intent);
            }
        });

        // Set OnClickListener for Rajasthan Card
        tamil_naduCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), tamil_naduTour.class);
                startActivity(intent);
            }
        });

        // Set OnClickListener for Rajasthan Card
        goaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), goaTour.class);
                startActivity(intent);
            }
        });

        // Set OnClickListener for Rajasthan Card
        andhra_pradeshCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), andhra_pradeshTour.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
