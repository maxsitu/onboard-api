package com.axp.onboardapistream.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.axp.onboardapistream.api.OnboardapiStreamService
import com.axp.onboardapi.api.OnboardapiService

import scala.concurrent.Future

/**
 * Implementation of the OnboardapiStreamService.
 */
class OnboardapiStreamServiceImpl(onboardapiService: OnboardapiService)
    extends OnboardapiStreamService {
  def stream = ServiceCall { hellos =>
    Future.successful(hellos.mapAsync(8)(onboardapiService.hello(_).invoke()))
  }
}
