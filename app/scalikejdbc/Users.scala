package scalikejdbc

import scalikejdbc._
import org.joda.time.{DateTime}

case class Users(
  id: Long,
  userId: String,
  name: String,
  password: String,
  versionNo: Int,
  createdAt: DateTime,
  updatedAt: DateTime) {

  def save()(implicit session: DBSession = Users.autoSession): Users = Users.save(this)(session)

  def destroy()(implicit session: DBSession = Users.autoSession): Unit = Users.destroy(this)(session)

}


object Users extends SQLSyntaxSupport[Users] {

  override val tableName = "users"

  override val columns = Seq("id", "user_id", "name", "password", "version_no", "created_at", "updated_at")

  def apply(u: SyntaxProvider[Users])(rs: WrappedResultSet): Users = apply(u.resultName)(rs)
  def apply(u: ResultName[Users])(rs: WrappedResultSet): Users = new Users(
    id = rs.get(u.id),
    userId = rs.get(u.userId),
    name = rs.get(u.name),
    password = rs.get(u.password),
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
    userId: String,
    name: String,
    password: String,
    versionNo: Int,
    createdAt: DateTime,
    updatedAt: DateTime)(implicit session: DBSession = autoSession): Users = {
    val generatedKey = withSQL {
      insert.into(Users).namedValues(
        column.userId -> userId,
        column.name -> name,
        column.password -> password,
        column.versionNo -> versionNo,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt
      )
    }.updateAndReturnGeneratedKey.apply()

    Users(
      id = generatedKey,
      userId = userId,
      name = name,
      password = password,
      versionNo = versionNo,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: Seq[Users])(implicit session: DBSession = autoSession): Seq[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'userId -> entity.userId,
        'name -> entity.name,
        'password -> entity.password,
        'versionNo -> entity.versionNo,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt))
        SQL("""insert into users(
        user_id,
        name,
        password,
        version_no,
        created_at,
        updated_at
      ) values (
        {userId},
        {name},
        {password},
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
        column.password -> entity.password,
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
