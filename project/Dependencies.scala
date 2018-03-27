import sbt._

object Settings {
  val name                  = "play-rest-api"
}

object Versions {
  val scala                 = "2.12.4"

  // Database
  val play_slick            = "3.0.0"
  val slick                 = "3.2.2"

  val play_json             = "2.6.0"
  val mssql                 = "6.4.0.jre8"
}

object Dependencies {

  val root = Seq(
    "com.typesafe.play"           %% "play-json"              % Versions.play_json,
    "com.typesafe.play"           %% "play-slick"             % Versions.play_slick,
    "com.typesafe.slick"          %% "slick-codegen"          % Versions.slick,
    "com.microsoft.sqlserver"     %  "mssql-jdbc"             % Versions.mssql
  )

}