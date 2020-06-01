package com.seanutf.cmmonui.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.seanutf.cmmonui.arch.BaseViewModel;

import java.util.List;

public class List1ViewModel extends BaseViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<List<SvData>> svDataList;

    public LiveData<List<SvData>> getSvData() {
        //todo LiveData
        if (svDataList == null) {
            svDataList = new MutableLiveData<List<SvData>>();
            loadSvData();
        }
        return svDataList;
    }

    private void loadSvData() {

    }
}
