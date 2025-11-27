//gradle.project
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // Hilt plugin (возможно, тоже есть в catalog — если нет, оставляем так)
    id("com.google.dagger.hilt.android") version "2.48" apply false
}
