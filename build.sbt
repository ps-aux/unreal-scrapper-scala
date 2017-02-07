version := "0.1"

scalaVersion := "2.11.6"

libraryDependencies ++= {
  Seq(
    "io.undertow" % "undertow-core" % "1.4.8.Final",
    "org.seleniumhq.selenium" % "selenium-java" % "2.52.0",
    "org.seleniumhq.selenium" % "selenium-htmlunit-driver" % "2.52.0",
    "org.seleniumhq.selenium" % "selenium-chrome-driver" % "2.52.0"
  )
}
libraryDependencies += "org.springframework" % "spring-context" % "4.3.6.RELEASE"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "0.9.28"

