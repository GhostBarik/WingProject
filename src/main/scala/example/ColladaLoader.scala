package example

import org.denigma.threejs.Loader

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

// TODO: move to another package
@js.native
@JSGlobal("THREE.ColladaLoader")
class ColladaLoader extends Loader {

  def load(url: String, readyCallback: js.Function1[js.Any, Unit],
           progressCallback: js.Function1[js.Any, Unit] = js.native,
           failCallback : js.Function1[js.Any, Unit] = js.native): Unit = js.native
}
