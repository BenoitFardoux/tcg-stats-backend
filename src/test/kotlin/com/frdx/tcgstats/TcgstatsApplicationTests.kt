package com.frdx.tcgstats

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.sql.Connection
import java.sql.DriverManager
import org.junit.jupiter.api.Assertions.assertNotNull

@SpringBootTest
class TcgstatsApplicationTests {

	@Test
	fun contextLoads() {
	}

	@Test
	fun `test database connection`() {
		val url = "jdbc:mariadb://mariadb:3306/statistiques_mtg_test"
		val user = "root"
		val password = "password"

		val connection: Connection? = DriverManager.getConnection(url, user, password)
		assertNotNull(connection, "La connexion à la base de données a échoué")
		connection?.close()
	}

}
