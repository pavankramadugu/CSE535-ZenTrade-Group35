package com.asu.mc.zentrade.models

import com.asu.mc.zentrade.activities.RecommendationsActivity
import org.apache.commons.collections.functors.TruePredicate

data class RecommendationsRequest(
    val frequency: String,
    val appetite: String,
    val countries: List<String>
)

data class RecommendationsResponse(
    val recommendations: List<String>
)

data class UserRequest(
    val  firstname : String,
    val lastname  :  String,
    val email : String,
    val frequency : String,
    val appetite :String,
    val stress : String,
    val recommendations: List<String>
)

data class UserResponse(
    val inserted_id : String,
    val message: String
)