package utils

import net.logstash.logback.marker.LogstashMarker
import net.logstash.logback.marker.Markers.*
import play.api.MarkerContext
import play.api.mvc.Headers
import utils.ContextId.cId

object RequestMarkerContext extends ContextId {

  def requestHeaderToMarkerContext(requestHeader: Headers): MarkerContext =
    messageToMarkerContext(getCtxId(requestHeader))

  def messageToMarkerContext(contextId: String): MarkerContext = {
    val requestMarkers: LogstashMarker = append(cId, contextId)
    MarkerContext(requestMarkers)
  }

}
