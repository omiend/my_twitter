package controllers

import javax.inject.Inject

import play.api.libs.json.Json
import play.api.mvc._
import scalikejdbc.DB
import services.UsersService
import util.ControllerHelper

class UsersController @Inject()(
  val usersService: UsersService
) extends Controller with ControllerHelper {

  def list = Action { implicit req =>
    (for {
      users <- DB localTx { implicit session =>
        usersService.list.right
      }
    } yield Ok(Json.obj("users" -> users))).toResult
  }

}
