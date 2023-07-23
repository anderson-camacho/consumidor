package model

import play.api.libs.json.Json

case class UserGet(
                    id: Int,
                    email: String,
                    first_name: String,
                    last_name: String,
                    avatar: String
                  )

object UserGet {
  // Utilizamos Json.format para generar automáticamente un Writes[UserGet]
  implicit val userWrites = Json.format[UserGet]
}