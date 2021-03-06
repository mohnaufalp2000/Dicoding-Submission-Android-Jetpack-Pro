package com.naufal.moviepedia.activity

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.naufal.moviepedia.R
import com.naufal.moviepedia.util.DataDummy
import com.naufal.moviepedia.util.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityTest{

    private val dummyMovie = DataDummy.getDataMovies()
    private val dummyTV = DataDummy.getDataTV()

    @Before
    fun setUp(){
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadMovies(){
        onView(withText("MOVIES")).perform(click())
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovie.size))
        onView(withId(R.id.rv_movies)).perform(swipeUp())
    }

    @Test
    fun loadDetailMovies(){
    onView(withText("MOVIES")).perform(click())
    onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
    onView(withId(R.id.detail_activity)).perform(swipeUp())
    onView(withId(R.id.txt_title)).check(matches(isDisplayed()))
    onView(withId(R.id.txt_released)).check(matches(isDisplayed()))
    onView(withId(R.id.txt_genre)).check(matches(isDisplayed()))
    onView(withId(R.id.txt_runtime)).check(matches(isDisplayed()))
    onView(withId(R.id.txt_language)).check(matches(isDisplayed()))
    onView(withId(R.id.txt_rating)).check(matches(isDisplayed()))
    onView(withId(R.id.txt_overview)).check(matches(isDisplayed()))
    onView(withId(R.id.img_poster)).check(matches(isDisplayed()))
    }

    @Test
    fun loadTV(){
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_shows)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTV.size))
        onView(withId(R.id.rv_tv_shows)).perform(swipeUp())
    }

    @Test
    fun loadDetailTV(){
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rv_tv_shows)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.detail_activity)).perform(swipeUp())
        onView(withId(R.id.txt_title)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_released)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_runtime)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_language)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.img_poster)).check(matches(isDisplayed()))
    }

}