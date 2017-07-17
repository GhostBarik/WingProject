package example

import org.denigma.threejs.{Mesh, MeshBasicMaterialParameters, _}
import org.scalajs.dom
import org.scalajs.dom.window

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.scalajs.js
import scala.scalajs.js.Dynamic.{literal => JSObject}
import scala.util.Try


object Main {

  /**
   * simple cube, that will be rendered in scene
   */
  val cube: Mesh = createCube()

  /**
   * application entry point
   */
  def main(args: Array[String]): Unit = {

    val renderer = createRenderer()
    val camera = createCamera()

    // load scene from collada => once scene is loaded, start main loop
    loadSceneFromCollada("models/test1.dae").foreach{ scene  =>

      // enable scene matrix automatic updates
      scene.autoUpdate = true

      // create light sources

      val light = new AmbientLight(0xffffff)
      light.asInstanceOf[js.Dynamic].intensity = 1.2 // FIXME: update Three.js facade traits
      scene.add(light)

      val light2 = new PointLight(0xffffff, 10, 20)
      light2.position.set(6, 6, 9)
      scene.add(light2)

      // extract propeller object from scene graph
      val propeller: Object3D = scene.getObjectByName("Propeller", recursive = true)

      // rotate aircraft
      scene.rotation.x = 30.0
      scene.rotation.z = 31.0

      animate(0) {

        // rotate propeller
        propeller.rotation.y += Config.rotationSpeed * 10.0

        // render scene
        renderer.render(scene, camera)
      }
    }
  }

  def animate(timestamp: Double)(sceneUpdateCallback: => Unit) {

    // update scene
    sceneUpdateCallback

    // request next animation frame from browser
    window.requestAnimationFrame(animate(_)(sceneUpdateCallback))
  }

  def createCube(): Mesh = {

    val geometry = new BoxGeometry(1, 1, 1)
    val mat = JSObject("color" -> 0x00ff00)
    val material = new MeshBasicMaterial(mat.asInstanceOf[MeshBasicMaterialParameters])

    new Mesh(geometry, material)
  }

  def createRenderer(): WebGLRenderer = {

    val renderer = new WebGLRenderer()
    renderer.setSize(window.innerWidth * 0.75, window.innerHeight * 0.75)
    dom.document.body.appendChild( renderer.domElement )

    renderer
  }

  def createCamera(): PerspectiveCamera = {

    val camera = new PerspectiveCamera(75, window.innerWidth/window.innerHeight, 0.1, 1000)
    camera.position.z = 1.5

    camera
  }

  def loadSceneFromCollada(url: String): Future[Scene] = {

    // instantiate a loader
    val loader = new ColladaLoader()

    val scenePromise = concurrent.Promise[Scene]()

    loader.load(url, (collada: js.Any) => {

      val colladaScene: Scene = collada.asInstanceOf[js.Dynamic].scene.asInstanceOf[Scene]
      scenePromise.complete(Try(colladaScene))
    })

    scenePromise.future
  }
}
