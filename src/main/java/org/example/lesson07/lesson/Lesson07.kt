package org.example.lesson07.lesson

import org.hamcrest.Description
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeDiagnosingMatcher

class BrandMatcher(
    private val expectedBrand: String
) : TypeSafeDiagnosingMatcher<Car>() {

    override fun describeTo(description: Description) {
        description.appendText("car with brand ")
            .appendValue(expectedBrand)
    }

    override fun matchesSafely(item: Car, mismatchDescription: Description): Boolean {
        if (item.brand != expectedBrand) {
            mismatchDescription
                .appendText("brand was ")
                .appendValue(item.brand)
            return false
        }
        return true
    }
}

class ColorMatcher(private val expectedColor: Color) : TypeSafeDiagnosingMatcher<Car>() {

    override fun describeTo(description: Description) {
        description.appendText("car with color ")
            .appendValue(expectedColor.name)
    }

    override fun matchesSafely(
        item: Car,
        mismatchDescription: Description
    ): Boolean {
        if (item.color != expectedColor) {
            mismatchDescription.appendText("color was ")
                .appendValue(item.color)
            return false
        }
        return true
    }
}

class FromAgeMatcher(private val expectedFromAge: Int) : TypeSafeDiagnosingMatcher<Car>() {
    override fun matchesSafely(
        item: Car,
        mismatchDescription: Description
    ): Boolean {
        if (item.age < expectedFromAge) {
            mismatchDescription.appendText("age was ")
                .appendValue(item.age)
            return false
        }
        return true
    }

    override fun describeTo(description: Description) {
        description.appendText("car age from ")
            .appendValue(expectedFromAge)
    }
}

class ToAgeMatcher(private val expectedToAge: Int) : TypeSafeDiagnosingMatcher<Car>() {
    override fun matchesSafely(
        item: Car,
        mismatchDescription: Description
    ): Boolean {
        if (item.age > expectedToAge) {
            mismatchDescription.appendText("age was ")
                .appendValue(item.age)
            return false
        }
        return true
    }

    override fun describeTo(description: Description) {
        description.appendText("car age to ")
            .appendValue(expectedToAge)
    }
}

fun main() {
    val car1 = Car("Toyota", Color.BLUE, 10)
    val car2 = Car("Audi", Color.GREEN, 10)
    val car3 = Car("Subaru", Color.BLUE, 8)
    val car4 = Car("Subaru", Color.BLUE, 10)

    val isSubaru = BrandMatcher("Subaru")
    val isBlue = ColorMatcher(Color.BLUE)
    val from4Age = FromAgeMatcher(4)
    val to8age = ToAgeMatcher(8)
    val allMatchers = allOf(isSubaru, isBlue, from4Age, to8age)

    val cars = listOf(car1, car2, car3, car4)

    val filtered = cars.filter { allMatchers.matches(it) }
    println(filtered)
    val matcherComposition = allOf(
        anyOf(isSubaru, BrandMatcher("Toyota")),
        isBlue
    )
    val filtered2 = cars.filter { matcherComposition.matches(it) }
    println(filtered2)

    assertThat(car1, allMatchers)
}