package models

import scalikejdbc._
import org.joda.time.DateTime
import play.api.libs.json.Json

case class UsersView(
  id: Long,
  userId: String,
  name: String
)

object UsersView {
  implicit val writes = Json.writes[UsersView]
}

case class Users(
  id: Long,
  userId: String,
  name: String,
  versionNo: Int,
  createdAt: DateTime,
  updatedAt: DateTime) {

  def save()(implicit session: DBSession = Users.autoSession): Users = Users.save(this)(session)

  def destroy()(implicit session: DBSession = Users.autoSession): Unit = Users.destroy(this)(session)

}


object Users extends SQLSyntaxSupport[Users] {

  override val tableName = "users"

  override val columns = Seq("id", "user_id", "name", "version_no", "created_at", "updated_at")

  def apply(u: SyntaxProvider[Users])(rs: WrappedResultSet): Users = apply(u.resultName)(rs)
  def apply(u: ResultName[Users])(rs: WrappedResultSet): Users = new Users(
    id = rs.get(u.id),
    userId = rs.get(u.userId),
    name = rs.get(u.name),
    versionNo = rs.get(u.versionNo),
    createdAt = rs.get(u.createdAt),
    updatedAt = rs.get(u.updatedAt)
  )

  val u = Users.syntax("u")

  override val autoSession = AutoSession

  def find(id: Long)(implicit session: DBSession = autoSession): Option[Users] = {
    withSQL {
      select.from(Users as u).where.eq(u.id, id)
    }.map(Users(u.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Users] = {
    withSQL(select.from(Users as u)).map(Users(u.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Users as u)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Users] = {
    withSQL {
      select.from(Users as u).where.append(where)
    }.map(Users(u.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Users] = {
    withSQL {
      select.from(Users as u).where.append(where)
    }.map(Users(u.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Users as u).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    id: Long,
    userId: String,
    name: String,
    versionNo: Int,
    createdAt: DateTime,
    updatedAt: DateTime)(implicit session: DBSession = autoSession): Users = {
    withSQL {
      insert.into(Users).namedValues(
        column.id -> id,
        column.userId -> userId,
        column.name -> name,
        column.versionNo -> versionNo,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt
      )
    }.update.apply()

    Users(
      id = id,
      userId = userId,
      name = name,
      versionNo = versionNo,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: Seq[Users])(implicit session: DBSession = autoSession): Seq[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'id -> entity.id,
        'userId -> entity.userId,
        'name -> entity.name,
        'versionNo -> entity.versionNo,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt))
        SQL("""insert into users(
        id,
        user_id,
        name,
        version_no,
        created_at,
        updated_at
      ) values (
        {id},
        {userId},
        {name},
        {versionNo},
        {createdAt},
        {updatedAt}
      )""").batchByName(params: _*).apply()
    }

  def save(entity: Users)(implicit session: DBSession = autoSession): Users = {
    withSQL {
      update(Users).set(
        column.id -> entity.id,
        column.userId -> entity.userId,
        column.name -> entity.name,
        column.versionNo -> entity.versionNo,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Users)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(Users).where.eq(column.id, entity.id) }.update.apply()
  }

}
