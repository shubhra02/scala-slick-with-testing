package com.example.connectionProvider

import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._


trait DBComponent {
  val driver: JdbcProfile
  val db: Database

}
