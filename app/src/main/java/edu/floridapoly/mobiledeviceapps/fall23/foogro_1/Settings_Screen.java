package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Settings_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);

        Button profileInfoButton = findViewById(R.id.profile_info_button);
        Button personalInfoButton = findViewById(R.id.personal_info_button);
        Button savedItemsInfoButton = findViewById(R.id.saved_items_button);
        Button browsingHistoryButton = findViewById(R.id.browsing_history_button);
        Button logoutButton = findViewById(R.id.logout_button);

        profileInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Will allow you to view and change profile information");
            }
        });

        personalInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Will allow you to view and change personal information");
            }
        });

        savedItemsInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Allows you to view the items you were interested in");
            }
        });

        browsingHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Shows you what items you last looked at");
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Logs you out");
                // You can also perform logout actions here if needed.
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
