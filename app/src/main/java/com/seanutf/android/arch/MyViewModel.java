package com.seanutf.android.arch;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.seanutf.android.arch.data.User;

import java.util.List;

public class MyViewModel extends ViewModel {

    int i;

    public MyViewModel(int i) {
        this.i = i;
    }

    private MutableLiveData<List<User>> users; //todo MutableLiveData

    public LiveData<List<User>> getUsers() {  //todo LiveData
        if (users == null) {
            users = new MutableLiveData<List<User>>();
            //todo
            loadUsers();
        }
        return users;
    }

    private void loadUsers() {
        // Do an asynchronous operation to fetch users.
    }
}
