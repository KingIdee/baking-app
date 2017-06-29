package com.idee.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class RecipeDetailView extends AppCompatActivity {

    public static boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail_view);

        twoPane = findViewById(R.id.fl_tablet_recipe_detail_view) != null;

    }

}
