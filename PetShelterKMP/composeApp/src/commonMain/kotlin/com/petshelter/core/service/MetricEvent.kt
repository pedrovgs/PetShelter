package com.petshelter.core.service

sealed interface MetricEvent {
    val name: String
    val properties: Map<String, String>
}
