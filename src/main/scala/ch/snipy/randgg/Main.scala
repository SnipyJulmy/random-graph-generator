package ch.snipy.randgg

import ch.snipy.randgg.generator.RandomGraphGenerator

object Main extends App {

  val parser = new OptionsParser
  val config: Config = parser.parse(args, Config()) match {
    case Some(value) => value
    case None =>
      println(s"can't parse the arguments : ${args mkString ","}")
      System.exit(0)
      null // wtf
  }
  val graphGenerator = new RandomGraphGenerator(config)

  val g = graphGenerator.nextGraph(true)
  println(g.toDot)
  val gp = g.nextPermutation
  println(gp.toDot)
}
