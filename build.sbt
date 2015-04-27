name := "ObjectFactory"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "com.google.guava" % "guava" % "18.0",
  "org.apache.commons" % "commons-lang3" % "3.4",
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.testng" % "testng" % "6.8.21" % "test",
  "org.easymock" % "easymock" % "3.3.1" % "test",
  "org.hamcrest" % "hamcrest-library" % "1.3" % "test"
)