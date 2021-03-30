package com.team13.trojancheckin_out.Layouts;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.team13.trojancheckin_out.Accounts.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SearchStudentsFunctionalityTest {

    @Rule
    public ActivityTestRule<Startup> mActivityTestRule = new ActivityTestRule<>(Startup.class);

    @Test
    public void searchStudentsFunctionalityTest() {
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
        appCompatEditText.perform(replaceText("aoeth@usc.edu"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editTextTextPassword3),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("a"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.editTextTextPassword3), withText("a"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText3.perform(pressImeActionButton());


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
                allOf(withId(R.id.button5), withText("SEARCH STUDENTS"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        7),
                                3),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.spinner),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                10),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(45);
        appCompatCheckedTextView.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.button7), withText("search    "),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                14),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.button7), withText("search    "),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                14),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.fullName), withText("annika oeth"),
                        hasSibling(allOf(withId(R.id.profileButton), withText("PROFILE"),
                                childAtPosition(
                                        childAtPosition(
                                                withClassName(is("androidx.cardview.widget.CardView")),
                                                0),
                                        2)) ),
                        isDisplayed()));
        textView.check(matches(withText("annika oeth")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.studentID), withText("2794040392"),
                        hasSibling(allOf(withId(R.id.profileButton), withText("PROFILE"),
                                childAtPosition(
                                        childAtPosition(
                                                withClassName(is("androidx.cardview.widget.CardView")),
                                                0),
                                        2)) ),
                        isDisplayed()));
        textView2.check(matches(withText("2794040392")));



        ViewInteraction appCompatSpinner8 = onView(
                allOf(withId(R.id.spinner2),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                11),
                        isDisplayed()));
        appCompatSpinner8.perform(click());

        DataInteraction appCompatCheckedTextView6 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(12);
        appCompatCheckedTextView6.perform(click());

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.button7), withText("search    "),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                14),
                        isDisplayed()));
        materialButton6.perform(click());

        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.button7), withText("search    "),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                14),
                        isDisplayed()));
        materialButton7.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.fullName), withText("annika oeth"),
                        hasSibling(allOf(withId(R.id.profileButton), withText("PROFILE"),
                                childAtPosition(
                                        childAtPosition(
                                                withClassName(is("androidx.cardview.widget.CardView")),
                                                0),
                                        2)) ),
                        isDisplayed()));
        textView3.check(matches(withText("annika oeth")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.studentID), withText("2794040392"),
                        hasSibling(allOf(withId(R.id.profileButton), withText("PROFILE"),
                                childAtPosition(
                                        childAtPosition(
                                                withClassName(is("androidx.cardview.widget.CardView")),
                                                0),
                                        2)) ),
                        isDisplayed()));
        textView4.check(matches(withText("2794040392")));

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
