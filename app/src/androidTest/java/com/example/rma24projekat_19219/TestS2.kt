import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
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
        // Testiranje validacije ako nisu odabrane medicinske koristi
        // Očekujemo da će se prikazati poruka o grešci
        // Ova provjera treba biti izvršena nakon klikanja na dugme "Dodaj biljku"

        // Simulacija klika na dugme "Dodaj biljku"
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())

        // Provjera da li se prikazala poruka o grešci za odabir medicinskih koristi
        onView(withId(R.id.medicinskaKoristLV)).check(matches(hasErrorText("Odaberite barem jednu medicinsku korist")))
    }

    @Test
    fun testPrikazSlikeBiljke() {
        // Testiranje prikaza slike biljke nakon klika na dugme "Uslikaj biljku"
        // Očekujemo da će se slika biljke prikazati u ImageView elementu

        // Simulacija klika na dugme "Uslikaj biljku"
        onView(withId(R.id.uslikajBiljkuBtn)).perform(click())

        // Provjera da li se slika biljke prikazala u ImageView elementu
        onView(withId(R.id.slikaIV)).check(matches(isDisplayed()))
    }
}
