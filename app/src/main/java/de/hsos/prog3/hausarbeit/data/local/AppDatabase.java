package de.hsos.prog3.hausarbeit.data.local;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.hsos.prog3.hausarbeit.data.dao.GroupDao;
import de.hsos.prog3.hausarbeit.data.dao.OutputDao;
import de.hsos.prog3.hausarbeit.data.dao.PersonDao;
import de.hsos.prog3.hausarbeit.data.model.Group;
import de.hsos.prog3.hausarbeit.data.model.Output;
import de.hsos.prog3.hausarbeit.data.model.Person;

@Database(entities = {Group.class, Person.class, Output.class}, version = 44, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static AppDatabase instance;

    public abstract GroupDao groupDao();
    public abstract PersonDao personDao();
    public abstract OutputDao outputDao();
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "shootoutt").fallbackToDestructiveMigration().addCallback(roomCallBack).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            Executors.newSingleThreadExecutor().execute(() -> {
                GroupDao groupDao = instance.groupDao();
                PersonDao personDao = instance.personDao();
                OutputDao outputDao = instance.outputDao(); // Add this line


                Group group1 = new Group("Group 1");
                Group group2 = new Group("Group 2");
                long group1Id = groupDao.insertGroup(group1); // Only insert once
                long group2Id = groupDao.insertGroup(group2); // Only insert once

                Person person1 = new Person("Person 1", "person1@example.com", (int) group1Id);
                Person person2 = new Person("Person 2", "person2@example.com", (int) group1Id);
                Person person3 = new Person("Person 3", "person3@example.com", (int) group2Id);
                long personId1 = personDao.insertPerson(person1); // Only insert once
                long personId2 = personDao.insertPerson(person2); // Only insert once
                long personId3 = personDao.insertPerson(person3); // Only insert once

                Output output1 = new Output((int) group1Id, (int) personId1, "Output 1", 50, new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
                Output output2 = new Output((int) group1Id, (int) personId2, "Output 2", 100, new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
                long outputId1 = outputDao.insert(output1);
                long outputId2 = outputDao.insert(output2);

                Log.d("Database Creation", "Inserted groups with ids: " + group1Id + ", " + group2Id);
                Log.d("Database Creation", "Inserted persons with ids: " + personId1 + ", " + personId2 + ", " + personId3);
                Log.d("Database Creation", "Inserted outputs with ids: " + outputId1 + ", " + outputId2);

                Log.d("Database Creation", "Inserted groups with ids: " + group1Id + ", " + group2Id);
                Log.d("Database Creation", "Inserted persons with ids: " + personId1 + ", " + personId2 + ", " + personId3);

            });
        }
    };
}

