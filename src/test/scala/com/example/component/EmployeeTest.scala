package com.example.component

import com.example.EmployeeRepo
import com.example.Models.Employee
import com.example.connection.H2DBComponent
import org.scalatest.AsyncFunSuite

class EmployeeTest extends AsyncFunSuite{
  object testing extends EmployeeRepo with H2DBComponent {

    test("Add Employee record") {
      testing.insert(Employee(14,"shivangi",1.5)).map(data=>assert(data == 8))
    }

    test("update Employee record ") {
      testing.update(Employee(12, "ankita", 3.5)).map(data=>assert(data == 1))
    }

    test("delete Employee record ") {
      testing.delete(15).map(data=>assert(data == 1))
    }

    test("retrieve all Employee records") {
      testing.getAllEmployees.map(data=>assert(data == 3))
    }

    test("update or insert Employee record") {
      testing.upsert(Employee(14, "Aditya", 2.5)).map(data=>assert(data == false || data == true))
    }

    test("retrieve employees with experience more than 2 years") {
      testing.seniorEmployees.map(data=>assert(data.length == 2))
    }
  }

}
