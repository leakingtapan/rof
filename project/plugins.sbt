logLevel := Level.Warn

addSbtPlugin("de.johoop" % "sbt-testng-plugin" % "3.1.1")

addSbtPlugin("com.etsy" % "sbt-checkstyle-plugin" % "3.1.1")
dependencyOverrides += "com.puppycrawl.tools" % "checkstyle" % "8.8"

addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "2.3")