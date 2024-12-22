package de.hsos.prog3.hausarbeit.ui.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hsos.prog3.hausarbeit.R;
import de.hsos.prog3.hausarbeit.data.model.Output;
import de.hsos.prog3.hausarbeit.data.model.OutputWithBuyerName;
import de.hsos.prog3.hausarbeit.ui.viewModel.OutputViewModel;

public class OutputAdapter extends RecyclerView.Adapter<OutputAdapter.OutputHolder> {
    private List<OutputWithBuyerName> outputs = new ArrayList<>();
    private OutputViewModel outputViewModel; // Add OutputViewModel to the adapter

    public OutputAdapter(OutputViewModel outputViewModel) {
        this.outputViewModel = outputViewModel;
    }



    @NonNull
    @Override
    public OutputHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.output_item, parent, false);
        return new OutputHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OutputHolder holder, int position) {
        OutputWithBuyerName currentOutput = outputs.get(position);
        holder.textViewBuyerName.setText(currentOutput.getBuyerName());
        holder.textViewOutputName.setText(currentOutput.getOutputName());
        holder.textViewAmount.setText(String.valueOf(currentOutput.getAmount()));
        holder.textViewDate.setText(currentOutput.getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Delete Output")
                        .setMessage("Are you sure you want to delete this output?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            // ...
                            public void onClick(DialogInterface dialog, int which) {
                                // Create an Output object using the OutputWithBuyerName object
                                Output output = new Output(currentOutput.getGroupId(),
                                        currentOutput.getPersonId(),
                                        currentOutput.getOutputName(),
                                        currentOutput.getAmount(),
                                        currentOutput.getDate());
                                output.setId(currentOutput.getId());  // changed from getId() to getOutputId()
                                // Delete the selected output
                                outputViewModel.delete(output);
                            }
// ...

                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return outputs.size();
    }

    public void setOutputs(List<OutputWithBuyerName> outputs) {
        this.outputs = outputs;
        notifyDataSetChanged();
    }

    class OutputHolder extends RecyclerView.ViewHolder {
        private TextView textViewOutputName;
        private TextView textViewAmount;
        private TextView textViewDate;
        private TextView textViewBuyerName;

        public OutputHolder(View itemView) {
            super(itemView);
            textViewBuyerName = itemView.findViewById(R.id.output_buyer_name);
            textViewOutputName = itemView.findViewById(R.id.output_item_name);
            textViewAmount = itemView.findViewById(R.id.output_total_cost);
            textViewDate = itemView.findViewById(R.id.output_date);
        }
    }
}
