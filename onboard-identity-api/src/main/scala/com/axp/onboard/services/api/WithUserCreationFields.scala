package com.axp.onboard.services.api

trait WithUserCreationFields {
  val firstName: String
  val lastName: String
  val email: String
  val username: String
  val password: String
}