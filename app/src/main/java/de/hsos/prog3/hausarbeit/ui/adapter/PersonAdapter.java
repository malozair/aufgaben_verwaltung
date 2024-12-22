package de.hsos.prog3.hausarbeit.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hsos.prog3.hausarbeit.R;
import de.hsos.prog3.hausarbeit.data.model.Person;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonHolder> {
    private List<Person> persons = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public PersonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person_item, parent, false);

        return new PersonHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonHolder holder, int position) {
        Person currentPerson = persons.get(position);
        holder.textViewName.setText(currentPerson.getName());
        holder.textViewEmail.setText(currentPerson.getEmail());
        holder.textViewBalance.setText(String.valueOf(currentPerson.getBalance()));

    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    public void setPersonList(List<Person> persons) {
        this.persons = persons;
        notifyDataSetChanged();
    }

    class PersonHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewEmail;
        private TextView textViewBalance;

        public PersonHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.person_text);
            textViewEmail = itemView.findViewById(R.id.person_email_text);
            textViewBalance = itemView.findViewById(R.id.text_view_balance);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(persons.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Person person);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
