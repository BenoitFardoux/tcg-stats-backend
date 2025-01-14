package com.frdx.tcgstats.jeux.serverside.dto

import com.frdx.tcgstats.joueur.serverside.dto.JoueurDocument
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import org.apache.commons.lang3.builder.ToStringExclude
import java.util.*


@Entity
@Table(name = "jeux")
data class JeuxDocument(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(unique = true)
    val nom: String,

    @ManyToMany(mappedBy = "jeux", cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @ToStringExclude
    val joueurs: MutableList<JoueurDocument> = mutableListOf()
)
