import generator.RandomGraphGenerator

object Main extends App {
  val minVertices = 40
  val maxVertices = 100
  val minEdges = 5
  val maxEdges = 140
  val minDegree = 1
  val maxDegree = 3

  val graphGenerator = new RandomGraphGenerator(minVertices, maxVertices, minEdges, maxEdges, minDegree, maxDegree, true)

  println(graphGenerator.nextGraph().toDot)
}
