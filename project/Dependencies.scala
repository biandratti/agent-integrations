import play.sbt.PlayImport.guice
import sbt._

object Dependencies {

  val appDependencies: Seq[ModuleID] = {
    Seq(
      guice,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
    )
  }
}
