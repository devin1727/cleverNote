package mobileprogramming.devinwinardi.clevernote.ui.task;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import id.ac.ui.cs.mobileprogramming.devinwinardi.clevernote.R;

public class AddTaskActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "EXTRA_ID";
    public static final String EXTRA_TITLE =
            "EXTRA_TITLE";
    private EditText editTextTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        editTextTitle = findViewById(R.id.edit_text_title);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        setTitle(getResources().getString(R.string.add_task));
    }
    private void saveTask() {
        String title = editTextTitle.getText().toString();
        if (title.trim().isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.empty_field), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1){
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_task_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_task:
                saveTask();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
