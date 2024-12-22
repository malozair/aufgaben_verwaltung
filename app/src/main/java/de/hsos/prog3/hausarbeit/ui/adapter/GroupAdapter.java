package de.hsos.prog3.hausarbeit.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hsos.prog3.hausarbeit.R;
import de.hsos.prog3.hausarbeit.data.model.Group;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupHolder> {
    private List<Group> groupList=new ArrayList<>();
    private OnGroupListener onGroupListener;

    @NonNull
    @Override
    public GroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View itemView = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.group_item,parent,false);

        return new GroupHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupHolder holder, @SuppressLint("RecyclerView") int position) {

        Group currentGroup = groupList.get(position);
        holder.textViewTitel.setText(currentGroup.getName());
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public void setGroupList(List<Group>groups){
        this.groupList=groups;
        notifyDataSetChanged();
    }

    class GroupHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitel;

        public GroupHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitel=itemView.findViewById(R.id.groupText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onGroupListener != null && position != RecyclerView.NO_POSITION) {
                        onGroupListener.onGroupClick(groupList.get(position));
                    }
                }
            });

        }
    }
    public interface OnGroupListener {
        void onGroupClick(Group group);
        void onGroupEdit(Group group);

    }

    public void setOnGroupListener( OnGroupListener  onGroupListener ) {
        this.onGroupListener= onGroupListener;
    }


}
