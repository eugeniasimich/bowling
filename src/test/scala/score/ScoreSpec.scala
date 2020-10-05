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

  test("ten pairs of 9 and miss") {
    val test: IO[Int] = testGame(List.fill(10)(List(9,0)).flatten)
    assert(test.unsafeRunSync() === 90)
  }

  test("ten pairs of 5 and spare, with a final 5") {
    val test: IO[Int] = testGame(List.fill(21)(5))
    assert(test.unsafeRunSync() === 150)
  }

}


