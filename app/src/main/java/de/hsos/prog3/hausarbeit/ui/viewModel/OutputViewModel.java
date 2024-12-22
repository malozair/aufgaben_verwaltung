package de.hsos.prog3.hausarbeit.ui.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import de.hsos.prog3.hausarbeit.data.model.Output;
import de.hsos.prog3.hausarbeit.data.model.OutputWithBuyerName;
import de.hsos.prog3.hausarbeit.data.repository.OutputRepository;

public class OutputViewModel extends AndroidViewModel {

    private OutputRepository repository;

    public OutputViewModel(@NonNull Application application) {
        super(application);
        repository = OutputRepository.getInstance(application);
    }

    public void insert(Output output) {
        repository.insertOutput(output);
    }

    public void update(Output output) {
        repository.updateOutput(output);
    }

    public void delete(Output output) {
        repository.deleteOutput(output);
    }

    public LiveData<List<Output>> getOutputsForGroup(int groupId) {
        return repository.getOutputsForGroup(groupId);
    }
    public LiveData<List<OutputWithBuyerName>> getAllOutputsWithBuyerNames(int groupId) {
        return repository.getAllOutputsWithBuyerNames(groupId);
    }

//    public LiveData<Output> getOutputById(int outputId) {
//        return repository.getOutputById(outputId);
//    }
}
