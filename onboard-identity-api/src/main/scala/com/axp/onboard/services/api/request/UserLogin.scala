package com.axp.onboard.services.api.request

import play.api.libs.json.{Format, Json}
import com.wix.accord.dsl._
import com.axp.onboard.services.common.validation.ValidationViolationKeys._

case class UserLogin(
  username: String,
  password: String)

object UserLogin {
  implicit val format: Format[UserLogin] = Json.format
  implicit val userLoginValidation = validator[UserLogin] { u ⇒
    u.username.as(notEmptyKey("username")).is(notEmpty)
    u.password.as(notEmptyKey("password")).is(notEmpty)
  }
}
