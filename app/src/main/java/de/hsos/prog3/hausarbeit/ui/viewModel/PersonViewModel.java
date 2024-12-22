package de.hsos.prog3.hausarbeit.ui.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import de.hsos.prog3.hausarbeit.data.model.Person;
import de.hsos.prog3.hausarbeit.data.repository.PersonRepository;

public class PersonViewModel extends AndroidViewModel {

    private final PersonRepository repository;

    public PersonViewModel(@NonNull Application application) {
        super(application);
        repository = PersonRepository.getInstance(application);
    }
    public LiveData<List<Person>> getGroupMembers(int groupId) {
        return repository.getGroupMembers(groupId);
    }

    public void insert(Person person) {
        repository.insertPerson(person);
    }

    public void update(Person person) {
        repository.updatePerson(person);
    }

    public void delete(Person person) {
        repository.deletePerson(person);
    }
    public LiveData<Person> getPersonById(int personId) {
        return repository.getPersonById(personId);
    }

}

