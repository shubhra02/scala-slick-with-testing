package com.example

import com.example.Models.{Employee, Project}
import com.example.connectionProvider.MySqlDBComponent
import slick.dbio.Effect.Read
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import slick.model.ForeignKey
import slick.sql.FixedSqlStreamingAction

 trait ProjectTable extends EmployeeTable{

   this : MySqlDBComponent =>
   import driver.api._
  class ProjectTable(tag: Tag) extends Table[Project](tag, "projectTable"){
    val projectId = column[Int]("projectid", O.PrimaryKey)
    val empId = column[Int]("empid")
    val pName = column[String]("project_name")
    val projectDuration = column[Double]("project_duration")
    val empProjectFK = foreignKey("emp_project_fk", empId, employeeTableQuery)(_.id)

    def * = (projectId, empId, pName, projectDuration) <> (Project.tupled, Project.unapply)
  }
   val projectQuery = TableQuery[ProjectTable]
}

trait ProjectRepo extends ProjectTable {
  this : MySqlDBComponent =>
  import driver.api._            //object of db to hit query to db

  def create = db.run(projectQuery.schema.create)
  def insert(pro: Project) = db.run {
    projectQuery += pro
  }
  def delete(id: Int): Future[Int] = {
    val query = projectQuery.filter(data => data.projectId === id)
    val delQuery = query.delete
    db.run(delQuery)

  }

  def update(project: Project): Future[Int] = {
    val query = projectQuery.filter(_.projectId === project.projectId)
    val res = query.map(_.pName).update(project.pName)
    db.run(res)
  }

  def upsert(proj: Project): Future[Int] = db.run{
    projectQuery.insertOrUpdate(proj)

  }

  def getAllProjects: Future[List[Project]] = db.run{
    projectQuery.to[List].result
  }

  def oldProjects: Future[List[Project]] = db.run{
    projectQuery.filter(_.projectDuration > 2.0).to[List].result

  }

  def insertAfterDelete(proj: Project): Future[Int] = {
    val query = projectQuery.filter(data => data.projectId === proj.projectId)
    val action1 = query.delete
    val action2 = projectQuery += proj
    db.run(action1 andThen(action2))

  }

  def insertAtOnce: Future[Int] = {
    val query1 = projectQuery += Project(4, 11, "google", 2.5)
    val query2 = projectQuery += Project(5, 11, "carbon data", 2.5)
    val query3 = projectQuery += Project(6, 12, "google", 3.5)
    db.run((query1 andThen query2 andThen(query3)).cleanUp(x => x match {case Some(_) => projectQuery.delete
                                                                          case None => projectQuery.result}))
//    db.run(query1 andThen query2 andThen(query3)
  }
/*
  def showNameAndId = {
    val filteredQuery = projectQuery.filter(data => data.pName === "carbon data")
    val query = (for(proj <- filteredQuery)) yield proj.
    val action = query.result
    db.run(action)
  }*/
def joiningTables = {
  val innerJoin = for {
    (e, p) <- employeeTableQuery join projectQuery on (_.id === _.empId)
  } yield (e.name, p.pName)
  db.run(innerJoin.to[List].result)
}
 def leftJoiningTables = {
   val leftOuterJoin = for {
     (e, p) <- employeeTableQuery joinLeft  projectQuery on (_.id === _.empId)
   } yield (e.name, p.map(_.pName))
   db.run(leftOuterJoin.to[List].result)
 }

  def rightJoiningTables = {
    val rightOuterJoin = for {
      (e, p) <- employeeTableQuery joinRight  projectQuery on (_.id === _.empId)
    } yield (e.map(_.name), p.pName)
    db.run(rightOuterJoin.to[List].result)
  }

  def zippingTables = {
    val zipJoinQuery = for {
      (e, p) <- employeeTableQuery zip projectQuery
    } yield (e.name, p.pName)
    db.run(zipJoinQuery.to[List].result)
  }

/*  def zipWithJoinTables = {
    val zipWithJoin = for {
      res <- employeeTableQuery.zipWith(projectQuery, (e: Employee, p: Project) => (e.name, p.pName))
    } yield res
    db.run(zipWithJoin)
  }*/



  /**
    * applying leftJoin to obtain answer same as left join
    */
  /*  def rightJoiningTables = {
    val rightOuterJoin = for {
      (p, e) <- projectQuery joinLeft employeeTableQuery  on (_.empId  === _.id)
    } yield (e.map(_.name),p.pName)
    db.run(rightOuterJoin.to[List].result)
  }*/


}
object ForProjectRepo extends ProjectRepo with MySqlDBComponent
