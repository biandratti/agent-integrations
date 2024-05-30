import play.api.libs.json.Json
import play.api.libs.json.OFormat

package object models {

  case class TraceResponse(id: String)

  object TraceResponse {
    implicit val TraceResponseFormat: OFormat[TraceResponse] =
      Json.format[TraceResponse]
  }

}
