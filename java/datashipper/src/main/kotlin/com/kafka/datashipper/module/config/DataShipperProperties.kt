package com.kafka.datashipper.module.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.collections.LinkedHashMap
import kotlin.collections.LinkedHashSet
import kotlin.collections.Map
import kotlin.collections.Set

@Component
@ConfigurationProperties(prefix = "data-shipper")
data class ShipperProperties(
    var import: List<String> = emptyList()
) {
    data class ShipperDataLocation(
        var name: String = "",
        var url: String = "",
        var active: Boolean = false,
        var topics: Set<String> = LinkedHashSet(),
        var properties: Map<String, String> = LinkedHashMap()
    )
}