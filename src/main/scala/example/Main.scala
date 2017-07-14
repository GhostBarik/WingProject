package example

import org.denigma.threejs.{MeshBasicMaterialParameters, _}
import org.scalajs.dom
import org.scalajs.dom.window

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{literal => JSObject}


object Main {


  /**
   * simple cube, that will be rendered in scene
   */
  val cube: Mesh = createCube()


  def main(args: Array[String]): Unit = {

    val renderer = createRenderer()
    val camera = createCamera()

    val scene = new Scene()
    scene.add(cube)

    animate(0) {

      cube.rotation.x += Config.rotationSpeed
      cube.rotation.y += Config.rotationSpeed

      renderer.render(scene, camera)
    }
  }

  def animate(timestamp: Double)(sceneUpdateCallback: => Unit) {

    // update scene
    sceneUpdateCallback

    // request next animation frame
    val callback: js.Function1[Double, Any] = animate(_)(sceneUpdateCallback)
    window.requestAnimationFrame(callback)
  }

  def createCube(): Mesh = {

    val geometry = new BoxGeometry(1, 1, 1)
    val mat = JSObject("color" -> 0x00ff00)
    val material = new MeshBasicMaterial(mat.asInstanceOf[MeshBasicMaterialParameters])

    new Mesh(geometry, material)
  }

  def createRenderer(): WebGLRenderer = {

    val renderer = new WebGLRenderer()
    renderer.setSize( window.innerWidth * 0.75, window.innerHeight * 0.75)
    dom.document.body.appendChild( renderer.domElement )

    renderer
  }

  def createCamera(): PerspectiveCamera = {

    val camera = new PerspectiveCamera( 75, window.innerWidth/window.innerHeight, 0.1, 1000 )
    camera.position.z = 5

    camera
  }
}
