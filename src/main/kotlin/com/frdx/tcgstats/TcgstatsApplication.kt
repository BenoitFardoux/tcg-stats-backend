package com.frdx.tcgstats

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TcgstatsApplication

fun main(args: Array<String>) {
	runApplication<TcgstatsApplication>(*args)
}
