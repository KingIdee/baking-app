package com.idee.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by idee on 7/1/17.
 */

@RunWith(AndroidJUnit4.class)

public class RecipeDetailActivityTest {

    IdlingResource idlingResource;

    @Before
    public void registerIdlingResource(){
        idlingResource = activityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }

    //@Rule public ActivityTestRule<RecipesActivity> activityTestRule = new ActivityTestRule<>(RecipesActivity.class);
    @Rule
    public IntentsTestRule<RecipeDetailView> activityTestRule = new IntentsTestRule<>(RecipeDetailView.class);

    // i am yet to run this test
    @Test
    public void openStepDetailView(){
        //onView(withId(R.id.rv_recipes)).perform(click());

        //perform click on the recycler view
        onView(withId(R.id.rv_steps))
                .perform(actionOnItemAtPosition(0, click()));

        // check if the intent is starts the class StepDetailView
        intended(hasComponent(StepDetailView.class.getName()));

    }

    @Test
    public void recyclerviewNotEmpty(){
        //assertThat();

    }

    @After
    public void unregisterIdlingResource(){

        if (idlingResource!=null)
            Espresso.unregisterIdlingResources(idlingResource);

    }

}