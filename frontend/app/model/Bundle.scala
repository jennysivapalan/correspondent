package model


case class Bundle(heading:Option[String], standfirst: Option[String], mainImage: String, keyEvents: List[KeyEvent], blocks:  List[Block])

case class KeyEvent(id: String, title:Option[String])

case class Block(id:String, elements: List[Element])

case class Element(elementType: String,
                   text: Option[String],
                   html: Option[String],
                   headline:Option[String],
                   originalUrl: Option[String])
