package mobileprogramming.devinwinardi.clevernote.ui.task;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import id.ac.ui.cs.mobileprogramming.devinwinardi.clevernote.R;

public class AddSubtaskActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "EXTRA_ID";
    public static final String EXTRA_TITLE =
            "EXTRA_TITLE";
    private EditText editTextTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subtask);
        editTextTitle = findViewById(R.id.edit_text_title);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        setTitle(getResources().getString(R.string.add_subtask));
    }
}
