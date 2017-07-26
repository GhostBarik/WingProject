package example

import org.denigma.threejs.Vector3

// TODO: split constants (move to config?) and stateful variables ???
object WorldState {

  /**
   * Stateful variables
   */
  var moveForward: Boolean = false
  var moveBackward: Boolean = false
  var moveLeft: Boolean = false
  var moveRight: Boolean = false

  // velocity unit(!) vector
  // TODO: this vector should remain unit - think of better abstraction?
  var aircraftVelocityVector: Vector3 = new Vector3(0,0,-1)
  var vectorRotationAngleY: Double = 0.0

  var aircraftSpeed: Double = 0.0

  /**
   * Constants
   */
  val aircraftSlowdown: Double = 6.0
  val aircraftAcceleration: Double = 9.0
}
