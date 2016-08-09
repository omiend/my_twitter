package models

import play.api.libs.json._

case class Login(
  userId: String,
  password: String
) {
  def encryptPassword = {
    this.password
  }
}

object Login {
  implicit val reads = Json.reads[Login]
}
