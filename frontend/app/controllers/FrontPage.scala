package controllers

import play.api.mvc.{ Action, Controller }
import configuration.Config
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.ws.WS
import play.api.Play.current
import model._

trait FrontPage extends Controller {

  val testBundle =
	Bundle("Brand new bundle created right here",
		   "http://www.gateplay.com/images/products/detail/Gateway_Game_Bundle_big.jpg",
		   List(KeyEvent("test-event-1", "This is a test event")),
		   List(Block("test-block-1", "annotation", "<p>This is an annotation.</p>", None)))

  def index = Action { implicit request =>
	Ok(views.html.index(testBundle))
  }

  private def getBundle(): String = {
    "http://%s:9081/api/content/53c3feb83004f2a0164537b9/live".format(Config.composer) //todo add in a config
  }

}

object FrontPage extends FrontPage

