package com.axp.onboard.services.identity.impl

import com.axp.onboard.services.common.response.GeneratedIdDone
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType

sealed trait IdentityCommand

case class CreateUser(
  firstName: String,
  lastName: String,
  email: String,
  username: String,
  password: String)
    extends ReplyType[GeneratedIdDone]
    with IdentityCommand
