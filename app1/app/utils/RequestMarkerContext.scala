package utils

import kamon.Kamon
import net.logstash.logback.marker.LogstashMarker
import net.logstash.logback.marker.Markers._
import play.api.MarkerContext
import play.api.mvc.Headers
import utils.ContextId.cId

object RequestMarkerContext extends ContextId {

  def requestHeaderToMarkerContext(requestHeader: Headers): MarkerContext =
    messageToMarkerContext(getCtxId(requestHeader))

  private def messageToMarkerContext(contextId: String): MarkerContext = {
    setKamonContext(cId, contextId)
    val requestMarkers: LogstashMarker = append(cId, contextId)
    MarkerContext(requestMarkers)
  }

  private def setKamonContext(key: String, contextId: String): Unit = {
    Kamon.currentSpan().tag(key, contextId)
  }

}
