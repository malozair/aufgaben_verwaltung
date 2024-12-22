package de.hsos.prog3.hausarbeit.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import de.hsos.prog3.hausarbeit.data.dao.OutputDao;
import de.hsos.prog3.hausarbeit.data.local.AppDatabase;
import de.hsos.prog3.hausarbeit.data.model.Output;
import de.hsos.prog3.hausarbeit.data.model.OutputWithBuyerName;

public class OutputRepository {

    private OutputDao outputDao;
    private static volatile OutputRepository INSTANCE = null;

    public static OutputRepository getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (OutputRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OutputRepository(application);
                }
            }
        }
        return INSTANCE;
    }

    private OutputRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        outputDao = database.outputDao();
    }

    public void insertOutput(Output output) {
        AppDatabase.databaseWriteExecutor.execute(() -> outputDao.insert(output));
    }

    public void updateOutput(Output output) {
        AppDatabase.databaseWriteExecutor.execute(() -> outputDao.update(output));
    }

    public void deleteOutput(Output output) {
        AppDatabase.databaseWriteExecutor.execute(() -> outputDao.deleteOutput(output));
    }

    public LiveData<List<Output>> getOutputsForGroup(int groupId) {
        return outputDao.getOutputsForGroup(groupId);
    }

    public LiveData<List<OutputWithBuyerName>> getAllOutputsWithBuyerNames(int groupId) {
        return outputDao.getAllOutputsWithBuyerNames(groupId);
    }

//    public LiveData<Output> getOutputById(int outputId) {
//        return outputDao.getOutputById(outputId);
//    }
}
