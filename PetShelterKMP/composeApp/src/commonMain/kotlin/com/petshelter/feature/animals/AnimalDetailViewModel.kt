package com.petshelter.feature.animals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petshelter.core.data.AnimalRepository
import com.petshelter.core.model.Animal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AnimalDetailUiState(
    val isLoading: Boolean = true,
    val animal: Animal? = null,
    val cleanDescription: String = "",
    val videoLinks: List<String> = emptyList(),
    val errorMessage: String? = null,
)

class AnimalDetailViewModel(
    private val animalId: String,
    private val repository: AnimalRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AnimalDetailUiState())
    val uiState: StateFlow<AnimalDetailUiState> = _uiState.asStateFlow()

    init {
        loadAnimal()
    }

    private fun loadAnimal() {
        viewModelScope.launch {
            try {
                val animal = repository.getAnimalById(animalId)
                if (animal != null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            animal = animal,
                            cleanDescription = cleanDescription(animal.description),
                            videoLinks = extractVideoLinks(animal),
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = "Animal not found")
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = e.message)
                }
            }
        }
    }
}

private val YOUTUBE_URL_REGEX = Regex("https?://(?:www\\.)?(?:youtube\\.com|youtu\\.be)/\\S+")

internal fun cleanDescription(raw: String): String {
    val withoutUrls = YOUTUBE_URL_REGEX.replace(raw, "").trim()
    val lines = withoutUrls.lines().map { it.trim() }.filter { it.isNotEmpty() }
    return deduplicateDescription(lines)
}

private fun deduplicateDescription(lines: List<String>): String {
    val joined = lines.joinToString("\n")
    val half = joined.length / 2
    if (half < 20) return joined

    val firstHalf = joined.substring(0, half).trim()
    val secondHalf = joined.substring(half).trim()

    return if (firstHalf.endsWith(secondHalf) || secondHalf.startsWith(firstHalf) || levenshteinSimilarity(firstHalf, secondHalf) > 0.8) {
        val longer = if (firstHalf.length >= secondHalf.length) firstHalf else secondHalf
        longer
    } else {
        joined
    }
}

private fun levenshteinSimilarity(
    a: String,
    b: String,
): Double {
    if (a.isEmpty() && b.isEmpty()) return 1.0
    val maxLen = maxOf(a.length, b.length)
    if (maxLen == 0) return 1.0

    val dp = Array(a.length + 1) { IntArray(b.length + 1) }
    for (i in 0..a.length) dp[i][0] = i
    for (j in 0..b.length) dp[0][j] = j
    for (i in 1..a.length) {
        for (j in 1..b.length) {
            val cost = if (a[i - 1] == b[j - 1]) 0 else 1
            dp[i][j] = minOf(dp[i - 1][j] + 1, dp[i][j - 1] + 1, dp[i - 1][j - 1] + cost)
        }
    }
    return 1.0 - dp[a.length][b.length].toDouble() / maxLen
}

private fun extractVideoLinks(animal: Animal): List<String> {
    val fromVideos = animal.videos.filter { it.isNotBlank() }
    val fromDescription = YOUTUBE_URL_REGEX.findAll(animal.description).map { it.value }.toList()
    return (fromVideos + fromDescription).distinct()
}
