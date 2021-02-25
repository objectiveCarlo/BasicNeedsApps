package com.cxd.basicneedsapps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)
    @Before
    fun before() {
        Intents.init()
    }

    @After
    fun after() {
        Intents.release()
    }
    @Test
    fun turnOnTorch() {
        onView(withId(R.id.flash_light_btn)).perform(click())
        onView(withId(R.id.flash_light_btn))
            .check(matches(withText("TORCH is ON")))
    }

    @Test
    fun turnOnQR() {
        onView(withId(R.id.qr_btn)).perform(click())
        intended(hasComponent(QRScannerActivity::class.java.name))
    }
}
