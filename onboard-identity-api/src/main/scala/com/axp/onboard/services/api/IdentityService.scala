package com.axp.onboard.services.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}
import com.axp.onboard.services.common.response.GeneratedIdDone
import com.axp.onboard.services.api.response.{TokenRefreshDone, UserLoginDone}
import com.axp.onboard.services.api.request.{UserCreation, UserLogin}

trait IdentityService extends Service {

  import com.lightbend.lagom.scaladsl.api.Descriptor

  def createUser(): ServiceCall[UserCreation, GeneratedIdDone]

  def loginUser(): ServiceCall[UserLogin, UserLoginDone]

  def refreshToken(): ServiceCall[NotUsed, TokenRefreshDone]

  override final def descriptor: Descriptor = {
    import Service._

    named("identity-service")
      .withCalls(
        restCall(Method.POST, "/api/user/login", loginUser _),
        restCall(Method.PUT, "/api/user/token", refreshToken _),
        restCall(Method.POST, "/api/user", createUser _)
      )
      .withAutoAcl(true)

  }
}
