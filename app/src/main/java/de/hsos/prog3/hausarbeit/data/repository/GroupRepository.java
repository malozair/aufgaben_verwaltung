package de.hsos.prog3.hausarbeit.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import de.hsos.prog3.hausarbeit.data.dao.GroupDao;
import de.hsos.prog3.hausarbeit.data.dao.PersonDao;
import de.hsos.prog3.hausarbeit.data.local.AppDatabase;
import de.hsos.prog3.hausarbeit.data.model.Group;
import de.hsos.prog3.hausarbeit.data.model.GroupWithPersons;
import de.hsos.prog3.hausarbeit.data.model.Person;

public class GroupRepository {

    private GroupDao groupDao;
    private PersonDao personDao;
    private LiveData<List<Group>> allGroups;
    private LiveData<List<GroupWithPersons>> groupWithPersons;
    private static volatile GroupRepository INSTANCE = null;


    public static GroupRepository getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (GroupRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GroupRepository(application);
                }
            }
        }
        return INSTANCE;
    }
    public GroupRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        groupDao = database.groupDao();
        personDao = database.personDao();
        allGroups = groupDao.getAllGroups();
        groupWithPersons = groupDao.getGroupsWithPersons();
    }

    public void insertGroup(Group group) {
        CompletableFuture.runAsync(() -> {
            long groupId = groupDao.insertGroup(group);
            List<Person> persons = group.getPersonList();
            for (Person person : persons) {
                person.setGroupId((int) groupId);
                personDao.insertPerson(person);
            }
        }, AppDatabase.databaseWriteExecutor);
    }

    public void updateGroup(Group group) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            groupDao.updateGroup(group);
            List<Person> persons = group.getPersonList();
            for (Person person : persons) {
                personDao.updatePerson(person);
            }
        });
    }

    public void deleteGroup(Group group) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            groupDao.deleteGroup(group);
            List<Person> persons = group.getPersonList();
            for (Person person : persons) {
                personDao.deletePerson(person);
            }
        });
    }

    public LiveData<List<Group>> getAllGroups() {
        return allGroups;
    }

    public LiveData<List<GroupWithPersons>> getGroupWithPersons() {
        return groupWithPersons;
    }


}
