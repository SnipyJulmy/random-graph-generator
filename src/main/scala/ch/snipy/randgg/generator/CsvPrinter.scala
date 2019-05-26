package ch.snipy.randgg.generator

import java.io.{File, IOException}

import ch.snipy.randgg.Config

class CsvPrinter(config: Config) {

  private val outputDirectory = checkDir(config.outputDirectory)
  assert(outputDirectory.exists())
  private val withCycleOutputDirectory = mkDir(s"${outputDirectory.getPath}/cycle")
  private val withoutCycleOutputDirectory = mkDir(s"${outputDirectory.getPath}/nocycle")

  // dump the adjacency matrix into a csv file
  def dump(graph: Graph, filename: String, withCycle: Boolean): Unit = {
    println(s"dump $filename into ${if (withCycle) withCycleOutputDirectory.getPath else withoutCycleOutputDirectory.getPath}")
  }

  private def checkDir(directory: String): File = checkDir(new File(directory))
  private def mkDir(directory: String): File = mkDir(new File(directory))

  private def mkDir(directory: File): File = {
    assert(!directory.exists())
    if (!directory.mkdir()) {
      throw new IOException(s"can't create ${directory.getPath}")
    }
    directory
  }

  private def checkDir(directory: File): File = {
    if (directory.exists()) {
      if (config.overwriteExistingOutput) {
        if (!delete(directory))
          throw new IOException(s"can't remove existing ${directory.getPath}")
      } else
        throw new IOException(s"directory ${directory.getPath} already exists")
    }
    if (!directory.mkdir())
      throw new IOException(s"can't create ${directory.getPath}")
    directory
  }

  private def delete(directory: File): Boolean = {
    if (!directory.exists()) true
    else {
      if (directory.isDirectory) {
        directory.listFiles().foreach(delete)
        directory.delete()
      } else {
        directory.delete()
      }
    }
  }
}
