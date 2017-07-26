package example

import org.scalajs.dom
import org.scalajs.dom.KeyboardEvent


object InputHelper {

  def initKeyboardListeners(): Unit = {

    println("init keyboard listeners..")

    dom.document.addEventListener("keydown", (event: KeyboardEvent) => {

      event.keyCode match {
        case 87 => WorldState.moveForward = true
        case 83 => WorldState.moveBackward = true
        case 65 => WorldState.moveLeft = true
        case 68 => WorldState.moveRight = true
        case _ => ()
      }


    }, useCapture = false)

    dom.document.addEventListener("keyup", (event: KeyboardEvent) => {

      event.keyCode match {
        case 87 => WorldState.moveForward = false
        case 83 => WorldState.moveBackward = false
        case 65 => WorldState.moveLeft = false
        case 68 => WorldState.moveRight = false
        case _ => ()
      }

    }, useCapture = false)
  }
}
