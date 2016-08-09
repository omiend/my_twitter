package controllers

import javax.inject.Inject

import models.Login
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import scalikejdbc.DB
import services.UsersService
import util.{ControllerHelper, Errors}

class LoginController @Inject()(
  val usersService: UsersService
) extends Controller with ControllerHelper {

  def login = Action(parse.json) { implicit req =>
    (for {
      login <- req.body.validate[Login].fold(err => Left(Errors(err.toString)), Right(_)).right
      user <- DB localTx { implicit session =>
        usersService.login(login).right
      }
    } yield Ok(Json.obj("user" -> user))).toResult
  }
}
