package de.hsos.prog3.hausarbeit.data.repository;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import de.hsos.prog3.hausarbeit.data.dao.PersonDao;
import de.hsos.prog3.hausarbeit.data.local.AppDatabase;
import de.hsos.prog3.hausarbeit.data.model.Person;

public class PersonRepository {


    private PersonDao personDao;

    private static volatile PersonRepository INSTANCE = null;

    public static PersonRepository getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (PersonRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PersonRepository(application);
                }
            }
        }
        return INSTANCE;
    }
    public PersonRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        personDao = database.personDao();
    }

    public void insertPerson(Person person) {
        AppDatabase.databaseWriteExecutor.execute(() -> personDao.insertPerson(person));
    }

    public void updatePerson(Person person) {
        AppDatabase.databaseWriteExecutor.execute(() -> personDao.updatePerson(person));
    }

    public void deletePerson(Person person) {
        AppDatabase.databaseWriteExecutor.execute(() -> personDao.deletePerson(person));
    }

    public LiveData<List<Person>> getGroupMembers(int groupId) {
        return personDao.getGroupMembers(groupId);
    }
    public LiveData<Person> getPersonById(int personId) {
        return personDao.getPersonById(personId);
    }


}
