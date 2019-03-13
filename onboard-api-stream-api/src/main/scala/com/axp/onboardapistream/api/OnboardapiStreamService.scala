package com.axp.onboardapistream.api

import akka.NotUsed
import akka.stream.scaladsl.Source
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

/**
 * The onboard-api stream interface.
 *
 * This describes everything that Lagom needs to know about how to serve and
 * consume the OnboardapiStream service.
 */
trait OnboardapiStreamService extends Service {

  def stream: ServiceCall[Source[String, NotUsed], Source[String, NotUsed]]

  override final def descriptor: Descriptor = {
    import Service._

    named("onboard-api-stream")
      .withCalls(
        namedCall("stream", stream)
      )
      .withAutoAcl(true)
  }
}
