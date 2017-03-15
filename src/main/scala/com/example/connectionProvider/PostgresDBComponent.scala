package com.example.connectionProvider

import slick.jdbc.{JdbcProfile, PostgresProfile}
import slick.jdbc.PostgresProfile.api._

trait PostgresDBComponent {
  val driver : JdbcProfile = PostgresProfile
  val db : Database = Database.forConfig("myPostgresDB")

}
