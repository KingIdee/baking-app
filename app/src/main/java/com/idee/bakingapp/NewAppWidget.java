package com.idee.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                String nameOfRecipe,
                                ArrayList<IngredientModel> list,
                                int appWidgetId) {


        // Construct the RemoteViews object

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);


        CharSequence ss=nameOfRecipe+"/n" ;
        for (int i = 0; i<list.size();i++)
             ss = ss + list.get(i).getIngredient()+", ";

        views.setTextViewText(R.id.appwidget_text, ss);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    static void customUpdate(Context context, AppWidgetManager appWidgetManager,String nameOfRecipe,
                             ArrayList<IngredientModel> list,int[] appWidgetIds){

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, nameOfRecipe, list, appWidgetId);
        }

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        /*for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }*/

        //TODO:  call RecipActivity
        new RecipesActivity().forWidget();
        //myCustomUpdate(context);

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

