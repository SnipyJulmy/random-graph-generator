package ch.snipy.randgg.generator

import ch.snipy.randgg.Config
import ch.snipy.randgg.generator.Utils._

import scala.util.Random

class RandomGraphGenerator(config: Config) {

  private val r = new Random()
  private val csvPrinter = new CsvPrinter(config)

  def generateGraphs(): Unit = {
    for (i <- 0 to config.nWith) {
      val g = nextGraph(true)
      if (config.generatePermutation) {
        for ((p, idx) <- g.permutations.zipWithIndex) {
          csvPrinter.dump(p, s"graph_${i}_p_$idx", withCycle = true)
        }
      } else
        csvPrinter.dump(g, s"graph_$i", withCycle = true)
    }
    for (i <- 0 to config.nWithout) {
      val g = nextGraph(false)
      if (config.generatePermutation) {
        for ((p, idx) <- g.permutations.zipWithIndex) {
          csvPrinter.dump(p, s"graph_${i}_p_$idx", withCycle = false)
        }
      } else
        csvPrinter.dump(g, s"graph_$i", withCycle = false)
    }
  }

  def nextGraph(withCycle: Boolean): Graph = {
    val nbNodesToGenerate: Int = r.nextInRange(config.nodesArg)
    val nbEdges: Int = r.nextInRange(config.edgesArg)

    println(s"generate a random graph with $nbNodesToGenerate nodes and $nbEdges edges")

    val g = new Graph(nbNodesToGenerate)
    g.addNode(0)
    genNodes(g, List(0), nbNodesToGenerate)
    if (withCycle) {
      genCycle(g)
    }
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
      } while (i == j || g.areConnected(i, j))
      g.addEdge(i, j, 2)
    }
  }

  def genNodes(graph: Graph, crtLeaves: List[Int], remainingPlace: Int): Unit =
    if (!graph.isFull) {
      // pick a random leaves
      val (chosenLeaf, newLeaves) = r.pick(crtLeaves)
      // generate the new children
      val generatedChildren = genChild(graph, chosenLeaf)
      genNodes(graph, newLeaves ++ generatedChildren, remainingPlace - generatedChildren.length)
    }

  // generate a random number of child for the root and return the list of the generated child indices
  def genChild(graph: Graph, root: Int): List[Int] = {
    val nbChild = {
      val t = r.nextInRange(config.degreeArg)
      if (t + graph.nbNodes >= graph.maxNodesCapacity) graph.maxNodesCapacity - graph.nbNodes - 1
      else t
    }
    val nbNodes = graph.nbNodes
    val children = for {
      n <- nbNodes to (nbChild + nbNodes)
      _ = graph.addNode(n)
      _ = graph.addEdge(root, n)
    } yield n
    children.toList
  }
}

