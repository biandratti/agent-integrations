import zio.json.DeriveJsonDecoder
import zio.json.DeriveJsonEncoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder

package object models {

  case class TraceResponse(id: String)

  object TraceResponse {
    implicit val encoder: JsonEncoder[TraceResponse] =
      DeriveJsonEncoder.gen[TraceResponse]
    implicit val decoder: JsonDecoder[TraceResponse] =
      DeriveJsonDecoder.gen[TraceResponse]
  }

}
