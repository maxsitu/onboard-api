organization in ThisBuild := "com.axp"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.12.4"

resolvers += "softprops-maven" at "http://dl.bintray.com/content/softprops/maven"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test
val accord = "com.wix" %% "accord-core" % "0.7.3"
val jwt = "com.pauldijou" %% "jwt-play-json" % "2.1.0"

lazy val `onboard-api` = (project in file("."))
  .aggregate(
    `onboard-api-api`,
    `onboard-api-impl`,
    `onboard-api-stream-api`,
    `onboard-api-stream-impl`,
    `common`,
    `onboard-identity-api`,
    `onboard-identity-impl`,
  )

lazy val `onboard-api-api` = (project in file("onboard-api-api"))
  .settings(libraryDependencies ++= Seq(lagomScaladslApi))

lazy val `onboard-api-impl` = (project in file("onboard-api-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`onboard-api-api`)

lazy val `onboard-api-stream-api` = (project in file("onboard-api-stream-api"))
  .settings(libraryDependencies ++= Seq(lagomScaladslApi))

lazy val `onboard-api-stream-impl` =
  (project in file("onboard-api-stream-impl"))
    .enablePlugins(LagomScala)
    .settings(
      libraryDependencies ++= Seq(lagomScaladslTestKit, macwire, scalaTest)
    )
    .dependsOn(`onboard-api-stream-api`, `onboard-api-api`)

lazy val `common` = (project in file("common"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi,
      lagomScaladslServer,
      jwt,
      accord
    )
  )

lazy val `onboard-identity-api` = (project in file("onboard-identity-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi,
      lagomScaladslServer,
      jwt,
      accord
    )
  )

lazy val `onboard-identity-impl` = (project in file("onboard-identity-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslTestKit,
      macwire,
      scalaTest,
      jwt
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`common`, `onboard-identity-api`)
