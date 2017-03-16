import com.example.Models.{Dependent, Employee, Project}
import com.example._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

object ForAllRelations extends App{

  /**
    * for Employee table
    */
/*  val tableCreate = Await.result(ForEmployeeRepo.create, Duration.Inf)
  val insertRes = ForEmployeeRepo.insert(Employee(14, "nitin",4.0))
  val res = insertRes.map{
    res => s"$res row inserted"
  }.recover {
    case ex: Throwable => ex.getMessage
  }

  val deleteRes = ForEmployeeRepo.delete(14)

  val updateRes = Await.result(ForEmployeeRepo.update(Employee(12, "ankit", 2.5)), Duration.Inf)

  val getAllRes = Await.result(ForEmployeeRepo.getAllEmployees, Duration.Inf)
  println(getAllRes)
  val upsertRes = Await.result(ForEmployeeRepo.upsert(Employee(16, "shubham", 2.0)), Duration.Inf)
  val customRes = Await.result(ForEmployeeRepo.seniorEmployees, Duration.Inf)*/


  /**
    * for Project table
    */
 /* val tableCreate = Await.result(ForProjectRepo.create, Duration.Inf)
  println("table created")
 val insertRes = ForProjectRepo.insert(Project(4,12,"carbon data", 2.5))
  val res = insertRes.map{
    res => s"$res row inserted"
  }.recover {
    case ex: Throwable => ex.getMessage
  }

  val deleteRes = ForProjectRepo.delete(1)

  val updateRes = Await.result(ForProjectRepo.update(Project(4, 11, "carbon data", 2.5)), Duration.Inf)

  val getAllRes = Await.result(ForProjectRepo.getAllProjects, Duration.Inf)
  println(getAllRes)

  val upsertRes = Await.result(ForProjectRepo.upsert(Project(5, 12, "google", 3.0)), Duration.Inf)

  val customRes = Await.result(ForProjectRepo.oldProjects, Duration.Inf)
  println(customRes)

  val insertAtOnceRes = Await.result(ForProjectRepo.insertAtOnce(Project(4, 11, "google", 2.5),Project(5, 11, "carbon data", 2.5), Project(6, 12, "google", 3.5)), Duration.Inf)

  val insertAfterDeleteRes = Await.result(ForProjectRepo.insertAfterDelete(Project(7, 11, "microsoft", 1.5)), Duration.Inf)
    val plainSqlRes = Await.result(ForProjectRepo.plainSqlQuery, Duration.Inf)

  val insertObjectRes = Await.result(ForProjectRepo.insertObject(Project(9, 12, "hp", 3.5)), Duration.Inf)
  println(insertObjectRes)*/

/*  val joinResult = Await.result(ForProjectRepo.joiningTables, Duration.Inf)
  println(joinResult)*/

/*val joinResult = Await.result(ForProjectRepo.rightJoiningTables, Duration.Inf)
  println(joinResult)*/


/*  val zipResult = Await.result(ForProjectRepo.zippingTables, Duration.Inf)
  println(zipResult)*/

/*val zipWithJoinRes = Await.result(ForProjectRepo.zipWithJoinTables, Duration.Inf)
  println(zipWithJoinRes)*/



  /**
    * for Dependent Table
    */
/*val tableCreate = Await.result(ForDependentRepo.create, Duration.Inf)
val insertRes = ForDependentRepo.insert(Dependent(115,11,"Varun", "brother", 25))
val res = insertRes.map{
res => s"$res row inserted"
}.recover {
case ex: Throwable => ex.getMessage
}

val deleteRes = ForDependentRepo.delete(111)

val updateRes = Await.result(ForDependentRepo.update(1, "Hp"), Duration.Inf)

val getAllRes = Await.result(ForDependentRepo.getAllDependents, Duration.Inf)
val upsertRes = Await.result(ForDependentRepo.upsert(Dependent(112, 11, "Anshul", "Sister", 24)), Duration.Inf)
val customRes = Await.result(ForDependentRepo.customQuery, Duration.Inf)*/


Thread.sleep(10000)


}
