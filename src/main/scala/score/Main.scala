package score

import cats.effect._
import cats.implicits._
import score.LiveGame

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    for {
      game <- LiveGame.make[IO]
      //rolls = List.fill(12)(10)
      //rolls = List(9,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0)
      rolls = List.fill(21)(5)
      _ <- rolls.map(game.roll(_)).sequence
      s <- game.score()
      _ <- IO { println(s"result: $s") }
    } yield ExitCode(0)

  }
}
