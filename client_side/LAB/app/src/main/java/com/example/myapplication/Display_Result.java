package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

public class Display_Result extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String lastOperation = intent.getStringExtra("last_operation");
        String lastResult= intent.getStringExtra( "last_result");

System.out.println(lastOperation);
        // Capture the layout's TextView and set the string as its text
        TextView operation = findViewById(R.id.operation);
        TextView result= findViewById(R.id.result);
        operation.setText(lastOperation);
        result.setText(lastResult);
    }
    public void URLresearch(View view) {
        //Intent intent = new Intent(this, URL.class);
        EditText editText = (EditText) findViewById(R.id.url);
        String message = editText.getText().toString();
        Uri uri = Uri.parse("https://www.google.com/search?q="+message);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);

    }


}
