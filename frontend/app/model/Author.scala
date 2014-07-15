package model

case class Stats(bundles: Int, followers: Int, following: Int)
case class Author(name: String, profile: String, stats: Stats, avatarUrl: String)
