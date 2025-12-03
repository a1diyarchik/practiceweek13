package com.example.myapplication

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BaselineProfileTest {

    @get:Rule
    val rule = BaselineProfileRule()

    @Test
    fun generate() = rule.collect(
        packageName = "com.example.myapplication"
    ) {

        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        startActivityAndWait()

        device.wait(
            Until.hasObject(By.pkg(packageName).depth(0)),
            5_000
        )

    }
}
