package start.application.RunningApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.application.RunningApp.R;

public class LocationActivity extends AppCompatActivity {

    ListView l;
    String places[]
            = { "New Hanover", "White Oak",
            "Topsail", "Jacksonville"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        l = findViewById(R.id.list);
        ArrayAdapter<String> arr;
        arr
                = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                places);
        l.setAdapter(arr);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ((Integer) position == 0) {
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=1307 Market+Street, Wilmington, North+Carolina");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
                if ((Integer) position == 1){
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=1001 Piney+Green+Road, Jacksonville, North+Carolina");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
                if ((Integer) position == 2){
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=245 North+Street+Johns+Church+Road, Hampstead, North+Carolina");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
                if ((Integer) position == 3){
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=1021 Henderson+Drive,, Jacksonville, North+Carolina");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            }
        });
    }
}