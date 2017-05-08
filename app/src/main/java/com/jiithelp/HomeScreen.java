package com.jiithelp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<JIITListModel> jiitListModels;
    JIITListAdapter jiitListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        recyclerView = (RecyclerView)findViewById(R.id.jiit_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        fetchlist();

    }

    void fetchlist(){


        jiitListModels =new ArrayList<>();
        jiitListModels.add(new JIITListModel("Admission","admission")); //http://www.jiit.ac.in/admission-results
        jiitListModels.add(new JIITListModel("Announcements","announcement")); //http://www.jiit.ac.in/announcements
        jiitListModels.add(new JIITListModel("Upcoming Events","events")); //http://www.jiit.ac.in/guidelines-research
        jiitListModels.add(new JIITListModel("Notice Board","notice")); //http://www.jiit.ac.in/important-notices-parents-students-must-read
        jiitListModels.add(new JIITListModel("Academic Calendar","calendar")); //http://www.jiit.ac.in/academic-calendars-0
        jiitListModels.add(new JIITListModel("Important Links","links")); //http://www.jiit.ac.in/importantlinks

        jiitListAdapter = new JIITListAdapter(jiitListModels,this);
        recyclerView.setAdapter(jiitListAdapter);

        YoYo.with(Techniques.BounceInUp)
                .duration(3000)
                .repeat(1)
                .playOn(findViewById(R.id.jiit_list));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
