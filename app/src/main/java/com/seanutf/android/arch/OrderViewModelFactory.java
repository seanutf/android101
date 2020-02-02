package com.seanutf.android.arch;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class OrderViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(MyViewModel.class)) return (T) new MyViewModel(7);
        else if (modelClass.isAssignableFrom(MyViewModel.class)) {

        } else {
            return null;
        }
        return null;
    }
}
