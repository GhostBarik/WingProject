package example

import example.typings.ColladaLoader
import org.denigma.threejs.{Scene, Texture}

import scala.concurrent.Future
import scala.scalajs.js
import scala.util.Try


object Loaders {

  def loadSceneFromCollada(sceneUrl: String): Future[Scene] = {

    val loader = new ColladaLoader()

    val scenePromise = concurrent.Promise[Scene]()

    loader.load(sceneUrl, (collada: js.Any) => {

      val colladaScene: Scene = collada.asInstanceOf[js.Dynamic].scene.asInstanceOf[Scene]
      scenePromise.complete(Try(colladaScene))
    })

    scenePromise.future
  }

  def loadTexture(textureUrl: String): Future[Texture] = {

    val loader = new typings.TextureLoader()

    val texturePromise = concurrent.Promise[Texture]()

    loader.load(textureUrl, (texture: Texture) => {
      texturePromise.complete(Try(texture))
    })

    texturePromise.future
  }
}
