package com.example.sampleclarityinitialise

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.microsoft.clarity.Clarity
import com.microsoft.clarity.ClarityConfig
import com.microsoft.clarity.models.LogLevel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

val scope = CoroutineScope(SupervisorJob())

class ClarityInitializer : Initializer<Unit> {

    // Add project id
    private val projectId = ""

    override fun create(context: Context) {
        // We have an App wide coroutine scope, which is essentially a scope with SupervisorJob
        scope.launch(Dispatchers.Main) {
            val config = withContext(Dispatchers.IO) {
                // Simulating a data store config read
                delay(50)
                ClarityConfig(
                    projectId,
                    logLevel = LogLevel.Verbose
//                enableWebViewCapture = false
                )
            }
            Clarity.initialize(context, config)

            Log.d("ClarityInit", "initialise called")

            // SDK VERSION 2.5.2:
            // The callback got triggered a few times, not reliable though
//            delay(5000)
//            Clarity.setOnNewSessionStartedCallback {
//                Log.i("ClarityInit","setOnNewSessionStartedCallback ${Clarity.getCurrentSessionUrl()}")
//                setCustomAttributes()
//                Clarity.getCurrentSessionUrl()?.let { url ->
//                    // Do something with url
//                }
//            }

            // SDK VERSION 3.0.0
            // Here too, setOnSessionStartedCallback works after some delay
            // This does not work, if we do GlobalScope.launch(Dispatchers.Main)
//            delay(5000)
//            Clarity.setOnSessionStartedCallback {
//                Log.i("ClarityInit","setOnSessionStartedCallback ${Clarity.getCurrentSessionUrl()}")
//                setCustomAttributes()
//                Clarity.getCurrentSessionUrl()?.let { url ->
//                    // Do something with url
//                }
//            }

            // SDK VERSION 3.0.0
            // This works on Normal GlobalScope.launch, does not work with Dispatchers.Main
            Clarity.setOnSessionStartedCallback {
                Log.i(
                    "ClarityInit",
                    "setOnSessionStartedCallback ${Clarity.getCurrentSessionUrl()}"
                )
                setCustomAttributes()

                Clarity.getCurrentSessionUrl()?.let { url ->
                    // Do something with url
                }
            }

            // SDK VERSION 3.0.0
//            Clarity.startNewSession { url ->
//                Log.i("ClarityInit","startNewSession $url")
//                setCustomAttributes()
//                Clarity.getCurrentSessionUrl()?.let { url ->
//                    // Do something with url
//                }
//            }
        }
    }

    private fun setCustomAttributes() {
        Clarity.setCustomUserId("4c19ba83-7fc0-4fba-a6df-560013e8df9e")
        Clarity.setCustomTag("IS_PROP_1", "TRUE")
        Clarity.setCustomTag("IS_PROP_2", "FALSE")
        Clarity.setCustomTag("IS_PROP_1", "TRUE")
        Clarity.setCustomTag("IS_PROP_2", "FALSE")
        Clarity.setCustomTag("IS_PROP_1", "TRUE")
        Clarity.setCustomTag("IS_PROP_2", "FALSE")
        Clarity.setCustomTag("IS_PROP_1", "TRUE")
        Clarity.setCustomTag("IS_PROP_2", "FALSE")
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf(
        WorkManagerInitializer::class.java
    )
}