package ch.snipy.randgg

import java.io.File

case class Config(nbGraphToGenerate: Int = Config.Default.nbGraphToGenerate,
                  minNodesCount: Int = Config.Default.minNodesCount,
                  maxNodesCount: Int = Config.Default.maxNodesCount,
                  minEdgesCount: Int = Config.Default.minEdgesCount,
                  maxEdgesCount: Int = Config.Default.maxEdgesCount,
                  minDegreePerNodes: Int = Config.Default.minDegreePerNodes,
                  maxDegreePerNodes: Int = Config.Default.maxDegreePerNodes,
                  generatePermutation: Boolean = Config.Default.generatePermutation,
                  withWithoutCycleProportion: Double = Config.Default.withWithoutCycleProportion,
                  nbCycleMax: Int = Config.Default.nbCycleMax,
                  outputDirectory: File = Config.Default.outputDirectory,
                  overwriteExistingOutput: Boolean = Config.Default.overwriteExistingOutput,
                  filePrefix: String = Config.Default.filePrefix) {

  lazy val nodesArg: (Int, Int) = (minNodesCount, maxNodesCount)
  lazy val edgesArg: (Int, Int) = (minEdgesCount, maxEdgesCount)
  lazy val degreeArg: (Int, Int) = (minDegreePerNodes, maxDegreePerNodes)
  lazy val (nWith, nWithout): (Int, Int) = {
    val nbWith = (nbGraphToGenerate * withWithoutCycleProportion).toInt
    (nbWith, nbGraphToGenerate - nbWith)
  }
}

object Config {
  object Default {
    val nbGraphToGenerate: Int = 100
    val minNodesCount: Int = 30
    val maxNodesCount: Int = 100
    val minEdgesCount: Int = 100
    val maxEdgesCount: Int = 200
    val minDegreePerNodes: Int = 2
    val maxDegreePerNodes: Int = 4
    val generatePermutation: Boolean = false
    val withWithoutCycleProportion: Double = 0.5
    val outputDirectoryPath: String = "./gen-graphs"
    val outputDirectory: File = new File(outputDirectoryPath)
    val overwriteExistingOutput: Boolean = true
    val filePrefix: String = ""
    val nbCycleMax: Int = 1
  }
}
