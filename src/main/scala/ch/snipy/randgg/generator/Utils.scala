package ch.snipy.randgg.generator

import scala.language.implicitConversions
import scala.util.Random

object Utils {
  implicit class RandomRange(random: Random) {

    def nextInRange(arg: (Int, Int)): Int = nextInRange(arg._1, arg._2)

    def nextInRange(min: Int, max: Int): Int = {
      require(min <= max)
      if (min == max) min
      else min + random.nextInt(max - min)
    }

    // extract an randomly choose element from a Queue
    def pick[A](xs: List[A]): (A, List[A]) = {
      val l = random.shuffle(xs)
      (l.head, l.tail)
    }
  }

  implicit def Array2D2Seq2D(input: Array[Array[Int]]): Seq[Seq[Int]] =
    input.map(_.toSeq).toSeq
}
