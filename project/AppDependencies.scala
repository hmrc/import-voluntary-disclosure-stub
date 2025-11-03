import play.core.PlayVersion.current
import sbt._

object AppDependencies {

  val bootstrapVersion = "10.3.0"
  val playVersion      = 30

  val compile: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"                  %% s"bootstrap-backend-play-$playVersion" % bootstrapVersion,
    "com.fasterxml.jackson.module" %% "jackson-module-scala"                 % "2.16.1"
  )

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"            %% s"bootstrap-test-play-$playVersion" % bootstrapVersion,
    "org.scalamock"          %% "scalamock"                         % "7.5.0",
    "org.scalatest"          %% "scalatest"                         % "3.2.19",
    "com.vladsch.flexmark"    % "flexmark-all"                      % "0.64.8",
    "org.scalatestplus.play" %% "scalatestplus-play"                % "7.0.2"
  ).map(_ % Test)

}
