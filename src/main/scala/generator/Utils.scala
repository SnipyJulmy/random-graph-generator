package generator

import scala.collection.mutable
import scala.util.Random

object Utils {
  implicit class RandomRange(random: Random) {
    def nextInRange(min: Int, max: Int): Int = {
      require(min <= max)
      if (min == max) min
      else min + random.nextInt(max - min)
    }

    // extract an randomly choose element from a Queue
    def pick[A](xs: mutable.Queue[A]): A = {
      random.shuffle(xs).dequeue()
    }
  }
}
