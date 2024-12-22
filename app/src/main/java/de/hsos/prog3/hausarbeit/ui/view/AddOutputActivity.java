package de.hsos.prog3.hausarbeit.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import de.hsos.prog3.hausarbeit.R;
import de.hsos.prog3.hausarbeit.data.model.Group;
import de.hsos.prog3.hausarbeit.data.model.Output;
import de.hsos.prog3.hausarbeit.data.model.Person;
import de.hsos.prog3.hausarbeit.data.model.PersonCheck;
import de.hsos.prog3.hausarbeit.ui.adapter.PersonCheckAdapter;
import de.hsos.prog3.hausarbeit.ui.viewModel.GroupViewModel;
import de.hsos.prog3.hausarbeit.ui.viewModel.OutputViewModel;
import de.hsos.prog3.hausarbeit.ui.viewModel.PersonViewModel;

public class AddOutputActivity extends AppCompatActivity {

    private EditText editTextOutputName, editTextAmount;
    private Spinner spinnerGroup, spinnerPerson;
    private Button buttonSave;
    private GroupViewModel groupViewModel;
    private PersonViewModel personViewModel;
    private OutputViewModel outputViewModel;
    private List<PersonCheck> personChecks = new ArrayList<>();
    private Executor executor = Executors.newSingleThreadExecutor();
    private ArrayAdapter<Group> groupAdapter;
    private ArrayAdapter<Person> personAdapter;
    private RecyclerView recyclerViewPersons;
    private PersonCheckAdapter personCheckAdapter;
    Group selectedGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_output);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add Output");
        editTextOutputName = findViewById(R.id.edt_output_name);
        editTextAmount = findViewById(R.id.edt_amount);
        spinnerGroup = findViewById(R.id.spinner_groups);
        spinnerPerson = findViewById(R.id.spinner_buyer);
        buttonSave = findViewById(R.id.btn_add_output);

        personCheckAdapter = new PersonCheckAdapter();
        recyclerViewPersons = findViewById(R.id.recycler_view_persons);
        recyclerViewPersons.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPersons.setAdapter(personCheckAdapter);

        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        personViewModel = new ViewModelProvider(this).get(PersonViewModel.class);
        outputViewModel = new ViewModelProvider(this).get(OutputViewModel.class);

        groupAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        groupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGroup.setAdapter(groupAdapter);

        personAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        personAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPerson.setAdapter(personAdapter);

        groupViewModel.getAllGroups().observe(this, new Observer<List<Group>>() {
            @Override
            public void onChanged(List<Group> groups) {
                groupAdapter.clear();
                groupAdapter.addAll(groups);
            }
        });

        spinnerGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGroup = (Group) spinnerGroup.getSelectedItem();

                personViewModel.getGroupMembers(selectedGroup.getId()).observe(AddOutputActivity.this, new Observer<List<Person>>() {
                    @Override
                    public void onChanged(List<Person> persons) {
                        personChecks.clear();
                        for (Person person : persons) {
                            personChecks.add(new PersonCheck(person, false));
                        }
                        personAdapter.clear();
                        personAdapter.addAll(persons);
                        personCheckAdapter.setPersons(personChecks);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOutput();
            }
        });
    }

    private void saveOutput() {
        String outputName = editTextOutputName.getText().toString();
        String amountString = editTextAmount.getText().toString();

        if (TextUtils.isEmpty(outputName) || TextUtils.isEmpty(amountString)) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(amountString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
            return;
        }

        if (personChecks.isEmpty()) {
            Toast.makeText(this, "No persons in the group", Toast.LENGTH_SHORT).show();
            return;
        }

        Group selectedGroup = (Group) spinnerGroup.getSelectedItem();
        Person selectedPerson = (Person) spinnerPerson.getSelectedItem();

        int groupId = selectedGroup.getId();
        int personId = selectedPerson.getId();
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // count selected persons
        long selectedCount = personChecks.stream().filter(PersonCheck::isChecked).count();

        if (selectedCount == 0) {
            Toast.makeText(this, "No persons selected", Toast.LENGTH_SHORT).show();
            return;
        }

        executor.execute(new Runnable() {
            @Override
            public void run() {
                Output output = new Output(groupId, personId, outputName, amount, date);
                outputViewModel.insert(output);

                int balanceChange = amount / (int)selectedCount; // share of each selected person

                for (PersonCheck personCheck : personChecks) {
                    Person person = personCheck.getPerson();
                    if (personCheck.isChecked()) {
                        if (person.getId() == personId) {
                            // the buyer's balance increases by the amount - share of others
                            person.setBalance(person.getBalance() + amount - balanceChange);
                        } else {
                            // selected persons' balance decreases by their share
                            person.setBalance(person.getBalance() - balanceChange);
                        }
                        personViewModel.update(person);
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AddOutputActivity.this, "Output added", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
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
