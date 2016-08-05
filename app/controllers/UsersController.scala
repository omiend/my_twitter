package controllers

import javax.inject.Inject

import play.api.mvc._

class UsersController @Inject() extends Controller {

  def list = Action {
    Ok
  }

}
