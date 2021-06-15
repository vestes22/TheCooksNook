package com.codelovely.thecooksnook;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

public class CookBookViewModel extends AndroidViewModel {
    private DatabaseRepository mRepository;

    public CookBookViewModel(Application application) {
        super(application);

        mRepository = new DatabaseRepository(application);
    }
}
