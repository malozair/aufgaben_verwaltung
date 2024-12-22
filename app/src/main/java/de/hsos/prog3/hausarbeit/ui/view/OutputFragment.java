package de.hsos.prog3.hausarbeit.ui.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hsos.prog3.hausarbeit.R;
import de.hsos.prog3.hausarbeit.data.model.OutputWithBuyerName;
import de.hsos.prog3.hausarbeit.ui.adapter.OutputAdapter;
import de.hsos.prog3.hausarbeit.ui.viewModel.OutputViewModel;

public class OutputFragment extends Fragment {
    private OutputViewModel outputViewModel;
    private OutputAdapter outputAdapter;

    private int groupId;

    public OutputFragment(int groupId){
        this.groupId=groupId;
    }




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_output, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewOutputs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);

        outputViewModel = new ViewModelProvider(this).get(OutputViewModel.class);
        outputAdapter = new OutputAdapter(outputViewModel);
        recyclerView.setAdapter(outputAdapter);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        outputViewModel = new ViewModelProvider(this).get(OutputViewModel.class);
        outputViewModel.getAllOutputsWithBuyerNames(groupId).observe(getViewLifecycleOwner(), new Observer<List<OutputWithBuyerName>>() {
            @Override
            public void onChanged(@Nullable List<OutputWithBuyerName> outputs) {
                // Update RecyclerView
                updateOutputs(outputs);
            }
        });

    }

    public void updateOutputs(List<OutputWithBuyerName> outputs) {
        outputAdapter.setOutputs(outputs);
    }
}