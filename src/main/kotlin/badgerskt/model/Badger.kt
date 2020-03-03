package badgerskt.model

import arrow.core.Option
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class Badger(val name: String, val dob: Option<LocalDate>, val foods: List<Food>)