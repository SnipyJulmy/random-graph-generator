package ch.snipy.randgg.generator

import scala.Function._
import scala.language.postfixOps
import scala.util.Random

class Graph(val maxNodesCapacity: Int) {


  private val data: Array[Array[Int]] = Array.fill(maxNodesCapacity, maxNodesCapacity)(0)
  private var _nbNodes: Int = 0
  private var _nbEdges: Int = 0

  def adjacencyMatrix: Array[Array[Int]] = data

  // generate all the permutations of the matrix
  def permutations: Iterator[Graph] = {
    val orig = (0 until _nbNodes).toList
    for {
      p <- orig.permutations
      g = permute(orig, p)
    } yield g
  }

  def nextPermutation: Graph = {
    val r = new Random
    val orig = (0 until _nbNodes).toList
    val p = r.shuffle(orig)
    println(s"permutation is $p")
    permute(orig, p)
  }

  // generate a permuted matrix given the permuted indices list
  def permute(originalIndices: List[Int], permutedIndices: List[Int]): Graph = {
    val res = new Graph(maxNodesCapacity)
    val map: Map[Int, Int] = (permutedIndices zip originalIndices).toMap
    for {
      i <- 0 until maxNodesCapacity
      j <- 0 until maxNodesCapacity
      if i <= j
    } {
      if (i == j) addNode(i)
      else {
        val v = data(map(j))(map(i))
        if (v == 1) res.addEdge(i, j)
        if (v == 2) res.addEdge(i, j, 2)
      }
    }
    res
  }

  def isFull: Boolean = _nbNodes == maxNodesCapacity

  def addNode(idx: Int): Unit = {
    data(idx)(idx) = 1
    _nbNodes += 1
  }

  def addEdge(from: Int, to: Int, weight: Int = 1): Unit = {
    data(from)(to) = weight
    data(to)(from) = weight
    _nbEdges += 1
  }

  def nbNodes: Int = _nbNodes

  def nbEdges: Int = _nbEdges

  // return all the edges of the graph
  def edges: List[(Int, Int)] = {
    for {
      i <- 0 until maxNodesCapacity
      j <- 0 until maxNodesCapacity
      if i < j
      if areConnected(i, j)
    } yield (i, j)
  } toList

  // return all the nodes of the graph
  def nodes: List[Int] = {
    for {
      i <- 0 until maxNodesCapacity
      if areConnected(i, i)
    } yield i
  } toList

  def areConnected(i: Int, j: Int): Boolean = data(i)(j) != 0

  // generate a dot graph
  def toDot: String =
    s"""
       |graph g$maxNodesCapacity {
       |${
      edges map tupled((a, b) =>
        s"""\t"$a" -- "$b" [color=${if (data(a)(b) == 1) "black" else "red"}]""") mkString "\n"
    }
       |}
  """.stripMargin

  override def toString: String =
    s"""
       |Graph(
       |${data.map(a => a.map(v => s"$v").mkString(",")).mkString("\n")}
       |)
  """.stripMargin
}
