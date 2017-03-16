package com.example.connectionProvider

import slick.jdbc.{JdbcProfile, MySQLProfile}
import slick.jdbc.MySQLProfile.api._

trait MySqlDBComponent extends DBComponent {

 val driver = MySQLProfile
 import driver.api._
  val db : Database = Database.forConfig("mysqlDB")

}
