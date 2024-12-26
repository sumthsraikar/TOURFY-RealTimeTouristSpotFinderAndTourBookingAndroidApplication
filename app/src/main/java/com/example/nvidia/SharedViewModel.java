package com.example.nvidia;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> tourDetails = new MutableLiveData<>();

    public void setTourDetails(String details) {
        tourDetails.setValue(details);
    }

    public LiveData<String> getTourDetails() {
        return tourDetails;
    }
}
