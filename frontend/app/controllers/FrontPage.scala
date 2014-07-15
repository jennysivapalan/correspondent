package controllers

import play.api.mvc.{ Action, Controller }
import configuration.Config
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.ws.WS
import play.api.Play.current
import play.api.libs.json._
import model._

object Util {
	def stripHtml(s: String) = s.replaceAll("</?[A-Za-z]+>", "")
}

trait FrontPage extends Controller {

	val testAuthor = Author("Matt Andrews",
							"Matt is a web developer at the Guardian and definitely isn't just making up this bio for a hack day demo.",
							Stats(11, 43, 19), "assets/img/avatar.jpg")

  def index = Action.async { implicit request =>
      WS.url(getBundle()).get().map { response =>
        val json = (response.json) \ "data"
        val headline = (json \ "fields" \ "headline" \ "data" ).asOpt[String]
        val standfirst = (json \ "fields" \ "standfirst" \ "data" ).asOpt[String]

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
        val bundle = new Bundle(headline, standfirst, keyEvents.toList, blocks.toList)

        Ok(views.html.index(bundle, testAuthor))

      }
  }

  private def getBundle(): String = {
    "http://%s:9081/api/content/53c3feb83004f2a0164537b9/live".format(Config.composer)
  }

}

object FrontPage extends FrontPage

