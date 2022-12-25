package com.hiroshisasmita.feature.domain.model

data class MovieVideoDomain(
    var iso6391: String,
    var iso31661: String,
    var name: String,
    var key: String,
    var site: String,
    var size: Int,
    var type: String,
    var official: Boolean,
    var publishedAt: String,
    var id: String
)
