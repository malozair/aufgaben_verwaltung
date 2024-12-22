package de.hsos.prog3.hausarbeit.data.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class GroupWithPersons {
    @Embedded
    public Group group;

    @Relation(parentColumn = "id", entityColumn = "groupId", entity = Person.class)
    public List<Person> personList;
}
