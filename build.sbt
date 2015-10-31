import de.johoop.testngplugin.TestNGPlugin._

name := "ObjectFactory"

version := "1.0"

scalaVersion := "2.11.6"

organization := "com.amazon.datagen"

organizationName := "Amazon Technologies, Inc."

description := "a tool which assists in creating randomized Java objects in an easy way"

licenses += "Apache License Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")

libraryDependencies ++= Seq(
  "com.google.guava" % "guava" % "18.0",
  "org.apache.commons" % "commons-lang3" % "3.4",
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.testng" % "testng" % "6.8.21" % "test",
  "org.easymock" % "easymock" % "3.3.1" % "test",
  "org.easymock" % "easymockclassextension" % "3.2" % "test",
  "org.hamcrest" % "hamcrest-library" % "1.3" % "test"
)

testNGSettings

testNGSuites := Seq("src/test/resources/testng.xml")

resolvers += Resolver.sbtPluginRepo("releases")

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

pomIncludeRepository := { _ => false }

pomExtra := (
    <url>"https://github.com/leakingtapan/rof"</url>

    <scm>
      <url>https://github.com/leakingtapan/rof</url>
      <connection>scm:git:https://github.com/leakingtapan/rof.git</connection>
    </scm>

    <developers>
      <developer>
        <id>leakingtapan</id>
        <name>Cheng Pan</name>
        <url>http://leakingtapan.github.io</url>
      </developer>
    </developers>
)

