import play.api.libs.json.Json
import play.api.libs.json.OFormat

package object models {

  case class TraceResponse(id: String)

  object TraceResponse {
    given TraceResponseFormat: OFormat[TraceResponse] =
      Json.format[TraceResponse]
  }

}
