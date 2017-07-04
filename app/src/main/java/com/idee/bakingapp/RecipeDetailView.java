package com.idee.bakingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;

public class RecipeDetailView extends AppCompatActivity {

    public static boolean twoPane;

    @Nullable
    private MyIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public MyIdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new MyIdlingResource();
        }
        return mIdlingResource;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIdlingResource();
        setContentView(R.layout.activity_recipe_detail_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        twoPane = findViewById(R.id.fl_tablet_recipe_detail_view) != null;

    }

}
