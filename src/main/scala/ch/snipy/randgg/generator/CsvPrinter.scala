package ch.snipy.randgg.generator

import java.io.{File, IOException}

import ch.snipy.randgg.Config
import com.github.tototoshi.csv.CSVWriter

class CsvPrinter(config: Config) {

  private val outputDirectory = checkDir(config.outputDirectory)
  assert(outputDirectory.exists())
  private val withCycleOutputDirectory = mkDir(s"${outputDirectory.getPath}/cycle")
  private val withoutCycleOutputDirectory = mkDir(s"${outputDirectory.getPath}/nocycle")

  // dump the adjacency matrix into a csv file
  def dump(graph: Graph, filename: String, withCycle: Boolean): Unit = {

    val outputDirectoryName = s"${
      if (withCycle) withCycleOutputDirectory.getAbsolutePath
      else withoutCycleOutputDirectory.getAbsolutePath
    }"
    val outputFilename = s"$outputDirectoryName/${config.filePrefix}$filename.csv"
    val outputFile = mkFile(outputFilename)

    println(s"dump $filename into ${outputFile.getPath}")

    val writer = CSVWriter.open(outputFile)
    writer.writeAll(graph.adjacencyMatrix.toSeq.map(_.toSeq))
    writer.close()
  }


  private def mkFile(filename: String): File = mkFile(new File(filename))
  private def checkDir(directory: String): File = checkDir(new File(directory))
  private def mkDir(directory: String): File = mkDir(new File(directory))

  private def mkFile(file: File): File = {
    if (file.exists()) throw new IOException(s"${file.getPath} already exists")
    if (!file.createNewFile()) throw new IOException(s"can't create ${file.getPath}")
    assert(file.exists())
    assert(file.canWrite)
    assert(file.canRead)
    file
  }

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
