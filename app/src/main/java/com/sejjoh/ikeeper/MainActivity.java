package com.sejjoh.ikeeper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="MainActivity" ;
    private KeeperViewModel mKeeperViewModel;
    public static final int ADD_NOTE = 1;
    public static final int EDIT_NOTE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MaterialToolbar myToolbar = (MaterialToolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        FloatingActionButton floatingActionButton =findViewById(R.id.add);
        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,AddIKeeperActivity.class);
            startActivityForResult(intent,ADD_NOTE);

        });
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        KeeperAdapter adapter = new KeeperAdapter();
        Log.d(TAG, "onCreate: data");
        recyclerView.setAdapter(adapter);

        mKeeperViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance
                ((Application) this.getApplicationContext())).get(KeeperViewModel.class);

        mKeeperViewModel.getAlliKeepers().observe(this, iKeeperEntities -> {
            Log.d(TAG, "onChanged: getting data...");
            adapter.submitList(iKeeperEntities);
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mKeeperViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note deleted!", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemSelectedListener(KeeperEntity -> {
            Intent intent = new Intent(MainActivity.this,AddIKeeperActivity.class);
            intent.putExtra(AddIKeeperActivity.EXTRA_TITLE, KeeperEntity.getTitle());
            intent.putExtra(AddIKeeperActivity.EXTRA_DESCRIPTION, KeeperEntity.getDescription());
            intent.putExtra(AddIKeeperActivity.EXTRA_PRIORITY, KeeperEntity.getPriority());
            intent.putExtra(AddIKeeperActivity.EXTRA_ID, KeeperEntity.getId());
            startActivityForResult(intent,EDIT_NOTE);
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==ADD_NOTE && resultCode == RESULT_OK){
            String title = data.getStringExtra(AddIKeeperActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddIKeeperActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddIKeeperActivity.EXTRA_PRIORITY,1);


            KeeperEntity KeeperEntity =new KeeperEntity(title,description,priority);
            mKeeperViewModel.insert(KeeperEntity);
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();

        }else if (requestCode ==EDIT_NOTE && resultCode == RESULT_OK){

            int id = data.getIntExtra(AddIKeeperActivity.EXTRA_ID,-1);

            if (id == -1){
                Toast.makeText(this, "Note can't be updated!", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddIKeeperActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddIKeeperActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddIKeeperActivity.EXTRA_PRIORITY,1);
            KeeperEntity keeperEntity = new KeeperEntity(title,description,priority);
            keeperEntity.setId(id);
            mKeeperViewModel.update(keeperEntity);


        }
        else {
            Toast.makeText(this, "Note not saved!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete_note,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                mKeeperViewModel.deleteAll();
                Toast.makeText(this, "All notes Deleted Successfully!", Toast.LENGTH_SHORT).show();
                return  true;
            default:
            return super.onOptionsItemSelected(item);

        }
    }
}