package io.github.bbodin.clickandsee;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {

    private Button   _btn = null;
    private TextView _tv  = null;
    private DatabaseReference _myRef = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _btn = (Button) findViewById(R.id.button);
        _tv  = (TextView) findViewById(R.id.label1);


        // Connect to server


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        _myRef = database.getReference("when");




        _btn = (Button)   findViewById( R.id.button);

        _btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                _myRef.setValue(dtf.format(now));
            }
        });



        _myRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                _tv.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                _tv.setText("Error");
            }
        });




    }
}