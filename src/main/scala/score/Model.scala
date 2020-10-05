package score

object Model {

  sealed abstract class Frame

  case class OpenFrame(fst: Int, snd: Int) extends Frame
  case class Spare(fst: Int) extends Frame
  case object Strike extends Frame
  case class FillBall(fst: Int, snd:Int) extends Frame

  sealed abstract class UnfinishedFrame
  case object NoShot extends UnfinishedFrame
  case class OneShot(i : Int) extends UnfinishedFrame
  val emptyGame = Left((NoShot, Nil))

  type OngoingGame = (UnfinishedFrame, List[Frame])
  type FinishedGame = List[Frame]
  type GameStatus = Either[OngoingGame, FinishedGame]
}
