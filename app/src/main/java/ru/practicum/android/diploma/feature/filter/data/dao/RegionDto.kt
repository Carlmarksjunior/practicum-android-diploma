package ru.practicum.android.diploma.feature.filter.data.dao

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.feature.search.data.dto.Response

class RegionDto : Response() {
    @SerializedName("id")
    val id: String? = null
    @SerializedName("parentId")
    val parentId: String? = null
    @SerializedName("name")
    val name: String? = null
    @SerializedName("areas")
    val areas: List<RegionDto>? = null
}
