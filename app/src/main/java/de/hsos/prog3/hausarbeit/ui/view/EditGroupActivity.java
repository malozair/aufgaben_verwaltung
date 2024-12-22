package de.hsos.prog3.hausarbeit.ui.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import de.hsos.prog3.hausarbeit.R;
import de.hsos.prog3.hausarbeit.data.model.Group;
import de.hsos.prog3.hausarbeit.ui.adapter.GroupAdapter;
import de.hsos.prog3.hausarbeit.ui.viewModel.GroupViewModel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import java.util.List;


public class EditGroupActivity extends AppCompatActivity implements GroupAdapter.OnGroupListener {
    private GroupViewModel groupViewModel;
    public static final int EDIT_GROUP_REQUEST = 2;
    GroupAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);
        setTitle("Edit or Delete Group");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GroupAdapter();
        adapter.setOnGroupListener(this);
        recyclerView.setAdapter(adapter);
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        groupViewModel.getAllGroups().observe(this, new Observer<List<Group>>() {
            @Override
            public void onChanged(List<Group> groups) {
                adapter.setGroupList(groups);
            }
        });
    }

    @Override
    public void onGroupClick(Group group) {
        showEditDeleteDialog(group);
    }

    @Override
    public void onGroupEdit(Group group) {
        openAddGroupActivityForEdit(group);
    }

    private void showEditDeleteDialog(final Group group) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to edit or delete this group?");
        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onGroupEdit(group);
            }
        });
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                groupViewModel.delete(group);
            }
        });
        builder.create().show();
    }

    private void openAddGroupActivityForEdit(Group group) {
        Intent intent = new Intent(EditGroupActivity.this, AddGroupActivity.class);
        intent.putExtra(AddGroupActivity.EXTRA_ID, group.getId());
        intent.putExtra(AddGroupActivity.EXTRA_TITLE, group.getName());
        startActivityForResult(intent, EDIT_GROUP_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_GROUP_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddGroupActivity.EXTRA_ID,-1);
            if (id == -1) {
                Toast.makeText(this, "Group can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddGroupActivity.EXTRA_TITLE);

            Group group = new Group(title);
            group.setId(id);
            groupViewModel.update(group);

            Toast.makeText(this, "Group updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Group not updated", Toast.LENGTH_SHORT).show();
        }
    }

}
