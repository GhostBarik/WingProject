package example

import org.denigma.threejs._
import org.scalajs.dom
import org.scalajs.dom.window

import scala.scalajs.js.Dynamic.{literal => JSObject}


object SceneFactory {

  def createCube(): Mesh = {

    val geometry = new BoxGeometry(1, 1, 1)
    val mat = JSObject("color" -> 0x00ff00)
    val material = new MeshBasicMaterial(mat.asInstanceOf[MeshBasicMaterialParameters])

    new Mesh(geometry, material)
  }

  def createPlane(): Mesh = {

    var geometry = new PlaneGeometry( 100, 100 )
    val material = new MeshBasicMaterial(JSObject("color" -> 0xffff00).asInstanceOf[MeshBasicMaterialParameters])

    new Mesh( geometry, material )
  }

  def createRenderer(): WebGLRenderer = {

    val renderer = new WebGLRenderer()
    renderer.setSize(window.innerWidth * 0.75, window.innerHeight * 0.75)
    dom.document.body.appendChild( renderer.domElement )

    renderer
  }

  def createCamera(): PerspectiveCamera = {

    val camera = new PerspectiveCamera(75, window.innerWidth/window.innerHeight, 0.1, 1000)

    camera.position.z = 2.3
    camera.position.y = 1.0
//    camera.position.x = 1.0

    camera.rotation.x = - 0.25
//    camera.rotation.y = 0.25

    camera
  }
}
