name := "bowling"
version := "0.0.1-SNAPSHOT"

scalaVersion := "2.13.1"

libraryDependencies += "org.typelevel" %% "cats-core" % "2.0.0"
libraryDependencies += "org.typelevel" %% "cats-effect" % "2.2.0"
libraryDependencies += "org.scalatest"     %% "scalatest"       % "3.2.2"
libraryDependencies += "org.scalatestplus" %% "scalacheck-1-14" % "3.2.2.0"

// scalac options come from the sbt-tpolecat plugin so need to set any here

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full)
