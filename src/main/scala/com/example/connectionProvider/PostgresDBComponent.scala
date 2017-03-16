package com.example.connectionProvider

import slick.jdbc.{JdbcProfile, PostgresProfile}
import slick.jdbc.PostgresProfile.api._

trait PostgresDBComponent extends DBComponent{
  val driver = PostgresProfile
  import driver.api._
  val db : Database = Database.forConfig("myPostgresDB")

}
