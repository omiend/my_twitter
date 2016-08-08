package services

import models.{Users, UsersView}
import scalikejdbc.DBSession
import util.Errors

/**
  * Created by kazuomi.endo on 2016/08/08.
  */
class UsersService {
  def list(implicit s: DBSession): Either[Errors, List[UsersView]] = {
    Right(
      Users.findAll().map { user =>
        UsersView(
          id  = user.id,
          userId = user.userId,
          name = user.name
        )
      }
    )
  }
}
