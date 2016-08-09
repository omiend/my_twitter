package util

import play.api.libs.json.Json

case class Errors(msg: String)

object Errors {
  implicit val writes = Json.writes[Errors]
}