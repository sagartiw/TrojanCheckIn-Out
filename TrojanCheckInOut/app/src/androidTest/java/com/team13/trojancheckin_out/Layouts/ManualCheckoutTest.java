package com.team13.trojancheckin_out.Layouts;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;
import com.team13.trojancheckin_out.Database.AccountManipulator;
import com.team13.trojancheckin_out.Database.MyUserCallback;
import com.team13.trojancheckin_out.UPC.Building;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.team13.trojancheckin_out.Layouts.Startup.buildingManipulator;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ManualCheckoutTest {
    AccountManipulator accountManipulator = new AccountManipulator();
    User student = new User();

    @Rule
    public ActivityTestRule<Startup> mActivityTestRule = new ActivityTestRule<>(Startup.class);

    @Test
    public void signOutStudentTest() {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.login), withText("LOGIN"),
                        childAtPosition(
                                allOf(withId(R.id.ConstraintLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editTextTextEmailAddress2),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("patricia@usc.edu"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editTextTextPassword3),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("p"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.login), withText("LOGIN"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialButton2.perform(click());

        // manually add student to a building
        accountManipulator.getAllAccounts(new MyUserCallback() {
            @Override
            public void onCallback(Map<String, User> map) {
                System.out.println("HELLO SHOW ALL!");
                for (Map.Entry<String, User> e : map.entrySet()) {
                    User u = e.getValue();
                    String name = u.getName();
                    String email = u.getEmail();

                    if (email.equalsIgnoreCase("patricia@usc.edu")) {
                        String pass = u.getPassword();
                        String photo = u.getPhoto();
                        String id = u.getId();
                        boolean inBuilding = u.isInBuilding();
                        Building currentBuilding = u.getCurrentBuilding();
                        Map<String, String> history = u.getHistory();
                        String major = u.getMajor();
                        String isManager = u.isManager();

                        student = new User(name, email, pass, photo, id, inBuilding,
                                currentBuilding, history, major, isManager);
                        System.out.println("HERE USER: " + name);

                        Building birnkrant = buildingManipulator.getBuilding("BSR");

                        birnkrant.addStudent(student);
                    }

                }
            }
        });

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.checkOut),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                10),
                        isDisplayed()));
        materialButton3.perform(click());


    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
