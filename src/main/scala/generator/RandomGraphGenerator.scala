package generator

import generator.Utils._

import scala.collection.mutable
import scala.util.Random

class RandomGraphGenerator(val minVertices: Int, val maxVertices: Int,
                           val minEdges: Int, val maxEdges: Int,
                           val minDegree: Int, val maxDegree: Int,
                           val withCycle: Boolean) {

  private val r = new Random()

  def nextGraph(): Graph = {
    val nbNodesToGenerate: Int = r.nextInRange(minVertices, maxVertices)
    val nbEdges: Int = r.nextInRange(minEdges, maxEdges)
    println(
      s"""
         | create a random graph with :
         | $nbNodesToGenerate vertices
         | $nbEdges edges
    """.stripMargin
    )

    val g = new Graph(nbNodesToGenerate)
    g.addNode(0)
    genNodes(g, mutable.Queue(0), nbNodesToGenerate)
    genCycle(g)
    g
  }

  // to gen a cycle, we pick n times 2 nodes that are not connected and connect them
  def genCycle(g: Graph, n: Int = 1): Unit = {
    for (_ <- 0 until n) {
      var i = 0
      var j = 0
      do {
        i = r.nextInt(g.maxNodesCapacity)
        j = r.nextInt(g.maxNodesCapacity)
      } while (i != j && g.areConnected(i, j))
      g.addEdge(i, j, 2)
      println(s"add cycle with $i -- $j")
    }
  }

  def genNodes(graph: Graph, crtLeaves: mutable.Queue[Int], remainingPlace: Int): Unit =
    if (!graph.isFull) {
      // pick a random leaves
      val chosenLeaf = r.pick(crtLeaves)
      val t = genChild(graph, chosenLeaf)
      genNodes(graph, crtLeaves ++ t, remainingPlace - t.length)
    }

  // generate a random number of child for the root and return the list of the generated child indices
  def genChild(graph: Graph, root: Int): mutable.Queue[Int] = {
    val nbChild = {
      val t = r.nextInRange(minDegree, maxDegree)
      if (t + graph.nbNodes >= graph.maxNodesCapacity) graph.maxNodesCapacity - graph.nbNodes - 1
      else t
    }
    val nbNodes = graph.nbNodes
    val children = for {
      n <- nbNodes to (nbChild + nbNodes)
      _ = graph.addNode(n)
      _ = graph.addEdge(root, n)
    } yield n
    mutable.Queue() ++= children
  }
}

