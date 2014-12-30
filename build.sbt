import AssemblyKeys._ // for "assenbly" plugin

name := "Scala_test_again"

version := "1.0"

scalaVersion := "2.11.4"

// add LWJGL library depencency
libraryDependencies += "org.lwjgl.lwjgl" % "lwjgl" % "2.9.1"

// enable assembly plugin
assemblySettings