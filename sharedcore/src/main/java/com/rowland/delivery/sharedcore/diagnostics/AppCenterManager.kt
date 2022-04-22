package com.rowland.delivery.sharedcore.diagnostics

import android.app.Application
import android.util.Log
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.AbstractCrashesListener
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.crashes.ingestion.models.ErrorAttachmentLog
import com.microsoft.appcenter.crashes.model.ErrorReport
import io.sentry.Sentry
import io.sentry.UserFeedback
import io.sentry.protocol.SentryId
import java.util.*
import javax.inject.Inject

class AppCenterManager @Inject constructor() {

    private val automatedCrashName = "Automated-Crash-Reports"
    private val automatedCrashEmail = "rowlandotienoo@outlook.com"

    private val appCenterId: String
        get() = "9cdb1599-6392-4d21-b8b3-b28c5c5d04f4"

    /**
     * Registers App Center to automatically collect user and session data.
     *
     * @param application
     * @param uploadCrash
     */
    fun initialize(application: Application) {
        if (AppCenter.isConfigured()) return

        initializeCrashListener()
        AppCenter.start(application, appCenterId, Analytics::class.java, Crashes::class.java)
    }

    /**
     * Sets the crash listener to upload to PowerLift after a crash. This will happen on next app launch.
     */
    private fun initializeCrashListener() {
        Crashes.setListener(object : AbstractCrashesListener() {
            override fun getErrorAttachments(report: ErrorReport): Iterable<ErrorAttachmentLog> {
                return listOf()
            }

            override fun onBeforeSending(report: ErrorReport) {
                val crashReport = "Crash Report\n" +
                        "crashId: ${report.id}\n" +
                        "appBuild: ${report.device.appBuild}\n" +
                        "appVersion: ${report.device.appVersion}\n" +
                        "locale: ${report.device.locale}\n" +
                        "oemName: ${report.device.oemName}\n" +
                        "osApiLevel: ${report.device.osApiLevel}\n" +
                        "osBuild: ${report.device.osBuild}\n" +
                        "osName: ${report.device.osName}\n" +
                        "osVersion: ${report.device.osVersion}\n" +
                        "screenSize: ${report.device.screenSize}\n" +
                        "timeZoneOffset: ${report.device.timeZoneOffset}\n" +
                        "appStartTime: ${report.appStartTime}\n" +
                        "appErrorTime: ${report.appErrorTime}\n" +
                        "threadName: ${report.threadName}\n" +
                        "stackTrace: ${report.stackTrace}\n"
                Log.e(AppCenterManager::class.java.simpleName, crashReport)

                uploadCrash(crashReport = crashReport)
            }
        })
    }

    /**
     * incidentID is the guid identifier for incident
     */
    fun uploadCrash(
        incidentId: UUID = UUID.randomUUID(), crashReport: String
    ): String {
        Log.i(AppCenterManager::class.java.simpleName, "Sentry incident: $incidentId")

        val userFeedback =
            UserFeedback(SentryId(incidentId), automatedCrashEmail, automatedCrashName, crashReport)
        Sentry.captureUserFeedback(userFeedback)

        return incidentId.toString()
    }
}