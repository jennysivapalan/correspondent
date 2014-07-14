package controllers

import play.api.mvc.{ Action, Controller }

trait FrontPage extends Controller {

  def index = Action { implicit request =>
    Ok(views.html.index())
  }

}

object FrontPage extends FrontPage

