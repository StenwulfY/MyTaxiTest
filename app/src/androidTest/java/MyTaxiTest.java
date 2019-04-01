import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.mytaxi.android_demo.R;
import com.mytaxi.android_demo.activities.MainActivity;
import com.mytaxi.android_demo.utils.storage.SharedPrefStorage;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MyTaxiTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    private MainActivity mMainActivity = null;
    private IdlingResource mIdlingResource;

    @Before
    public void setActivity() throws NoMatchingViewException {
        mMainActivity = mActivityRule.getActivity();
        mIdlingResource = mMainActivity.getIdlingResource();
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().register(mIdlingResource);
        }
    }

    @Test
    public void searchDriver() throws InterruptedException {
        Log.d("@EspressoTest", "Start Test");
        //Key-in the username
        onView(withId(R.id.edt_username)).perform(typeText("crazydog335"), closeSoftKeyboard());
        //Key-in the password
        onView(withId(R.id.edt_password)).perform(typeText("venture"), closeSoftKeyboard());
        // click on login
        onView(withId(R.id.btn_login)).perform(click());
        //Check that the search field is displayed
        onView(withId(R.id.textSearch)).check(matches(isDisplayed()));
        //Key-in the start of the driver name
        onView(withId(R.id.textSearch)).perform(typeText("sa"), closeSoftKeyboard());
        // Check that the name of the second driver is present
        onView(withText("Sarah Scott"))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        //Tap on the Driver's name
        onView(withText("Sarah Scott"))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).perform(click());
        //Check that the call button is displayed
        onView((withId(R.id.fab))).check(matches(isDisplayed()));
        //Click on the call button
        onView((withId(R.id.fab))).perform(click());

    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

}