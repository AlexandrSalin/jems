package by.salin.demo.jems;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import by.salin.apps.jems.EventHandlerCallback;
import by.salin.apps.jems.JEMS;
import by.salin.apps.jems.impl.Event;


public class SampleActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sample, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements EventHandlerCallback {

        public PlaceholderFragment() {
        }

        private TextView text;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.fragment_sample, container, false);
            text = (TextView) root.findViewById(R.id.text);
            return root;
        }

        @Override
        public void onStart() {
            super.onStart();
            JEMS.dispatcher().addListenerOnEvent(DemoJemsEvent.class, this);
        }

        @Override
        public void onStop() {
            super.onStop();
            JEMS.dispatcher().removeListener(this);
        }

        @Override
        public void onResume() {
            super.onResume();
            DemoThread.launchDemo();
        }

        @Override
        public void onEvent(Event event) {
            text.setText(event.toString());
        }
    }
}
