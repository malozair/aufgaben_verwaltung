package de.hsos.prog3.hausarbeit.ui.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import de.hsos.prog3.hausarbeit.R;
import de.hsos.prog3.hausarbeit.data.model.Person;
import de.hsos.prog3.hausarbeit.ui.adapter.PersonAdapter;
import de.hsos.prog3.hausarbeit.ui.viewModel.PersonViewModel;

public class OverviewFragment extends Fragment {

    private PersonViewModel personViewModel;
    private RecyclerView recyclerView;
    private PersonAdapter adapter;
    private int groupId;

    public OverviewFragment(int groupId) {
        this.groupId = groupId;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        personViewModel = new ViewModelProvider(requireActivity(),
                new ViewModelProvider.Factory() {
                    @NonNull
                    @Override
                    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                        return (T) new PersonViewModel(requireActivity().getApplication());
                    }
                }).get(PersonViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewPersons);
        adapter = new PersonAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // Observe data
        personViewModel.getGroupMembers(groupId).observe(getViewLifecycleOwner(), new Observer<List<Person>>() {
            @Override
            public void onChanged(List<Person> people) {
                // Update the cached copy of the words in the adapter.
                adapter.setPersonList(people);
            }
        });

        adapter.setOnItemClickListener(new PersonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Person person) {
                Intent intent = new Intent(getActivity(), EditPersonActivity.class);
                intent.putExtra("PERSON_ID", person.getId());
                startActivity(intent);
            }
        });
    }
}