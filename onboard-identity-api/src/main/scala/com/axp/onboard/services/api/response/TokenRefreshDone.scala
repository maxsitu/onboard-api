package com.axp.onboard.services.api.response

import play.api.libs.json.{Format, Json}

case class TokenRefreshDone(authToken: String)

object TokenRefreshDone {
  implicit val format: Format[TokenRefreshDone] = Json.format
}
