package com.galafis.bigdata.streaming

import org.apache.spark.sql.streaming.{GroupState, GroupStateTimeout}
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.OutputMode

/**
 * 🇧🇷 Gerenciamento de estado em streaming para detectar sessões e padrões
 * 🇺🇸 State management in streaming to detect sessions and patterns
 */
object StateManagement {

  case class SessionState(userId: String, sessionId: String, startTime: Long, lastEventTime: Long, eventCount: Int)
  case class Event(userId: String, sessionId: String, timestamp: Long)

  def updateSessionState(
      key: String,
      events: Iterator[Event],
      state: GroupState[SessionState]
    ): SessionState = {
    val prev = state.getOption.getOrElse(SessionState(key, "", 0L, 0L, 0))
    val latest = events.maxBy(_.timestamp)
    val newState = prev.copy(
      sessionId = latest.sessionId,
      lastEventTime = latest.timestamp,
      eventCount = prev.eventCount + events.size
    )
    state.update(newState)
    state.setTimeoutDuration("30 minutes")
    newState
  }
}
