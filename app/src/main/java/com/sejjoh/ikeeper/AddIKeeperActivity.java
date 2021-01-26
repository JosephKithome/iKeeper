package com.sejjoh.ikeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;

public class AddIKeeperActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.sejjoh.ikeeper.ID";
    public static final String EXTRA_TITLE = "com.sejjoh.ikeeper.TITLE";
    public static final String EXTRA_DESCRIPTION = "com.sejjoh.ikeeper.DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.sejjoh.ikeeper.PRIORITY";
    public static final String EXTRA_DATE = "com.sejjoh.ikeeper.DATE";
    private static final String TAG = "AddActivity";
    private EditText edtTitle, edtDescription;
    private NumberPicker priorityPicker;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ikeeper_menu, menu);
        return true;

    }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.save:
                    Log.d(TAG, "onOptionsItemSelected:data");
                    saveNote();
                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }

        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_i_keeper);
        edtTitle = findViewById(R.id.text_Title);
        edtDescription = findViewById(R.id.text_desc);
        priorityPicker = findViewById(R.id.number_picker);

        priorityPicker.setMinValue(1);
        priorityPicker.setMaxValue(10);

        MaterialToolbar myToolbar = (MaterialToolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
            Intent intent = getIntent();
            if (intent.hasExtra(EXTRA_ID)) {
                setTitle("Edit Note");
                edtTitle.setText(intent.getStringExtra(EXTRA_TITLE));
                edtDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
                priorityPicker.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
            } else {
                setTitle("Add your Keeper");
            }
        }


    }

    private void saveNote() {
        String title = edtTitle.getText().toString().trim();
        String description = edtDescription.getText().toString().trim();
        int priority = priorityPicker.getValue();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if (id != -1){
            data.putExtra(EXTRA_ID,id);
        }
        setResult(RESULT_OK, data);
        finish();


    }



}