package ua.tohateam.appinfo;

import android.app.Fragment;
import android.os.Bundle;

import java.util.List;

public class RetainedFragment extends Fragment {

    static final String FRAGMENT_TAG = "fragment_retained";

    private List<AppItemModel> mList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setList(List<AppItemModel> list) {
        mList = list;
    }

    public List<AppItemModel> getList() {
        return mList;
    }
}
