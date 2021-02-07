package com.cxd.basicneedsapps
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {
    private lateinit var tempToBeTyped: String

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun initValidString() {
        // Specify a valid string.
        tempToBeTyped = "36.8"
    }

    @Test
    fun changeText_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.add_temperature_editText))
            .perform(typeText(tempToBeTyped))
        closeSoftKeyboard()
        onView(withId(R.id.flash_light_btn)).perform(click())
        onView(withId(R.id.qr_btn)).perform(click())


        onView(withId(R.id.quote_textView))
            .check(matches(withText("On")))
    }
}
