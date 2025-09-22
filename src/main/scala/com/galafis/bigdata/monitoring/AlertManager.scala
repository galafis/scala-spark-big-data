package com.galafis.bigdata.monitoring

/**
 * 🇧🇷 Gerenciador de alertas para eventos críticos de pipeline/infraestrutura
 * 🇺🇸 Alert manager for critical pipeline/infrastructure events
 */
object AlertManager {

  def sendAlert(channel: String, message: String): Unit = {
    println(s"ALERT - Channel: $channel | Message: $message")
    // Pode ser adaptado para Slack, email, Prometheus pushgateway etc.
  }
}
