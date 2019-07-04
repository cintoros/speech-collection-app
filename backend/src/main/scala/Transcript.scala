import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

case class Transcript(id: Int, file: Array[Byte]) {
}

object Transcript {
  implicit val encoder = deriveEncoder[Transcript]
  implicit val decoder = deriveDecoder[Transcript]
}
