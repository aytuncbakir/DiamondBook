package com.sivamalabrothers.cevher;



import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by User on 2/28/2017.
 */

public class Sabah extends Fragment implements View.OnClickListener{
    private static final String TAG = "Sabah";

    private EditText yazi;
    static float sbhpunto = 14;
    FloatingActionButton fabayarlar;
    static int renksec = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sabah,container,false);

        fabayarlar =  view.findViewById(R.id.fabayarlar);
        fabayarlar.setOnClickListener(this);

        yazi = view.findViewById(R.id.yazi);
        yazi.setTextSize(sbhpunto);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        int id = renksec % 2;
        switch(id)
        {
            case 0:
            {
                yazi.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                renksec++;
                break;
            }
            case 1:
            {
                yazi.setTextColor(getResources().getColor(R.color.black));
                renksec++;
                break;
            }

        }
    }

}