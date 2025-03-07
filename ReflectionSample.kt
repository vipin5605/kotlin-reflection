package com.example.myapplication

import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

fun main() {

    /**
     * Print member properties and it's values
     * by passing an object
     */
    val userClass : Any = User("sample name", 13)
    printMemberProperties(userClass)


    println("\n#####################\n")


    /**
     * Update values of member properties
     * by passing current object and target values
     */
    val config = Config("localhost", 8080)
    println("config values : host=${config.host}, port=${config.port}")

    val updates = mapOf("host" to "127.0.0.1", "port" to 9090)
    updateConfig(config, updates)
    println("Updated config values: host=${config.host}, port=${config.port}")

}

/**
 * Print member properties and it's values
 * using reflection
 */
fun printMemberProperties(userClass : Any) {

    val kClass  = userClass::class as KClass<Any>
    val properties = kClass.memberProperties
    println("Below are memberProperties of ${kClass.simpleName}")
    properties.forEach { property ->
        println("${property.name} = ${property.get(userClass)}")
    }
}

/**
 * Finds the member property in the object
 * and updates it's values as inputted
 */
fun updateConfig(config: Any, updates: Map<String, Any>) {
    val kClass = config::class
    for (property in kClass.memberProperties) {
        property.isAccessible = true
        val value = updates[property.name]
        if (value != null) {
            (property as? KMutableProperty<Any>)?.setter?.call(config, value)
        }
    }
}

data class User(val name : String, val age : Int)

class Config(var host: String, var port: Int)