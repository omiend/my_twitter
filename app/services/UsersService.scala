package services

import models.{Login, UserCreation, UserView}
import org.joda.time.DateTime
import scalikejdbc.interpolation.SQLSyntax
import scalikejdbc.{DBSession, Users}
import util.Errors

/**
  * Created by kazuomi.endo on 2016/08/08.
  */
class UsersService {

  def list(implicit s: DBSession): Either[Errors, List[UserView]] = {
    Right(
      Users.findAll().map(toUserView)
    )
  }

  def creation(creation: UserCreation)(implicit s: DBSession): Either[Errors, UserView] = {
    val now = DateTime.now
    Right(toUserView(
      Users.create(
        userId = creation.userId,
        name = creation.name,
        password = creation.password,
        versionNo = 1,
        createdAt = now,
        updatedAt = now
      )
    ))
  }

  def login(login: Login)(implicit s: DBSession): Either[Errors, Option[UserView]] = {
    Right{
      val whereClause = SQLSyntax.eq(Users.u.column("user_id"), login.userId).and.eq(Users.u.column("password"), login.password)
      Users.findBy(whereClause).map(toUserView)
    }
  }

  private def toUserView(users: Users): UserView = {
    UserView(
      id  = users.id,
      userId = users.userId,
      name = users.name
    )
  }

}
