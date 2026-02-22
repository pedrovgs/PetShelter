package com.petshelter.feature.animals

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CleanDescriptionTest {
    @Test
    fun normalDescription_remainsUnchanged() {
        val description = "Luna is a beautiful and friendly dog who loves walks in the park."

        val result = cleanDescription(description)

        assertEquals(description, result)
    }

    @Test
    fun descriptionWithYouTubeUrls_removesUrls() {
        val description =
            "Luna is a great dog. Watch her video: https://www.youtube.com/watch?v=abc123 She loves to play."

        val result = cleanDescription(description)

        assertFalse(result.contains("youtube.com"))
        assertTrue(result.contains("Luna is a great dog."))
        assertTrue(result.contains("She loves to play."))
    }

    @Test
    fun descriptionWithShortYoutubeUrl_removesUrl() {
        val description = "Check out Luna: https://youtu.be/abc123 She is friendly."

        val result = cleanDescription(description)

        assertFalse(result.contains("youtu.be"))
        assertTrue(result.contains("Check out Luna:"))
        assertTrue(result.contains("She is friendly."))
    }

    @Test
    fun duplicatedDescription_deduplicatesContent() {
        val singlePart = "Luna is a beautiful dog who loves long walks and playing fetch in the park."
        val duplicated = "$singlePart\n$singlePart"

        val result = cleanDescription(duplicated)

        assertEquals(singlePart, result)
    }

    @Test
    fun shortDescription_noDeduplicationAttempted() {
        val shortText = "Short desc."

        val result = cleanDescription(shortText)

        assertEquals(shortText, result)
    }

    @Test
    fun descriptionWithMultipleYouTubeUrls_removesAll() {
        val description =
            "See https://www.youtube.com/watch?v=abc and also https://youtu.be/def for more info."

        val result = cleanDescription(description)

        assertFalse(result.contains("youtube.com"))
        assertFalse(result.contains("youtu.be"))
        assertTrue(result.contains("See"))
        assertTrue(result.contains("for more info."))
    }

    @Test
    fun emptyDescription_returnsEmpty() {
        val result = cleanDescription("")

        assertEquals("", result)
    }
}
