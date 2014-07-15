package model


case class Bundle(heading:String, mainImage: String, keyEvents: List[KeyEvent], blocks:  List[Block])

case class KeyEvent(id: String, title:Option[String])

case class Block(id:String, elementType: String, html: String, originalUrl: Option[String])
