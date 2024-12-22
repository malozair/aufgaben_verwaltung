package de.hsos.prog3.hausarbeit.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hsos.prog3.hausarbeit.R;
import de.hsos.prog3.hausarbeit.data.model.PersonCheck;

public class PersonCheckAdapter extends RecyclerView.Adapter<PersonCheckAdapter.PersonCheckViewHolder> {
    private List<PersonCheck> persons = new ArrayList<>();

    @NonNull
    @Override
    public PersonCheckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person_check_item, parent, false);
        return new PersonCheckViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonCheckViewHolder holder, int position) {
        PersonCheck currentPerson = persons.get(position);
        holder.checkBoxPerson.setText(currentPerson.getPerson().getName());
        holder.checkBoxPerson.setChecked(currentPerson.isChecked());
        holder.checkBoxPerson.setOnCheckedChangeListener((buttonView, isChecked) -> {
            persons.get(position).setChecked(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }
    public void setPersonChecks(List<PersonCheck> personChecks) {
        this.persons = personChecks;
        notifyDataSetChanged();
    }


    public void setPersons(List<PersonCheck> persons) {
        this.persons = persons;
        notifyDataSetChanged();
    }

    class PersonCheckViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBoxPerson;

        public PersonCheckViewHolder(View itemView) {
            super(itemView);
            checkBoxPerson = itemView.findViewById(R.id.checkBox);
        }
    }
    public List<PersonCheck> getPersonChecks() {
        return persons;
    }
}
