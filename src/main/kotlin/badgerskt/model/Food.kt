package badgerskt.model

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

sealed class Food

object Berries: Food()

object Nuts: Food()

object Seeds: Food()

object HedgehogsFeet: Food()

object FoodCodec {
    @ToJson
    fun toJson(food: Food): String {
        return when(food) {
            Berries -> "B"
            Nuts -> "N"
            Seeds -> "S"
            HedgehogsFeet -> "HF"
        }
    }

    @FromJson
    fun fromJson(str: String): Either<String, Food> {
        return when(str) {
            "B" -> Right(Berries)
            "N" -> Right(Nuts)
            "S" -> Right(Seeds)
            "HF" -> Right(HedgehogsFeet)
            else -> Left("Food $str not found")
        }
    }
}