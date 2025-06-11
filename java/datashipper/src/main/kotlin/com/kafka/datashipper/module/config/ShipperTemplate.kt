package com.kafka.datashipper.module.config

import org.springframework.stereotype.Component

data class ShipperTemplate(
    val name: String,
    val description: String,
    val source: String,
    val sink: String,
) {
}
