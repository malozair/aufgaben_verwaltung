package de.hsos.prog3.hausarbeit.data.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Group {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo (name = "name")
    private String name;

    @Ignore
    private List<Person> personList;

    public Group(String name) {
        this.name = name;
       this.personList = new ArrayList<>();
    }



    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return   this.name;
    }

    public List<Person> getPersonList() {return this.personList;}
}
