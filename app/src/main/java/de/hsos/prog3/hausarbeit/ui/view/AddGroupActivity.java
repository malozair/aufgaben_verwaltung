package de.hsos.prog3.hausarbeit.ui.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import java.util.Objects;

import de.hsos.prog3.hausarbeit.R;

public class AddGroupActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "com.example.newupdate.EXTRA_TITLE";
    public static final String EXTRA_ID = "com.example.newupdate.EXTRA_ID";
    private EditText editTextTitle;
    private int groupId = -1;
    private Button updateGroupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        editTextTitle = findViewById(R.id.edit_text_titel);
        updateGroupButton = findViewById(R.id.save_button); // add this line

        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Group");


        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID) && intent.hasExtra(EXTRA_TITLE)) {
            groupId = intent.getIntExtra(EXTRA_ID, -1);
            String groupTitle = intent.getStringExtra(EXTRA_TITLE);
            if (!TextUtils.isEmpty(groupTitle)) {
                editTextTitle.setText(groupTitle);
            } else {
                Toast.makeText(this, "Group Title is empty or not provided", Toast.LENGTH_SHORT).show();
            }
        }

        // add these lines
        updateGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGroup();
            }
        });
    }

    private void saveGroup() {
        String title = editTextTitle.getText().toString().trim();

        if (title.isEmpty()) {
            Toast.makeText(this, "Please insert a title", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_ID, groupId);
        setResult(RESULT_OK, data);
        finish();
    }


}