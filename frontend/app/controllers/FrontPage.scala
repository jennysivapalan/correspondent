package controllers

import play.api.mvc.{ Action, Controller }
import configuration.Config
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.ws.WS
import play.api.Play.current
import model._
import play.api.libs.json._


trait FrontPage extends Controller {

    def index = Action.async { implicit request =>
      WS.url(getBundle()).get().map { response =>
        val json = (response.json) \ "data"
        val headline = (json \ "fields" \ "headline" \ "data" )
        val mainBlock = (json \ "mainBlock" \ "data" \ "elements")(0) //assumes first block is image
        val images = (mainBlock \ "assets").as[Seq[JsValue]]

        val image = images.filter( image => (image \ "fields" \ "width").asOpt[String].exists( _ == "620"))

        val dataBlocks = (json \ "blocks" \ "data").as[Seq[JsValue]]
        val keyEvents = dataBlocks.map { block =>

          val id = Json.stringify(block \ "data" \ "id")
          val title =(block \ "data" \ "attributes" \ "title").asOpt[String]
          new KeyEvent(id, title)

        }

        val blocks = dataBlocks.map {block =>
          val id = Json.stringify(block \ "data" \ "id")
          val elementsData = (block \ "data" \ "elements").as[Seq[JsValue]]
          val elements = elementsData.map {
            e =>
            val elementType = Json.stringify(e \ "elementType")
            val text = (e \ "fields" \ "text").asOpt[String]
            val html = (e \ "fields" \ "html").asOpt[String]
            val headline = (e \ "fields" \ "headline").asOpt[String]
            val originalUrl = (e \ "fields" \ "id").asOpt[String]
            new Element(elementType, text, html, headline, originalUrl )

          }
          new Block(id, elements.toList)
        }
        val bundle = new Bundle(Json.stringify(headline), Json.stringify(image(0) \ "url"), keyEvents.toList, blocks.toList)

        Ok(views.html.index(bundle))

      }
  }

  private def getBundle(): String = {
    "http://%s:9081/api/content/53c3feb83004f2a0164537b9/live".format(Config.composer)
  }

}

object FrontPage extends FrontPage

