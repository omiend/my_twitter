// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.4")

addSbtPlugin("org.scalikejdbc" %% "scalikejdbc-mapper-generator" % "2.4.2")

// For Slick Code Gen
libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java"  % "5.1.39"
)

resolvers ++= Seq(
  "Flyway" at "https://flywaydb.org/repo"
)

addSbtPlugin("org.flywaydb" % "flyway-sbt" % "4.0.3")
