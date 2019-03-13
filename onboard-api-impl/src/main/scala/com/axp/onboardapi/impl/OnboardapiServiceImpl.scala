package com.axp.onboardapi.impl

import com.axp.onboardapi.api
import com.axp.onboardapi.api.OnboardapiService
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.broker.TopicProducer
import com.lightbend.lagom.scaladsl.persistence.{EventStreamElement, PersistentEntityRegistry}

/**
 * Implementation of the OnboardapiService.
 */
class OnboardapiServiceImpl(persistentEntityRegistry: PersistentEntityRegistry)
    extends OnboardapiService {

  override def hello(id: String) = ServiceCall { _ =>
    // Look up the onboard-api entity for the given ID.
    val ref = persistentEntityRegistry.refFor[OnboardapiEntity](id)

    // Ask the entity the Hello command.
    ref.ask(Hello(id))
  }

  override def useGreeting(id: String) = ServiceCall { request =>
    // Look up the onboard-api entity for the given ID.
    val ref = persistentEntityRegistry.refFor[OnboardapiEntity](id)

    // Tell the entity to use the greeting message specified.
    ref.ask(UseGreetingMessage(request.message))
  }

  override def greetingsTopic(): Topic[api.GreetingMessageChanged] =
    TopicProducer.singleStreamWithOffset { fromOffset =>
      persistentEntityRegistry
        .eventStream(OnboardapiEvent.Tag, fromOffset)
        .map(ev => (convertEvent(ev), ev.offset))
    }

  private def convertEvent(
    helloEvent: EventStreamElement[OnboardapiEvent]
  ): api.GreetingMessageChanged = {
    helloEvent.event match {
      case GreetingMessageChanged(msg) => api.GreetingMessageChanged(helloEvent.entityId, msg)
    }
  }
}
