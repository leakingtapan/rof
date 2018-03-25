name := "ObjectFactory"

version := "1.1.0"

organization := "com.amazon.datagen"

organizationName := "Amazon Technologies, Inc."

description := "a tool which assists in creating randomized Java objects in an easy way"

licenses += "Apache License Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")

libraryDependencies ++= Seq(
  "com.google.guava" % "guava" % "21.0",
  "org.apache.commons" % "commons-lang3" % "3.4",
  "cglib" % "cglib" % "3.2.0",
  "org.testng" % "testng" % "6.11" % "test",
  "org.easymock" % "easymock" % "3.4" % "test"
)

// Checkstyle settings
checkstyleConfigLocation := CheckstyleConfigLocation.File("configuration/checkstyle/checkstyle-8.8-config.xml")

(checkstyle in Test) := (checkstyle in Test).triggeredBy(compile in Test).value
(checkstyle in Compile) := (checkstyle in Compile).triggeredBy(compile in Compile).value

checkstyleSeverityLevel := Some(CheckstyleSeverityLevel.Warning)

// TestNG settings
enablePlugins(TestNGPlugin)

testNGSuites := Seq("tst/resources/testng.xml")

resolvers += Resolver.sbtPluginRepo("releases")

// Java Project Settings
// Do not append Scala versions to the generated artifacts
crossPaths := false

// This forbids including Scala related libraries into the dependency
autoScalaLibrary := false

javaSource in Compile := baseDirectory.value / "src"

javaSource in Test := baseDirectory.value / "tst"

resourceDirectory in Test := baseDirectory.value / "tst" / "resources"

// Sonatype Publish
publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

pomIncludeRepository := { _ => false }

pomExtra := <url>"https://github.com/leakingtapan/rof"</url>

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

