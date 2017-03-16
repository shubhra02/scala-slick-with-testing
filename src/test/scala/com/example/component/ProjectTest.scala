package com.example.component

import com.example.Models.{Employee, Project}
import com.example.ProjectRepo
import com.example.connection.H2DBComponent
import org.scalatest.AsyncFunSuite

class ProjectTest  extends AsyncFunSuite{
  object testing extends ProjectRepo with H2DBComponent {

      test("Add Project record") {
        testing.insert(Project(111,11,"google",1.5)).map(data=>assert(data == 8))
      }

      test("update Project record ") {
        testing.update(Project(112,11,"microsoft",2.5)).map(data=>assert(data == 1))
      }

      test("delete Project record ") {
        testing.delete(111).map(data=>assert(data == 1))
      }

      test("retrieve all Project records") {
        testing.getAllProjects.map(data=>assert(data == 3))
      }

      test("update or insert Project record") {
        testing.upsert(Project(113,12,"carbon data",3.5)).map(data=>assert(data == false || data == true))
      }

      test("get the Projects with duration more than 2 years") {
        testing.oldProjects.map(data => assert(data == 2))
      }

      test("insert record after delete") {
        testing.insertAfterDelete(Project(114, 13, "carbon data", 2.5)).map(data => assert(data == 1))
      }

      test("insert multiple records at once") {
        testing.insertAtOnce(Project(115, 11, "google", 2.5),Project(116, 11, "carbon data", 2.5), Project(117, 12, "google", 3.5)).map(data => assert(data == 3))
      }

      test("executing plain sql query") {
        testing.plainSqlQuery.map(data => assert(data == 1))
      }

      test("joining two tables using join") {
        testing.joiningTables.map(data => assert(data.size === 2))
      }

      test("joining two tables using leftjoin") {
        testing.leftJoiningTables.map(data => assert(data.size === 2))
      }

      test("joining two tables using leftjoin") {
        testing.zippingTables.map(data => assert(data.size === 2))
      }
    

    }

}
