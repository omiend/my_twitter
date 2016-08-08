package util

import play.api.libs.json.Json
import play.api.mvc.Result
import play.api.mvc.Results._

trait ControllerHelper {
  implicit class RichEither(either: Either[Errors, Result]) {
    def toResult: Result = either match {
      case Right(r) => r
      case Left(errors) => BadRequest(Json.toJson(errors))
    }
  }
}
