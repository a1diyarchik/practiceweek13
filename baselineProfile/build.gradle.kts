plugins {
    id("com.android.test")
    id("androidx.baselineprofile")
    kotlin("android")
}

android {
    namespace = "com.example.baselineprofile"
    compileSdk = 34


    defaultConfig {
        minSdk = 28
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    targetProjectPath = ":app"


    experimentalProperties["android.experimental.self-instrumenting"] = true
}
kotlin {
    jvmToolchain(17)
}

baselineProfile {
    useConnectedDevices = true
}

dependencies {
    implementation(libs.androidx.junit)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.uiautomator)
    implementation(libs.androidx.benchmark.macro.junit4)
    implementation(kotlin("test"))
}
