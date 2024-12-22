package de.hsos.prog3.hausarbeit.ui.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import de.hsos.prog3.hausarbeit.R;
import de.hsos.prog3.hausarbeit.data.model.Person;
import de.hsos.prog3.hausarbeit.ui.viewModel.PersonViewModel;

public class EditPersonActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail;
    private PersonViewModel personViewModel;
    private int personId;
    private Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Edit or Delete Person ");
        personId = getIntent().getIntExtra("PERSON_ID", -1);

        editTextName = findViewById(R.id.editPersonName);
        editTextEmail = findViewById(R.id.editPersonEmail);
        personViewModel = new ViewModelProvider(this).get(PersonViewModel.class);

        personViewModel.getPersonById(personId).observe(this, new Observer<Person>() {
            @Override
            public void onChanged(@Nullable final Person personResult) {
                // Update the cached copy of the words in the adapter.
                if (personResult != null) {
                    person = personResult;
                    editTextName.setText(person.getName());
                    editTextEmail.setText(person.getEmail());
                }
            }
        });


        Button updateButton = findViewById(R.id.buttonUpdate);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedName = editTextName.getText().toString();
                String updatedEmail = editTextEmail.getText().toString();

                if (updatedName.isEmpty() || updatedEmail.isEmpty()) {
                    Toast.makeText(EditPersonActivity.this, "Please insert a name and email", Toast.LENGTH_SHORT).show();
                    return;
                }

                person.setName(updatedName);
                person.setEmail(updatedEmail);
                personViewModel.update(person);
                Toast.makeText(EditPersonActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
        Button deleteButton = findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a confirmation dialog to the user before deleting the person
                new AlertDialog.Builder(EditPersonActivity.this)
                        .setTitle("Delete Person")
                        .setMessage("Are you sure you want to delete this person?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // User wants to delete the person
                                personViewModel.delete(person);
                                Toast.makeText(EditPersonActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                                finish(); // return to the previous Activity
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
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
