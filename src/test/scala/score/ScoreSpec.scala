package score

import cats.effect._
import cats.implicits.toTraverseOps
import org.scalatest.funsuite.AnyFunSuite

class ScoreSpec extends AnyFunSuite {

  def testGame(rolls: List[Int]): IO[Int] = {
    for {
      game <- LiveGame.make[IO]
      _ <- rolls.map(game.roll(_)).sequence
      s <- game.score()
    } yield s
  }

  test("twelve strikes") {
      val test: IO[Int] = testGame(List.fill(12)(10))
      assert(test.unsafeRunSync() === 300)
  }
}


