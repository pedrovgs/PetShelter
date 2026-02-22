package com.petshelter.core.service

interface MetricReporter {
    fun event(
        name: String,
        properties: Map<String, String> = emptyMap(),
    )

    fun report(event: MetricEvent) = event(event.name, event.properties)

    fun startTimer(name: String)

    fun stopTimer(name: String)

    fun increment(
        name: String,
        amount: Long = 1,
    )
}
