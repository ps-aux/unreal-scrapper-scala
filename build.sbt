version := "0.1"
name := "unreal-scraper"

scalaVersion := "2.12.1"
sbtVersion := "0.13.8"

val undertowVersion = "1.4.8.Final"
val seleniumVersion = "2.52.0"
val springVersion = "4.3.6.RELEASE"

libraryDependencies ++= {
  Seq(
    "io.undertow" % "undertow-core" % undertowVersion,
    "io.undertow" % "undertow-servlet" % undertowVersion,

    "org.seleniumhq.selenium" % "selenium-java" % seleniumVersion,
    "org.seleniumhq.selenium" % "selenium-htmlunit-driver" % seleniumVersion,
    "org.seleniumhq.selenium" % "selenium-chrome-driver" % seleniumVersion,

    "org.springframework" % "spring-webmvc" % springVersion,
    "org.springframework" % "spring-context" % springVersion,
    "org.springframework" % "spring-jdbc" % springVersion,

    "org.postgresql" % "postgresql" % "9.4.1212.jre7",

    "ch.qos.logback" % "logback-classic" % "0.9.28"
  )
}


val stage = taskKey[Unit]("Stage task")

// Make state allias for assembly (required by Heroku)
stage <<= assembly map { x => x }
