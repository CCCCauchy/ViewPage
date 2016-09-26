package oraro.com.blood.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import oraro.com.blood.R;

/**
 * Created by Administrator on 2016/9/26.
 */
public class GenerationFragment extends Fragment {
    private View mParentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("sysout","sdfsf");
        mParentView = inflater.inflate(R.layout.generation_fragment,container, false);

        TextView textView = new TextView(getActivity());
        textView.setText("sdfsdf");
        ListView listView = (ListView) mParentView.findViewById(R.id.generation_listView);


        return mParentView;
    }
}
