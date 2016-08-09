package models

import play.api.libs.json.{JsString, Writes}

object CommonReadsWrites {
  val idLongWrites: Writes[ID] = new Writes[ID] {
    def writes(o: ID) = JsString(o.toString)
  }
}
