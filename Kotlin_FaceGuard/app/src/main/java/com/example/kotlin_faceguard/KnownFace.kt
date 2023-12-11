package com.yourpackage.api.com.example.kotlin_faceguard

import kotlinx.serialization.Serializable

@Serializable
data class KnownFace(
    @Serializable val id: Int,
    @Serializable val name: String,
    @Serializable val relation: String,
    @Serializable val date: String,
    @Serializable val imageUrl: String
)

