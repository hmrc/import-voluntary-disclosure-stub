import play.core.PlayVersion.current
import sbt._

object AppDependencies {

  val bootstrapVersion = "8.4.0"

  val compile = Seq(
    "uk.gov.hmrc"                  %% "bootstrap-backend-play-28" % bootstrapVersion,
    "com.fasterxml.jackson.module" %% "jackson-module-scala"      % "2.16.1"
  )

  val test = Seq(
    "org.scalamock"          %% "scalamock"              % "5.2.0"  % Test,
    "com.github.tomakehurst"  % "wiremock-jre8"          % "2.33.2" % "test, it",
    "uk.gov.hmrc"            %% "bootstrap-test-play-28" % bootstrapVersion  % Test,
    "org.scalatest"          %% "scalatest"              % "3.2.13" % "test, it",
    "com.typesafe.play"      %% "play-test"              % current  % Test,
    "com.vladsch.flexmark"    % "flexmark-all"           % "0.62.2" % "test, it",
    "org.scalatestplus.play" %% "scalatestplus-play"     % "5.1.0"  % "test, it"
  )

}
