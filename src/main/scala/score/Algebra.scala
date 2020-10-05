package score

import cats.syntax.all._
import cats.effect.Sync
import cats.effect.concurrent.Ref
import Model.{GameStatus, emptyGame}
import ScoreLogic.{computeNextStatus, computeScore}

trait Game[F[_]] {
  // Is called each time the player rolls a ball. The argument
  // is the number of pins knocked down.
  def roll(pins : Int): F[Unit]
  // Is called only at the very end of the game. It returns
  // the total score for that game.
  def score() : F[Int]
}

object LiveGame {
  def make[F[_]: Sync]: F[Game[F]] = {
    for {
      ref <- Ref.of[F, GameStatus](emptyGame)
    } yield new LiveGame[F](ref)
  }

}

final class LiveGame[F[_]: Sync] private(ref : Ref[F, GameStatus] )
  extends Game[F] {
  def roll(pins : Int): F[Unit] = ref.update{computeNextStatus(pins)}
  def score() : F[Int] = ref.get.map(computeScore)
}

