package com.example.thebakery;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class TheBakeryActivityGridTest {
    @Rule
    public ActivityTestRule<TheBakeryActivity> mActivityTestRule = new ActivityTestRule<>(TheBakeryActivity.class);
    @Test
    public void scrollGridViewItem() {
        onView(withId(R.id.bakerygridRecyclerView)).perform(RecyclerViewActions.scrollToPosition(3));
    }
}
