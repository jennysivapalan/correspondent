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
		   List(KeyEvent("test-id-1", "This is a test event"), KeyEvent("test-id-2", "I annotated!"), KeyEvent("test-id-3", "Twitter embed")),
		   List(Block("test-id-1", "annotation", "<p>This is an annotation.</p>", None),
			  Block("test-id-2", "twitter", "<blockquote class=\"twitter-tweet\"><p>My latest on the WSJ front-page with <a href=\"https://twitter.com/joshmitnick\">@joshmitnick</a> on the 17,000 who fled northern Gaza <a href=\"http://t.co/LTF44N7QCM\">http://t.co/LTF44N7QCM</a>.</p>&mdash; Nick Casey (@caseysjournal) <a href=\"https://twitter.com/caseysjournal/statuses/488598432978718720\">July 14, 2014</a></blockquote>\n", None),
			  Block("test-id-3", "embed", "<iframe height='405' width='590' frameborder='0' allowtransparency='true' scrolling='no' src='http://www.strava.com/activities/162955112/embed/6f51d9f6d2ccfb6ee7db617ce783e4c4877f12ca'></iframe>", None),
			  Block("test-id-4", "guardian-content", "<p></p><p>\"I should like to add that I have dedicated my life to public service, to the pursuit of justice and to protecting the rights of children and families and I wish the inquiry success in its important work.\"</p><p>Theresa May said: \"I am deeply saddened by Baroness Butler-Sloss's decision to withdraw but understand and respect her reasons. Baroness Butler-Sloss is a woman of the highest integrity and compassion and continues to have an enormous contribution to make to public life.</p><p>\"As she has said herself, the work of this inquiry is more important than any individual and an announcement will be made on who will take over the chairmanship and membership of the panel as soon as possible so this important work can move forward.\"</p><p>Downing Street pointed out that it had been \"entirely\" the decision of Butler-Sloss to stand down. No 10 went out of its way to praise her work, describing her work as chair of the Cleveland child abuse inquiry as groundbreaking.</p><p>The prime minister's spokesman said: \"Dame Elizabeth has taken the decision to step down as the head of the panel inquiry. It is entirely her decision. Understanding and respecting her decision, the government's view has not changed: that she would have done a first-class job as chair of the panel. But she has taken that decision.\"</p><p>Keith Vaz, the chairman of the Commons home affairs select committee who raised concerns about the appointment with the Home Office permanent secretary, Mark Sedwill, last week, said that the inquiry was now becoming shambolic.</p><p>Vaz said: \"I am not surprised by this decision – it is the right one. As I pointed out to Mr Sedwill the public would be concerned that a member of parliament, no matter how distinguished, had been appointed to head this important panel. The whole inquiry process is becoming shambolic: missing files, ministers refusing to read reports and now the chair resigning before the inquiry is has even commenced.\"</p><p>Butler-Sloss's decision to stand down is a blow to the government, which appeared to have rushed into appointing her. On Sunday last week Michael Gove said there would be no public inquiry. Within 24 hours the home secretary announced a wide-ranging inquiry that will examine how public institutions responded to allegations of child abuse.</p><p>There were suggestions that the Home Office overlooked Butler-Sloss's family links. Government sources insisted last week that it was well known that Butler-Sloss was the sister of Havers, attorney general from 1979 to 1987.</p><p>Earlier on Monday Baird, the police and crime commissioner for Northumbria who served as Labour's solicitor general from 2007 to 2010, said May had made an error in appointing Butler-Sloss because of her brother's role. The Butler-Sloss panel would have had to examine whether Havers played down allegations of child abuse during that period.</p><p>Baird told the Today programme on BBC Radio 4: \"If she were in a court case presiding over it and her brother were mentioned as someone she may have to investigate, she would of course withdraw due to a conflict of interest. The conflict of interest is even bigger here where we have a vulnerable community of people who say that they have been not allowed to get justice.</p><p>\"It is her task to look into it. It has got to be done by somebody who is an outsider to this, who is completely independent. Without wanting to descend totally to cliche, justice must not only be done but it has to be seen to be done.\"</p>", None)))

	val testAuthor = Author("Matt Andrews",
							"Matt is a web developer at the Guardian and definitely isn't just making up this bio for a hack day demo.",
							Stats(11, 43, 19), "assets/img/avatar.jpg")

  def index = Action { implicit request =>
	Ok(views.html.index(testBundle, testAuthor))
  }

  private def getBundle(): String = {
    "http://%s:9081/api/content/53c3feb83004f2a0164537b9/live".format(Config.composer) //todo add in a config
  }

}

object FrontPage extends FrontPage

