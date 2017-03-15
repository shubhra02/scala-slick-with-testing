package com.example.connectionProvider

import slick.jdbc.{JdbcProfile, MySQLProfile}
import slick.jdbc.MySQLProfile.api._

trait MySqlDBComponent{

 val driver : JdbcProfile =  MySQLProfile
  val db : Database = Database.forConfig("mysqlDB")

}
