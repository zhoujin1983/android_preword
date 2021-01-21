package com.example.simple_jin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import org.apache.commons.io.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> items;

    Button btn;
    EditText edtx;
    RecyclerView rvlist;
    itemadapter itemsadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);
        edtx = findViewById(R.id.edtx);
        rvlist = findViewById(R.id.rvlist);

//        items = new ArrayList<>();
//        items.add("play soccer");
//        items.add("eat lunch");
//        items.add("Go shopping");
        loadItems();

        itemadapter.OnLongClickListener onLongClickListener = new itemadapter.OnLongClickListener(){

            @Override
            public void OnItemLongClicked(int position) {
                items.remove(position);
                itemsadapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };


        itemsadapter = new itemadapter(items, onLongClickListener);
        rvlist.setAdapter(itemsadapter);
        rvlist.setLayoutManager(new LinearLayoutManager(this));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = edtx.getText().toString();
                items.add(todoItem);
                itemsadapter.notifyItemInserted(items.size()-1);
                edtx.setText("");
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }


    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }

    private void loadItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e){
            Log.e("MainAcitivity", "Error reading items", e);
            items = new ArrayList<>();
        }
    }

    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items", e);
        }

    }
}