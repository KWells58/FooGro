package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class Navigation_Bar extends LinearLayout {

    public Navigation_Bar(Context context) {
        super(context);
        init(context);
    }

    public Navigation_Bar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_navigation_bar, this, true);

        Button button1 = view.findViewById(R.id.button1);
        Button button2 = view.findViewById(R.id.button2);
        Button button4 = view.findViewById(R.id.button4);

        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace with the desired action for Button 1
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });

        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace with the desired action for Button
                Intent intent = new Intent(context, Item_Search_Screen.class);
                context.startActivity(intent);
            }
        });



        button4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace with the desired action for Button 4
                Intent intent = new Intent(context, Cart_Screen.class);
                context.startActivity(intent);
            }
        });
    }
}
