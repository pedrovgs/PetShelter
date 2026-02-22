package com.petshelter.feature.animals

import com.petshelter.core.model.AnimalSex
import kotlin.test.Test
import kotlin.test.assertEquals

class FormatAgeTest {
    @Test
    fun zeroMonths_returnsZeroM() {
        assertEquals("0m", formatAge(0))
    }

    @Test
    fun monthsOnly_returnsMonthsWithSuffix() {
        assertEquals("3m", formatAge(3))
    }

    @Test
    fun elevenMonths_returnsMonthsWithSuffix() {
        assertEquals("11m", formatAge(11))
    }

    @Test
    fun exactlyOneYear_returnsOneY() {
        assertEquals("1y", formatAge(12))
    }

    @Test
    fun exactlyTwoYears_returnsTwoY() {
        assertEquals("2y", formatAge(24))
    }

    @Test
    fun yearsAndMonths_returnsCombined() {
        assertEquals("2y 6m", formatAge(30))
    }

    @Test
    fun oneYearAndOneMonth_returnsCombined() {
        assertEquals("1y 1m", formatAge(13))
    }
}

class SexSymbolTest {
    @Test
    fun female_returnsFemalSymbol() {
        assertEquals("\u2640\uFE0F", sexSymbol(AnimalSex.FEMALE))
    }

    @Test
    fun male_returnsMaleSymbol() {
        assertEquals("\u2642\uFE0F", sexSymbol(AnimalSex.MALE))
    }
}
