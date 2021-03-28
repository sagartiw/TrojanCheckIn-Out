package com.team13.trojancheckin_out.Layouts;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.team13.trojancheckin_out.Accounts.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SignOutStudentTest {

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

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.signOut), withText("SIGN OUT"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.textView20), withText("Sign Out"),
                        withParent(withParent(withId(R.id.cardView))),
                        isDisplayed()));
        textView.check(matches(withText("Sign Out")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textView26), withText("If you are checked in at a building, you will be checked out when you sign out."),
                        withParent(withParent(withId(R.id.cardView))),
                        isDisplayed()));
        textView2.check(matches(withText("If you are checked in at a building, you will be checked out when you sign out.")));

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.button10), withText("SUBMIT"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.cardView),
                                        0),
                                0),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.textView), withText("trojan"),
                        withParent(allOf(withId(R.id.ConstraintLayout),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        textView4.check(matches(withText("trojan")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.textView2), withText("check in/out"),
                        withParent(allOf(withId(R.id.ConstraintLayout),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        textView5.check(matches(withText("check in/out")));


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
