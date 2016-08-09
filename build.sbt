name := """twitter"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play"           % "1.5.1" % Test,
  "mysql"                   % "mysql-connector-java"         % "5.1.39",
  "org.scalikejdbc"        %% "scalikejdbc"                  % "2.4.2",
  "org.scalikejdbc"        %% "scalikejdbc-config"           % "2.4.2",
  "org.scalikejdbc"        %% "scalikejdbc-play-initializer" % "2.5.1",
  "com.offbytwo.keyczar"    % "keyczar"                      % "0.71g"
)

// for scalikejdbc-mapper-generator
scalikejdbcSettings

// for flyway
//Seq(flywaySettings: _*)

flywayDriver := "com.mysql.jdbc.Driver"

flywayUrl :=  "jdbc:mysql://localhost:3306/my_twitter"

flywayLocations := Seq("filesystem:conf/db/migration")

flywayUser := "root"

flywayPassword := ""

flywaySchemas := Seq("my_twitter")
