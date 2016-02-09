package com.gagnon.mario.mr.incexp.app.contributor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gagnon.mario.mr.incexp.app.R;

/**
 * Created by mario on 2/8/2016.
 */
public class ContributorActivityEditor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributor_editor);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            ContributorFragmentEditor fragment = new ContributorFragmentEditor();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contributor_editor_container, fragment)
                    .commit();
        }
    }
}
