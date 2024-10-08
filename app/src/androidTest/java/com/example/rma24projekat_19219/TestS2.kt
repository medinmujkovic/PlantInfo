import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.rma24projekat_19219.view.activities.NovaBiljkaActivity
import com.example.rma24projekat_19219.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class TestS2{

    @get:Rule
    val activityRule = ActivityTestRule(NovaBiljkaActivity::class.java)

    @Test
    fun testValidacijaUnosa() {
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.medicinskaKoristLV)).check(matches(hasErrorText("Odaberite barem jednu medicinsku korist")))
    }

    @Test
    fun testPrikazSlikeBiljke() {
        onView(withId(R.id.uslikajBiljkuBtn)).perform(click())
        onView(withId(R.id.slikaIV)).check(matches(isDisplayed()))
    }
}
