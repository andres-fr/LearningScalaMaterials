# call it with ./chapter7script.sh "https://github.com/andres-fr/bachelor-thesis/commits/master.atom"

#!/bin/sh
exec scala "$0" "$@"
!#
object HelloWorld {
  def main(args: Array[String]) {
    val url = args(0)
    val s = io.Source.fromURL(url)
    val text = s.getLines.map(_.trim).mkString("")
    val regx = """(<[\?\/]?[^>]*\??>)([^<]*)"""
    val rawList = regx.r.findAllIn(text).matchData.flatMap(_.subgroups).toList
    rawList.filter(!_.matches("")).sliding(4).collect {
      case List("<title>", x, "</title>", _) => x
      case List("<author>", "<name>", x, "</name>") => x
    }.foreach(println _)
  }
}
HelloWorld.main(args)
