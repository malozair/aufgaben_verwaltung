package de.hsos.prog3.hausarbeit.ui.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import de.hsos.prog3.hausarbeit.R;

public class GroupDetailsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int groupId;


    // Action Bar Variables start
    FloatingActionButton addPerson, newOutput;
    ExtendedFloatingActionButton addActionFab;
    TextView addPersonText, newOutputText;
    Boolean isAllFabVisible;
    // Action Bar variables end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);

        groupId = getIntent().getIntExtra("GROUP_ID", -1);
        setTitle("Group Details");
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Overview"));
        tabLayout.addTab(tabLayout.newTab().setText("Output"));

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new OverviewFragment(groupId);
                    case 1:
//                        OutputFragment outputFragment = new OutputFragment();
//                        Bundle bundle = new Bundle();
//                        bundle.putInt("group_id", groupId);
//                        outputFragment.setArguments(bundle);
                        return new OutputFragment(groupId);
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return tabLayout.getTabCount();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        addPerson = findViewById(R.id.addPerson);
        newOutput = findViewById(R.id.new_output);
        addActionFab = findViewById(R.id.add_fab2);

        addPersonText = findViewById(R.id.add_person);
        newOutputText = findViewById(R.id.new_output2);

        newOutput.setVisibility(View.GONE);
        addPerson.setVisibility(View.GONE);
        addPersonText.setVisibility(View.GONE);
        newOutputText.setVisibility(View.GONE);

        isAllFabVisible = false;
        addActionFab.shrink();

        addActionFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isAllFabVisible) {
                    addPerson.show();
                    newOutput.show();
                    addPersonText.setVisibility(View.VISIBLE);
                    newOutputText.setVisibility(View.VISIBLE);
                    addActionFab.extend();
                    isAllFabVisible = true;
                } else {
                    addPerson.hide();
                    newOutput.hide();
                    addPersonText.setVisibility(View.GONE);
                    newOutputText.setVisibility(View.GONE);
                    addActionFab.shrink();
                    isAllFabVisible = false;
                }
            }
        });

        addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupDetailsActivity.this, AddPersonActivity.class);
                intent.putExtra("GROUP_ID", groupId);
                startActivity(intent);
            }
        });

        newOutput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupDetailsActivity.this, AddOutputActivity.class);
                intent.putExtra("GROUP_ID", groupId);
                startActivity(intent);
            }
        });

    }
}