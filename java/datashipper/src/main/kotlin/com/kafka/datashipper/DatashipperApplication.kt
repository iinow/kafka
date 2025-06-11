package com.kafka.datashipper

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DatashipperApplication

fun main(args: Array<String>) {
	runApplication<DatashipperApplication>(*args)
}
