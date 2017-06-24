package com.idee.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by idee on 6/16/17.
 */

public class RecipeDetailViewAdapter extends RecyclerView.Adapter<RecipeDetailViewAdapter.ViewHolder> {

    private Context context;
    public AdapterClickListener adapterClickListener;
    ArrayList<StepModel> stepModelArrayList = new ArrayList<>();

    RecipeDetailViewAdapter(Context context, AdapterClickListener adapterClickListener){
        this.context = context;
        this.adapterClickListener=adapterClickListener;
    }

    void setStepModel(ArrayList<StepModel> stepModelArrayList){
        this.stepModelArrayList = stepModelArrayList;
        notifyDataSetChanged();

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_recipe_step_card,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.recipeStep.setText(stepModelArrayList.get(position).getShortDescription());

    }

    @Override
    public int getItemCount() {
        return stepModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView recipeStep;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            recipeStep = (TextView) itemView.findViewById(R.id.tv_recipe_step);
        }

        @Override
        public void onClick(View v) {
            adapterClickListener.recyclerOnClick(getAdapterPosition());
        }
    }
}
