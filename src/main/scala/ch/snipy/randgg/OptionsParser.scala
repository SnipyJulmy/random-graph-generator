package ch.snipy.randgg

import java.io.File

class OptionsParser extends scopt.OptionParser[Config]("random-graph-ch.snipy.randgg.generator") {
  head("random-graph-ch.snipy.randgg.generator", "1.0")

  opt[Int]('n', "nb-nodes")
    .action((v, config) => config.copy(nbGraphToGenerate = v))
    .text(s"number of graphs to generate [default : ${Config.Default.nbGraphToGenerate}]")

  opt[Int]("min-nodes")
    .action((v, config) => config.copy(minNodesCount = v))
    .text(s"minimum number of nodes for the graphs [default : ${Config.Default.minNodesCount}]")

  opt[Int]("max-nodes")
    .action((v, config) => config.copy(maxNodesCount = v))
    .text(s"maximum number of nodes for the graphs [default : ${Config.Default.maxNodesCount}]")

  opt[Int]("min-edges")
    .action((v, config) => config.copy(minEdgesCount = v))
    .text(s"minimum number of edges for the graphs [default : ${Config.Default.minEdgesCount}]")

  opt[Int]("max-edges")
    .action((v, config) => config.copy(maxEdgesCount = v))
    .text(s"maximum number of edges for the graphs [default : ${Config.Default.maxEdgesCount}]")

  opt[Int]("min-degree")
    .action((v, config) => config.copy(minDegreePerNodes = v))
    .text(s"minimum degree of a node [default : ${Config.Default.minDegreePerNodes}]")

  opt[Int]("max-degree")
    .action((v, config) => config.copy(maxDegreePerNodes = v))
    .text(s"minimum degree of a node [default : ${Config.Default.maxDegreePerNodes}]")

  opt[Double]('p', "cycle-proportion")
    .action((v, config) => config.copy(withWithoutCycleProportion = v))
    .text(s"proportion of graph with and without cycle [default : ${Config.Default.withWithoutCycleProportion}]")

  opt[File]('o', "otuput-directory")
    .required()
    .valueName("<file>")
    .action((f, config) => config.copy(outputDirectory = f))
    .text("output directory for the graphs")

  opt[String]("prefix")
    .valueName("<prefix>")
    .action((p, config) => config.copy(filePrefix = p))
    .text("prefix of the generated files [default : \"\"]")
}
