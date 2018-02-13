package copter.embi.flythecopter.common.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import copter.embi.flythecopter.R;

/**
 * Created by eMBi on 14.01.2018.
 */

public class MainMenuFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        return inflater.inflate(R.layout.main_menu_view, container, false);
    }
}
