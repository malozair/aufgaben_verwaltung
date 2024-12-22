package de.hsos.prog3.hausarbeit.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Patterns;

import de.hsos.prog3.hausarbeit.R;
import de.hsos.prog3.hausarbeit.data.model.Person;
import de.hsos.prog3.hausarbeit.ui.viewModel.PersonViewModel;

public class AddPersonActivity extends AppCompatActivity {
    private EditText personNameInput;
    private EditText personEmailInput;
    private Button savePersonButton;
    private PersonViewModel personViewModel;
    private int groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add Person");
        personNameInput = findViewById(R.id.person_name_input);
        personEmailInput = findViewById(R.id.person_email_input);
        savePersonButton = findViewById(R.id.save_person_button);

        personViewModel = new ViewModelProvider(this).get(PersonViewModel.class);

        groupId = getIntent().getIntExtra("GROUP_ID", -1);

        savePersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String personName = personNameInput.getText().toString();
                String personEmail = personEmailInput.getText().toString();

                if (TextUtils.isEmpty(personName) || TextUtils.isEmpty(personEmail)) {
                    Toast.makeText(AddPersonActivity.this, "Please enter both name and email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(personEmail).matches()) {
                    Toast.makeText(AddPersonActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();

                    return;
                }

                Person newPerson = new Person(personName, personEmail, groupId);
                personViewModel.insert(newPerson);

                Toast.makeText(AddPersonActivity.this, "Person added successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
