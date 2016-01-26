package com.example.dominiquer.dominiquereliford_todo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    //File to add new to-do list items to
    public static final String NEW_ITEM_FILENAME = "newItems.txt";

    //ArrayList of all the to-do list items
    private ArrayList<String> todolist;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connect ListView to adapter
        ListView todo = (ListView) findViewById(R.id.todo_list);
        todo.setOnItemLongClickListener(this);
        adapter = new ArrayAdapter<String>(this, R.layout.activity_main,
                R.id.textView, todolist);
        todo.setAdapter(adapter);
    }

    /*
     * Add a new item to the to-do list text file
     */
    public void addItem(View view){
        EditText newItem = (EditText) findViewById(R.id.addToDo);
        String item = newItem.getText().toString();

        try {
            PrintStream out = new PrintStream(openFileOutput(NEW_ITEM_FILENAME, MODE_APPEND));
            out.println("New Item: " + item);
            out.close();
        }
        catch (IOException ioe){
            Log.wtf("NewItem", ioe);
        }
    }

    /*
     * Read items from the to-do list and add it
     * to the ArrayList
     */
    private void readFile() {
        Scanner scan = new Scanner(getResources().openRawResource(R.raw.todo));
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            todolist.add(line);
        }
    }

    /*
     * Remove item from list when user holds and clicks on an item in the list
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        ListView todo = (ListView) findViewById(R.id.todo_list);
        todo.setAdapter(adapter);

        todolist.remove(position);
        adapter.notifyDataSetChanged();

        return false;
    }

    /*
     * Save instances of app
     */
    protected void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);

        bundle.putStringArrayList("todo", todolist);
    }

    /*
     * Restore instances of app
     */
    protected void onRestoreInstanceState(Bundle bundle){
        super.onRestoreInstanceState(bundle);

        todolist = bundle.getStringArrayList("todo");
    }
}
