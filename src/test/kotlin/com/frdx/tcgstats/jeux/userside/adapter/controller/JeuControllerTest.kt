package com.frdx.tcgstats.jeux.userside.adapter.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.frdx.tcgstats.jeux.serverside.adapter.mysql.repository.MariaDbJeuxRepository
import com.frdx.tcgstats.jeux.serverside.mapper.JeuxMapper.toJeuxDocument
import com.frdx.tcgstats.jeux.userside.mapper.JeuMapper.toJeu
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension::class)
@TestPropertySource(locations = ["classpath:application-test.properties"])

class JeuControllerTest {
    @Autowired
    private lateinit var mariaDbJeuxRepository: MariaDbJeuxRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        mariaDbJeuxRepository.deleteAll()
    }

    @Test
    fun `Lorsque j'essaye de créer un jeu qui n'existe pas il n'y a aucun conflit`() {
        // GIVEN
        val jeuACreer = JeuFixture.creerJeuDto
        // WHEN
        mockMvc.post("/tcgstat/jeux") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(jeuACreer)
        }
            // THEN
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }

    }

    @Test
    fun `Lorsque j'essaye de créer un jeu qui existe déjà il y a conflit`() {
        // GIVEN
        mariaDbJeuxRepository.saveAndFlush(JeuFixture.creerJeuDto.toJeu().toJeuxDocument())
        val jeuACreer = JeuFixture.creerJeuDto
        // WHEN
        mockMvc.post("/tcgstat/jeux") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(jeuACreer)
        }
            // THEN
            .andExpect {
                status { isConflict() }
                content { contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE) }
            }

    }
}