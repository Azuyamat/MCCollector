package com.azuyamat.mccollector.meta

import net.kyori.adventure.text.Component

data class CollectorMeta<T> internal constructor(
    val prompt: Component,
    var timeoutDuration: Long = 30000, // 30 seconds (default)
    var timeout: Long = System.currentTimeMillis() + timeoutDuration,
    var onCollect: (T) -> Unit = {},
    var onCancel: () -> Unit = {},
    var onTimeout: () -> Unit = {},
    var verifyValue: (T) -> Boolean = { true },
)