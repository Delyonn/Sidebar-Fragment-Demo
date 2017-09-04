package miracle.sidebarfragmentdemo.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.Random;

import miracle.sidebarfragmentdemo.R;


public class PageFragment extends Fragment {


    public PageFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.page_fragment, container, false);
        TextView tv = (TextView) v.findViewById(R.id.random);

        Random r =  new Random();
        int i = r.nextInt(10);
        tv.setText("Random generated number: " + i);
        return v;
    }
}



