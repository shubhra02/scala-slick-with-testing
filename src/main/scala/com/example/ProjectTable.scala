package com.example

import com.example.Models.{Employee, Project}
import com.example.connectionProvider.{DBComponent, MySqlDBComponent}
import slick.dbio.Effect.Read
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import slick.model.ForeignKey
import slick.sql.FixedSqlStreamingAction

 trait ProjectTable extends EmployeeTable{

   this : DBComponent =>
   import driver.api._
  class ProjectTable(tag: Tag) extends Table[Project](tag, "projectTable"){
    val projectId = column[Int]("projectid", O.PrimaryKey, O.AutoInc)
    val empId = column[Int]("empid")
    val pName = column[String]("project_name")
    val projectDuration = column[Double]("project_duration")
    val empProjectFK = foreignKey("emp_project_fk", empId, employeeTableQuery)(_.id)

    def * = (projectId, empId, pName, projectDuration) <> (Project.tupled, Project.unapply)
  }
   val projectQuery = TableQuery[ProjectTable]
}

trait ProjectRepo extends ProjectTable {
  this : DBComponent =>
  import driver.api._            //object of db to hit query to db

  def create = db.run(projectQuery.schema.create)
  def insert(pro: Project) = db.run {
    projectQuery returning projectQuery.map(_.projectId) += pro
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

  def insertAtOnce(project1: Project, project2: Project, project3: Project): Future[Int] = {
    val query1 = projectQuery += project1
    val query2 = projectQuery += project2
    val query3 = projectQuery += project3
    db.run((query1 andThen query2 andThen(query3)).cleanUp(x => x match {case Some(_) => projectQuery.delete
                                                                          case None => projectQuery.result}))
//    db.run(query1 andThen query2 andThen(query3)
  }

  def plainSqlQuery = {
    val action = sqlu"Insert into projectTable values(7, 13, 'microsoft', 2.5);"
    db.run(action)
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


  def insertObject(project: Project) : Future[Project] = {
    val insertQuery = projectQuery returning projectQuery.map(_.projectId) into ((data, id) => data.copy(empId = id))
      val action = insertQuery += project
    db.run(action)
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
