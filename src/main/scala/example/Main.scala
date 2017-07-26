package example

import org.denigma.threejs.{Mesh, MeshBasicMaterialParameters, _}
import org.scalajs.dom.window

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.scalajs.js
import scala.scalajs.js.Dynamic.{literal => JSObject}
import Constants.PI


object Main {


  /**
   * application entry point
   */
  def main(args: Array[String]): Unit = {

    InputHelper.initKeyboardListeners()

    val plane: Mesh = SceneFactory.createPlane()
    plane.rotation.x = -(PI / 2.0)
    plane.scale.set(2.0, 2.0, 2.0)


    val renderer = SceneFactory.createRenderer()
    val camera = SceneFactory.createCamera()


    // load scene from collada => once scene is loaded, start main loop
    for ( aircraft     <- loadAircraft();
          planeTexture <- Loaders.loadTexture("textures/test_surface.png")) {

      val scene = new Scene()

      // enable scene matrix automatic updates
      scene.autoUpdate = true
//      aircraft.autoUpdate = true

      // create light sources

      val light = new AmbientLight(0xffffff)
      light.asInstanceOf[js.Dynamic].intensity = 1.2 // FIXME: update Three.js facade traits
      scene.add(light)

      val directionalLight = new DirectionalLight( 0xffffff, 0.8 )
      directionalLight.position.set(-1, 1, 0)
      scene.add( directionalLight )

      // extract propeller object from scene graph
      val propeller: Object3D = aircraft.getObjectByName("Propeller", recursive = true)

      // initialize scene
      scene.add(aircraft)
      scene.add(plane)

      // TODO: move plane creation/configuration to factory object
      // set plane texture
      planeTexture.wrapS = THREE.RepeatWrapping
      planeTexture.wrapT = THREE.RepeatWrapping
      planeTexture.repeat.set(20, 20)
      planeTexture.anisotropy = 16
      plane.material = new MeshBasicMaterial(
        JSObject("map" -> planeTexture).asInstanceOf[MeshBasicMaterialParameters]
      )

      // init scene clock
      val clock = new Clock()

      // start rendering loop
      animate(0) {

        val timeDelta = clock.getDelta

        // rotate propeller
        propeller.rotation.y += Config.rotationSpeed * timeDelta



        // calculate aircraft rotation angle

        if (WorldState.moveLeft) {
          WorldState.vectorRotationAngleY += 0.6 * timeDelta
        }

        if (WorldState.moveRight) {
          WorldState.vectorRotationAngleY -= 0.6 * timeDelta
        }



        // TODO: simplify!!!
        // TODO: don't depend on sign (+/-), always calculate acceleration uniformly, than transform to Vector space!!

        // slowdown aircraft?
        if (WorldState.moveBackward) {

          val newVelocityZ = WorldState.aircraftSpeed - WorldState.aircraftSlowdown * timeDelta
          // TODO: move max speed to config
          WorldState.aircraftSpeed = clamp(newVelocityZ, 0, 8.0)
        }

        if (WorldState.moveForward) {

          val newVelocityZ = WorldState.aircraftSpeed + WorldState.aircraftAcceleration * timeDelta
          WorldState.aircraftSpeed = clamp(newVelocityZ, 0, 8.0)
        }

//        println(s"aircraft speed: ${WorldState.aircraftSpeed}")

        ///////////////////////////////////////////////////////////////

        // rotate velocity vector
        val rotatedVelocityVector = new Vector3(0,0,0)
        rotatedVelocityVector.copy(WorldState.aircraftVelocityVector)
        val axis = new Vector3(0,1,0)
        rotatedVelocityVector.applyAxisAngle(axis, WorldState.vectorRotationAngleY)


        // rotate aircraft base on the velocity vector orientation
        aircraft.rotation.y = WorldState.vectorRotationAngleY // FIXME: why Z is here ?????
//        aircraft.rotation.z = WorldState.vectorRotationAngleY * 4.0

//        println(s"vel vec: x - ${rotatedVelocityVector.x}, y - ${rotatedVelocityVector.y}, z - ${rotatedVelocityVector.z}")


        aircraft.position.x = aircraft.position.x +
          rotatedVelocityVector.x * WorldState.aircraftSpeed * timeDelta

        aircraft.position.y = aircraft.position.y +
          rotatedVelocityVector.y * WorldState.aircraftSpeed * timeDelta

        aircraft.position.z = aircraft.position.z +
          rotatedVelocityVector.z * WorldState.aircraftSpeed * timeDelta

        // FIXME: rotate camera, depending on the aircraft orientation
        // FIXME: derive camera position in disctinct method
        camera.position.z = 2.3 + aircraft.position.z
//        camera.position.y = 1.0
        camera.rotation.y = WorldState.vectorRotationAngleY

        // render scene
        renderer.render(scene, camera)
      }
    }
  }

  def clamp(v: Double, min: Double, max: Double): Double = if (v < min) min else if (v > max) max else v

  def loadAircraft(): Future[Object3D] = {

    Loaders.loadSceneFromCollada("models/test1.dae").map( aircraft => {

      // alight aircraft orientation along the axis
      aircraft.rotation.y = PI
      aircraft.rotation.x = PI / 2.0

//      aircraft.position.y = 0.6

//      aircraft.autoUpdate = true
//      aircraft

      val o = new Object3D()
      o.add(aircraft)
      o
    })
  }

  def animate(timestamp: Double)(sceneUpdateCallback: => Unit) {

    // update scene
    sceneUpdateCallback

    // request next animation frame from browser
    window.requestAnimationFrame(animate(_)(sceneUpdateCallback))

//    window.setTimeout( () => {
//
//      // request next animation frame from browser
//      window.requestAnimationFrame(animate(_)(sceneUpdateCallback))
//
//    }, 1000 / 20.0 )
  }
}
