package com.idee.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesActivity extends AppCompatActivity implements AdapterClickListener {

    public static final String EXTRA_RECIPE_MODEL = "extra_recipe_model";
    private final String LOG_TAG = RecipesActivity.this.getClass().getSimpleName();
    RecyclerView mRecipes;
    ArrayList<RecipeModel> arrayList = new ArrayList<>();
    RecipesAdapter adapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecipes = (RecyclerView) findViewById(R.id.rv_recipes);
        progressBar = (ProgressBar) findViewById(R.id.pb_recipes_loading);
        progressBar.setVisibility(View.INVISIBLE);

        /*if (findViewById(R.id.tablet_view)!=null) {
            //tablet
            mRecipes.setLayoutManager(new GridLayoutManager(this,3));
        } else*/

        mRecipes.setLayoutManager(new GridLayoutManager(this, 1)/*new LinearLayoutManager(this)*/);

        adapter = new RecipesAdapter(this,this);
        mRecipes.setAdapter(adapter);

        fetchResponse();

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    private void fetchResponse() {

        if (NetworkUtils.isOnline(this)) {

            progressBar.setVisibility(View.VISIBLE);
            Call<String> call = NetworkUtils.recipieInterfaceClient().myRecipes();
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    //Log.d(LOG_TAG, String.valueOf(response.body()));
                    showResponse();
                    if (response.isSuccessful())
                        parseJson(response.body());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    showError();
                    Toast.makeText(RecipesActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                    Log.d(LOG_TAG, String.valueOf(t));

                }
            });

        } else {

            //progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(RecipesActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();

        }

    }

    private void showError() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void showResponse() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void parseJson(String response) {

        try {
            JSONArray array = new JSONArray(response);
            //Log.d(LOG_TAG,array.toString());

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Log.d(LOG_TAG, object.getString("name"));

                RecipeModel model = new RecipeModel();
                model.setName(object.getString("name"));

                ArrayList<IngredientModel> ingredientArrayList = new ArrayList<>();
                JSONArray array1 = new JSONArray(object.getString("ingredients"));
                //Log.d(LOG_TAG, array1.toString());
                for (int j = 0; j < array1.length(); j++) {
                    JSONObject object1 = array1.getJSONObject(j);

                    IngredientModel ingredientModel = new IngredientModel();
                    ingredientModel.setIngredient(object1.getString("ingredient"));
                    Log.d(LOG_TAG+String.valueOf(j),object1.getString("ingredient"));
                    ingredientModel.setMeasure(object1.getString("measure"));
                    ingredientModel.setQuantity(object1.getString("quantity"));

                    ingredientArrayList.add(ingredientModel);

                }

                model.setIngredientModelArrayList(ingredientArrayList);

                ArrayList<StepModel> stepArrayList = new ArrayList<>();
                JSONArray array2 = new JSONArray(object.getString("steps"));
                //Log.d(LOG_TAG, array1.toString());
                for (int k = 0; k < array2.length(); k++) {
                    JSONObject object1 = array2.getJSONObject(k);

                    StepModel stepModel = new StepModel();
                    stepModel.setDescription(object1.getString("description"));
                    stepModel.setShortDescription(object1.getString("shortDescription"));
                    Log.d(LOG_TAG+"2",object1.getString("shortDescription"));
                    stepModel.setVideoURL(object1.getString("videoURL"));
                    stepModel.setThumbnailURL(object1.getString("thumbnailURL"));

                    stepArrayList.add(stepModel);

                }

                model.setStepModelArrayList(stepArrayList);


                arrayList.add(model);

            } // end of  for-loop for populate the arrayList

            adapter.setArrayList(arrayList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //adapter click
    @Override
    public void recyclerOnClick(int position) {
        Intent intent = new Intent(RecipesActivity.this,RecipeDetailView.class);
        intent.putExtra(EXTRA_RECIPE_MODEL,arrayList.get(position));
        startActivity(intent);
    }
}