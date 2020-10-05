package score

import score.Model.{FillBall, Frame, GameStatus, NoShot, OneShot, OpenFrame, Spare, Strike}

import scala.annotation.tailrec

object ScoreLogic {

  def computeNextStatus(pins: Int)(currentStatus: GameStatus): GameStatus = {
    currentStatus match {
      case Right(_) => currentStatus
      case Left((NoShot, l)) if l.length == 10 =>
        if (l.head == Strike) // first fill ball after a strike in the 10th
          Left((OneShot(pins), l))
        else
          Right(FillBall(pins, 0) :: l) // fill ball after a spare in the 10th
      case Left((OneShot(fst), l)) if l.length == 10 =>
        Right(FillBall(fst, pins) :: l) // second fill ball after a strike in the 10th
      case Left((NoShot, l)) =>
        if (pins == 10)
          Left((NoShot, Strike :: l))
        else Left((OneShot(pins), l))

      case Left((OneShot(fst), l)) =>
        if( fst + pins == 10)
          Left((NoShot, Spare(fst) ::l))
        else if(l.length == 9)  // last shot, no fill ball
          Right(OpenFrame(fst, pins):: l)
        else
          Left((NoShot, OpenFrame(fst, pins) :: l))
    }

  }

  def computeScore(game: GameStatus): Int = {

    @tailrec
    def score(game: List[Frame], partial: List[Int], extra: Int): List[Int] = {
      game match {
        case FillBall(a,b)::Strike::rest => score(rest, 10::b::a::partial, extra)
        case FillBall(a,b)::Spare(c)::rest => score(rest, (10-c)::c::b::a::partial, extra)
        case OpenFrame(a,b)::rest => score(rest, b::a::partial, extra)
        case Spare(fst)::rest => score(rest, (10-fst)::fst::partial, extra + partial.headOption.getOrElse(0))
        case Strike::rest => score(rest, 10::partial, extra + partial.take(2).sum)
        case Nil => extra :: partial
        case FillBall(_,_)::_ => List(-1) //should not happen
      }
    }

    game match {
      case Right(l) => score(l, Nil, 0).sum
      case Left((_,l)) => score(l, Nil, 0).sum
    }
  }
}
