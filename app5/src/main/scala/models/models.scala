import zio.json.DeriveJsonDecoder
import zio.json.DeriveJsonEncoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder

package object models {

  case class TraceResponse(id: String)

  object TraceResponse {
    given encoder: JsonEncoder[TraceResponse] =
      DeriveJsonEncoder.gen[TraceResponse]
    given decoder: JsonDecoder[TraceResponse] =
      DeriveJsonDecoder.gen[TraceResponse]
  }

}
