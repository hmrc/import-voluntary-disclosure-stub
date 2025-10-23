import uk.gov.hmrc.DefaultBuildSettings

val appName = "import-voluntary-disclosure-stub"

ThisBuild / majorVersion := 0
ThisBuild / scalaVersion := "3.3.6"

lazy val microservice = Project(appName, file("."))
  .enablePlugins(play.sbt.PlayScala,SbtDistributablesPlugin)
  .settings(
    PlayKeys.playDefaultPort := 7952,
    libraryDependencies ++= AppDependencies.compile ++ AppDependencies.test,
    scalacOptions += "-Wconf:src=routes/.*:s"
  )
  .settings(resolvers += Resolver.jcenterRepo)
  .settings(CodeCoverageSettings.settings *)

lazy val it = project
  .enablePlugins(PlayScala)
  .dependsOn(
    microservice % "test->test"
  ) // the "test->test" allows reusing test code and test dependencies
  .settings(DefaultBuildSettings.itSettings())
  .settings(libraryDependencies ++= AppDependencies.test)
