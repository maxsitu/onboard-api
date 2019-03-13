package com.axp.onboardapistream.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.axp.onboardapistream.api.OnboardapiStreamService
import com.axp.onboardapi.api.OnboardapiService
import com.softwaremill.macwire._

class OnboardapiStreamLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new OnboardapiStreamApplication(context) {
      override def serviceLocator: NoServiceLocator.type = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new OnboardapiStreamApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[OnboardapiStreamService])
}

abstract class OnboardapiStreamApplication(context: LagomApplicationContext)
    extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer: LagomServer =
    serverFor[OnboardapiStreamService](wire[OnboardapiStreamServiceImpl])

  // Bind the OnboardapiService client
  lazy val onboardapiService: OnboardapiService = serviceClient.implement[OnboardapiService]
}
