package controllers

import play.api.mvc.{ Action, Controller }
import configuration.Config
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.ws.WS
import play.api.Play.current


trait FrontPage extends Controller {


  def index = Action.async {
      WS.url(getBundle()).get().map { response =>
        Ok(response.json \ "data" \ "blocks") //todo


    }

  }
  private def getBundle(): String = {
    "http://%s:9081/api/content/53c3feb83004f2a0164537b9/live".format(Config.composer) //todo add in a config
  }

}

object FrontPage extends FrontPage

