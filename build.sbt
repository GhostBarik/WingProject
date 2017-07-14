enablePlugins(ScalaJSPlugin)

// scala version
scalaVersion := "2.12.2"

// project definition
name := "WingProject"
version := "0.0.1"

// allow SBT to automatically recognize and use main class of our application
scalaJSUseMainModuleInitializer := true

// resolver for Three.js facade library
resolvers += sbt.Resolver.bintrayRepo("denigma", "denigma-releases")

// libraries
libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.1"
libraryDependencies += "org.denigma" %%% "threejs-facade" % "0.0.77-0.1.8"


