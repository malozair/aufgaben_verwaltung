package de.hsos.prog3.hausarbeit.ui.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import de.hsos.prog3.hausarbeit.data.model.Group;
import de.hsos.prog3.hausarbeit.data.model.GroupWithPersons;
import de.hsos.prog3.hausarbeit.data.repository.GroupRepository;

public class GroupViewModel extends AndroidViewModel {

    private GroupRepository groupRepository;
    private LiveData<List<Group>> allGroups;
    private LiveData<List<GroupWithPersons>> groupWithPersons;


    public GroupViewModel(@NonNull Application application) {
        super(application);
        groupRepository = new GroupRepository(application);
        allGroups = groupRepository.getAllGroups();
        groupWithPersons=groupRepository.getGroupWithPersons();
    }

    public void insert(Group group) {
        groupRepository.insertGroup(group);
    }

    public void update(Group group) {
        groupRepository.updateGroup(group);
    }

    public void delete(Group group) {
        groupRepository.deleteGroup(group);
    }

    public LiveData<List<Group>> getAllGroups() {
        return allGroups;
    }

}

