package com.blakestiller.mynotes;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.blakestiller.mynotes.Comment;

import java.util.List;


public class MainActivity extends ListActivity {
    private CommentsDataSource datasource;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Show ActionBar
        ActionBar actionBar = getActionBar();

       // ListView notes = (ListView) findViewById(R.id.list);
        datasource = new CommentsDataSource(this);
        datasource.open();
        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        List<Comment> values = datasource.getAllComments();
        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void addNote(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        Comment comment = null;
        // Hide Numpad onClick - when the button is pressed
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        // get all the user input from the EditText fields
        EditText addNote = (EditText) findViewById(R.id.editText);
        String comments = addNote.getText().toString();
        // save the new comment to the database
        comment = (Comment) datasource.createComment(comments);
        adapter.add(comment);
        adapter.notifyDataSetChanged();
    }
}
