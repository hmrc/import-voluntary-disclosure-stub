import play.core.PlayVersion.current
import sbt._

object AppDependencies {

  val compile = Seq(
    "uk.gov.hmrc" %% "bootstrap-backend-play-28" % "5.18.0",
    "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.13.0"
  )

  val test = Seq(
    "uk.gov.hmrc" %% "bootstrap-test-play-28" % "5.18.0" % Test,
    "org.scalatest" %% "scalatest" % "3.2.10" % Test,
    "com.typesafe.play" %% "play-test" % current % Test,
    "org.scalamock" %% "scalamock" % "5.1.0" % Test,
    "com.vladsch.flexmark" % "flexmark-all" % "0.62.2" % "test, it",
    "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % "test, it",
    "com.github.tomakehurst" % "wiremock-jre8" % "2.31.0" % "test, it"
  )
}
