package oraro.com.blood.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import oraro.com.blood.R;
import oraro.com.blood.fragment.ListFragment;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("sysout","main oncreate");
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.dialtacts_frame, new ListFragment(), "Generation")
                    .commit();
        }
    }
}
