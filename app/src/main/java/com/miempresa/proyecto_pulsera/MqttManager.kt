package com.miempresa.proyecto_pulsera


import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

class MqttManager {
    private val serverUri = "http://161.132.41.41:1883"
    private val clientId = "AndroidClient"
    private val topic = "ubicacion"

    private val mqttClient = MqttClient(serverUri, clientId, MemoryPersistence())

    init {
        connect()
        subscribe()
    }

    private fun connect() {
        val options = MqttConnectOptions()
        options.isCleanSession = true
        mqttClient.connect(options)
    }

    private fun subscribe() {
        mqttClient.subscribe(topic) { _, message ->

            val payload = String(message.payload)

        }
    }
}