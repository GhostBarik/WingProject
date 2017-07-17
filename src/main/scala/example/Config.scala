package example

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("config")
object Config {

  @JSExport
  var rotationSpeed = 0.01

  // TODO: only for debugging purposes, remove after ...
  @JSExport
  var colladaLoadedScene: AnyRef = _
}
