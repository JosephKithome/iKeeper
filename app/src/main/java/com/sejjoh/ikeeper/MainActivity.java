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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="MainActivity" ;
    private iKeeperViewModel mIKeeperViewModel;
    public static final int ADD_NOTE = 1;
    public static final int EDIT_NOTE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton floatingActionButton =findViewById(R.id.add);
        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,AddIKeeperActivity.class);
            startActivityForResult(intent,ADD_NOTE);

        });
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        IkeeperAdapter adapter = new IkeeperAdapter();
        Log.d(TAG, "onCreate: data");
        recyclerView.setAdapter(adapter);

        mIKeeperViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance
                ((Application) this.getApplicationContext())).get(iKeeperViewModel.class);

        mIKeeperViewModel.getAlliKeepers().observe(this, new Observer<List<iKeeperEntity>>() {
            @Override
            public void onChanged(List<iKeeperEntity> iKeeperEntities) {
                Log.d(TAG, "onChanged: getting data...");
                adapter.setIkeeper(iKeeperEntities);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mIKeeperViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note deleted!", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemSelectedListener(new IkeeperAdapter.onItemClickListener() {
            @Override
            public void onItemClick(iKeeperEntity iKeeperEntity) {
                Intent intent = new Intent(MainActivity.this,AddIKeeperActivity.class);
                intent.putExtra(AddIKeeperActivity.EXTRA_TITLE,iKeeperEntity.getTitle());
                intent.putExtra(AddIKeeperActivity.EXTRA_DESCRIPTION,iKeeperEntity.getDescription());
                intent.putExtra(AddIKeeperActivity.EXTRA_PRIORITY,iKeeperEntity.getPriority());
                intent.putExtra(AddIKeeperActivity.EXTRA_ID,iKeeperEntity.getId());
                startActivityForResult(intent,EDIT_NOTE);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==ADD_NOTE && resultCode == RESULT_OK){
            String title = data.getStringExtra(AddIKeeperActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddIKeeperActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddIKeeperActivity.EXTRA_PRIORITY,1);


            iKeeperEntity iKeeperEntity =new iKeeperEntity(title,description,priority);
            mIKeeperViewModel.insert(iKeeperEntity);
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
            iKeeperEntity keeperEntity = new iKeeperEntity(title,description,priority);
            keeperEntity.setId(id);
            mIKeeperViewModel.update(keeperEntity);


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
                mIKeeperViewModel.deleteAll();
                Toast.makeText(this, "All notes Deleted Successfully!", Toast.LENGTH_SHORT).show();
                return  true;
            default:
            return super.onOptionsItemSelected(item);

        }
    }
}