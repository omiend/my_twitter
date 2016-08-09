package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class UserView(
  id: ID,
  userId: String,
  name: String
)

object UserView {
//  implicit val writes = Json.writes[UserView]
  implicit val writes = (
    (__ \ "id").write[Long](CommonReadsWrites.idLongWrites) and
    (__ \ "userId").write[String] and
    (__ \ "name").write[String]
  )(unlift(UserView.unapply))
}

case class UserCreation(
  userId: String,
  name: String,
  password: String
)

object UserCreation {
//  implicit val reads = Json.reads[UserCreation]
  implicit val reads: Reads[UserCreation] = (
    (__ \ "userId").read[String] and
    (__ \ "name").read[String] and
    (__ \ "password").read[String]
  )(UserCreation.apply _)
}
