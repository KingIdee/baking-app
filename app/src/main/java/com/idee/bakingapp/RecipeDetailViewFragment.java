package com.idee.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import static com.idee.bakingapp.RecipeDetailView.twoPane;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeDetailViewFragment extends Fragment implements AdapterClickListener {

    public static final String EXTRA_STEP_MODEL = "extra_step_model";
    public static final String POSITION = "position";

    public RecipeDetailViewFragment() {}

    private RecipeDetailViewAdapter adapter;
    RecyclerView recyclerView;
    RecipeModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_recipe_detail_view, container, false);

        model = getActivity().getIntent().getParcelableExtra(RecipesActivity.EXTRA_RECIPE_MODEL);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_steps);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecipeDetailViewAdapter(getActivity(),this);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);

        ArrayList<IngredientModel> ingredientModel = model.getIngredientModelArrayList();
        //TextView ingredients = (TextView) view.findViewById(R.id.tv_ingredients);
        String ing="";

        for (int i = 0; i<ingredientModel.size();i++){
            //String ingredient, quantity, measure;
            ing = ing+"\n"+ingredientModel.get(i).getIngredient()+", "+ingredientModel.get(i).getQuantity()+", "+ingredientModel.get(i).getMeasure();

        }

        //ingredients.setText(ing);

        adapter.setStepModel(model.getStepModelArrayList());
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void recyclerOnClick(int position) {
        //TODO: start StepDetailView Activity

        if (twoPane){

            StepDetailViewFragment fragment = new StepDetailViewFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(EXTRA_STEP_MODEL,model.getStepModelArrayList());
            bundle.putInt(POSITION,position);
            fragment.setArguments(bundle);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_tablet_recipe_detail_view,fragment)
                    .commit();


        } else {
            Intent intent = new Intent(getActivity(),StepDetailView.class);
            //intent.putExtra(EXTRA_STEP_MODEL,model.getStepModelArrayList().get(position));
            intent.putExtra(EXTRA_STEP_MODEL,model.getStepModelArrayList());
            intent.putExtra(POSITION,position);
            startActivity(new Intent(intent));
        }



    }

}
