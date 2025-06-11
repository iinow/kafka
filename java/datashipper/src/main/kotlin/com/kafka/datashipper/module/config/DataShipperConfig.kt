package com.kafka.datashipper

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.kafka.core.KafkaTemplate
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.util.*
import java.io.File
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.collections.LinkedHashMap
import kotlin.collections.LinkedHashSet
import kotlin.collections.Map
import kotlin.collections.Set
import com.kafka.datashipper.module.config.ShipperProperties
import com.kafka.datashipper.module.config.ShipperTemplate
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory

@Component
@EnableConfigurationProperties(ShipperProperties::class)
class DataShipperConfig(
    private val shipperProperties: ShipperProperties,
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val kafkaConsumer: KafkaConsumer<String, String>
) {

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper(YAMLFactory()).findAndRegisterModules()
    }

    @Bean
    fun shipperTemplates(objectMapper: ObjectMapper): List<ShipperTemplate> {
        return shipperProperties.import.map { importPath ->
        val yamlContent = if (importPath.startsWith("classpath:/")) {
            val resourcePath = importPath.removePrefix("classpath:/")
            val resource = ClassPathResource(resourcePath)
            resource.inputStream.bufferedReader().use { it.readText() }
        } else {
            File(importPath).readText()
        }
        objectMapper.readValue(yamlContent, ShipperTemplate::class.java)
    }

    @Bean
    fun kafkaConsumer(): KafkaConsumer<String, String> {
        val props = Properties()
        props["bootstrap.servers"] = shipperProperties.kafka.bootstrapServers
        props["key.deserializer"] = "org.apache.kafka.common.serialization.StringDeserializer"
        props["value.deserializer"] = "org.apache.kafka.common.serialization.StringDeserializer"
        props["group.id"] = shipperProperties.kafka.groupId
        return KafkaConsumer(props)
    }
}
