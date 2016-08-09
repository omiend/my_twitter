package controllers

import javax.inject.Inject

import models.UserCreation
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import scalikejdbc.DB
import services.UsersService
import util.{ControllerHelper, Errors}

class SignupController @Inject()(
  val usersService: UsersService
) extends Controller with ControllerHelper {

  def signup = Action(parse.json) { implicit req =>
    (for {
      creation <- req.body.validate[UserCreation].fold(err => Left(Errors(err.toString)), Right(_)).right
      user <- DB localTx { implicit session =>
        usersService.creation(creation).right
      }
    } yield Ok(Json.obj("user" -> user))).toResult
  }
}
