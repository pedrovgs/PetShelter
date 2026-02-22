package com.petshelter.core.service

class WasmJsMetricReporter : MetricReporter {
    override fun event(
        name: String,
        properties: Map<String, String>,
    ) {
        // No-op stub
    }

    override fun startTimer(name: String) {
        // No-op stub
    }

    override fun stopTimer(name: String) {
        // No-op stub
    }

    override fun increment(
        name: String,
        amount: Long,
    ) {
        // No-op stub
    }
}
