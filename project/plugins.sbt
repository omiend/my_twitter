// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.4")

// For Slick Code Gen
libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java"  % "5.1.39"
)

addSbtPlugin("org.scalikejdbc" %% "scalikejdbc-mapper-generator" % "2.4.2")
