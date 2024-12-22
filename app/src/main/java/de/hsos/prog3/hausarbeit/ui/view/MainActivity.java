package de.hsos.prog3.hausarbeit.ui.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import de.hsos.prog3.hausarbeit.R;
import de.hsos.prog3.hausarbeit.data.model.Group;
import de.hsos.prog3.hausarbeit.ui.adapter.GroupAdapter;
import de.hsos.prog3.hausarbeit.ui.viewModel.GroupViewModel;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_GROUP_REQUEST=1;
    private GroupViewModel groupViewModel;
    GroupAdapter adapter;
    /** Action Bar Variables start **/
    FloatingActionButton addGroup, edit;
    ExtendedFloatingActionButton addActionFab;
    TextView addGroupText, editText;

    Boolean isAllFabVisiable;
    /** Action Bar varidables end **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView =findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        setTitle("All Groups");

        adapter=new GroupAdapter();
        adapter.setOnGroupListener(new GroupAdapter.OnGroupListener() {
            @Override
            public void onGroupClick(Group group) {
                int groupId = group.getId();
                // Open GroupActivity and pass the group ID
                Intent intent=new Intent(MainActivity.this,GroupDetailsActivity.class);
                intent.putExtra("GROUP_ID", groupId);
                startActivity(intent);
            }

            @Override
            public void onGroupEdit(Group group) {

            }
        });
        recyclerView.setAdapter(adapter);


        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        groupViewModel.getAllGroups().observe(this, new Observer<List<Group>>() {
            @Override
            public void onChanged(@Nullable List<Group> groups) {
                adapter.setGroupList(groups);
            }
        });


        /** Action Bar **/
        addGroup = findViewById(R.id.addGroup);
        edit = findViewById(R.id.edit);
        addActionFab = findViewById(R.id.add_fab);

        addGroupText = findViewById(R.id.add_group);
        editText = findViewById(R.id.editting);

        addGroup.setVisibility(View.GONE);
        edit.setVisibility(View.GONE);
        addGroupText.setVisibility(View.GONE);
        editText.setVisibility(View.GONE);

        isAllFabVisiable = false;
        addActionFab.shrink();


        addActionFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isAllFabVisiable) {
                    expandFloatingActionBar();
                } else {
                    notExpandFloatingActionBar();
                }
            }
        });
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AddGroupActivity.class);
                startActivityForResult(intent,ADD_GROUP_REQUEST);
                notExpandFloatingActionBar();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,EditGroupActivity.class);
                startActivity(intent);
                notExpandFloatingActionBar();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_GROUP_REQUEST && resultCode == RESULT_OK) {
            assert data != null;
            String titel = data.getStringExtra(AddGroupActivity.EXTRA_TITLE);

            Group group = new Group(titel);
            groupViewModel.insert(group);
            Toast.makeText(this, "Group saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Group not saved", Toast.LENGTH_SHORT).show();
        }
    }





    /** Help methods expanded Floating  Action bar */

    private void expandFloatingActionBar(){
        addGroup.show();
        edit.show();
        addGroupText.setVisibility(View.VISIBLE);
        editText.setVisibility(View.VISIBLE);
        addActionFab.extend();
        isAllFabVisiable = true;
    }

    private void notExpandFloatingActionBar(){
        addGroup.hide();
        edit.hide();
        addGroupText.setVisibility(View.GONE);
        editText.setVisibility(View.GONE);
        addActionFab.shrink();
        isAllFabVisiable = false;
    }

}
