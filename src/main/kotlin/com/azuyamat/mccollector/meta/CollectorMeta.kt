package com.azuyamat.mccollector.meta

import com.azuyamat.mccollector.Restriction
import com.azuyamat.mccollector.collectors.Verifiable
import net.kyori.adventure.text.Component

/**
 * The meta class for a collector.
 *
 * @param T The type of the value to be collected.
 * @property prompt The action to be taken when the collector is prompted.
 * @property timeoutDuration The duration of the collector before it times out.
 * @property timeout The time at which the collector will time out. (You should not set this manually)
 * @property onCollect The action to be taken when the collector collects a value.
 * @property onCancel The action to be taken when the collector is cancelled.
 * @property onTimeout The action to be taken when the collector times out.
 * @property onInvalid The action to be taken when the collector collects an invalid value.
 * @property verifyValue The action to be taken to verify the value collected.
 * @property restrictions The restrictions to be applied to the collector.
 * @constructor Creates a new collector meta.
 * @since 1.0.0
 */
data class CollectorMeta<T> internal constructor(
    val prompt: () -> Unit,
    var timeoutDuration: Long = 30000, // 30 seconds (default)
    var timeout: Long = System.currentTimeMillis() + timeoutDuration,
    var onCollect: (T) -> Unit = {},
    var onCancel: () -> Unit = {},
    var onTimeout: () -> Unit = {},
    var onInvalid: (Verifiable.ValidationResult, T) -> Unit = { _, _ -> },
    var verifyValue: (T) -> Verifiable.ValidationResult = { Verifiable.ValidationResult.valid() },
    val restrictions: MutableSet<Restriction> = mutableSetOf(),
)