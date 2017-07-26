package example.typings

import org.denigma.threejs.Texture

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal


// TODO: move to another package
@js.native
@JSGlobal("THREE.TextureLoader")
class TextureLoader extends js.Object {

  def load(url: String, readyCallback: js.Function1[Texture, Unit],
           progressCallback: js.Function1[js.Any, Unit] = js.native,
           failCallback : js.Function1[js.Any, Unit] = js.native): Unit = js.native
}
