package com.frdx.tcgstats.joueur.serverside.dto

import com.frdx.tcgstats.joueur.domain.model.Joueur
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.*


@Entity
@Table(name = "jeux")
data class JeuxDocument(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    val nom: String,

    @ManyToMany(mappedBy = "jeux")
    val joueurs: MutableList<JoueurDocument> = mutableListOf(),
)
