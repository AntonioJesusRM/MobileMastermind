import java.util.Properties

plugins {
    kotlin("kapt")
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hiltPluggin)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.sonarPluggin)
    alias(libs.plugins.pluginSecrets)
    id("jacoco")
}

android {
    namespace = "com.example.mobile_mastermind"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.mobile_mastermind"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.mobile_mastermind.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    val secretsProperties = file("../secrets.properties").takeIf { it.exists() }
        ?.let { Properties().apply { load(it.inputStream()) } }

    secretsProperties?.let {
        project.ext.set("SONAR_SECRET", it.getProperty("SONAR_SECRET"))
    }

    lint {
        xmlReport = true
        xmlOutput = file("build/reports/lint-results-debug.xml")
    }

    sonar {
        properties {
            property("sonar.projectKey", "MobileMastermind")
            property("sonar.projectName", "MobileMastermind")
            property("sonar.host.url", "http://127.0.0.1:9000")
            property("sonar.token", project.findProperty("SONAR_SECRET") ?: "")
            property(
                "sonar.androidLint.reportPaths",
                "${project.projectDir}/build/reports/lint-results-debug.xml"
            )
            property(
                "sonar.coverage.jacoco.xmlReportPaths",
                "${project.projectDir}/build/reports/jacoco/connected/coverage.xml"
            )
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
        debug {
            enableAndroidTestCoverage = true
        }
    }

    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.serializable.kotlinx)
    kapt(libs.hilt.android.compiler)
    kaptAndroidTest(libs.androidx.hilt.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.hilt.work)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestUtil(libs.androidx.orchestrator)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}

tasks.register<JacocoReport>("jacocoConnectedTestReport") {
    dependsOn("connectedDebugAndroidTest")
    group = "Reporting"
    description = "Genera el informe de cobertura de pruebas instrumentadas con Jacoco"

    reports {
        xml.required.set(true)
        xml.outputLocation.set(layout.buildDirectory.file("reports/jacoco/connected/coverage.xml"))
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco/connected/html"))
    }

    val fileFilter = listOf(
        "**/R.class",
        "**/R$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*",
        "**/di/**",
        "**/hilt_*/**"
    )

    val javaClasses = fileTree(layout.buildDirectory.dir("intermediates/javac/debug/classes")) {
        exclude(fileFilter)
    }

    val kotlinClasses = fileTree(layout.buildDirectory.dir("tmp/kotlin-classes/debug")) {
        exclude(fileFilter)
    }

    classDirectories.setFrom(files(javaClasses, kotlinClasses))
    sourceDirectories.setFrom(files("src/main/java", "src/main/kotlin"))

    executionData.setFrom(
        layout.buildDirectory.dir("outputs/code_coverage/debugAndroidTest/connected/Pixel_4_API_33(AVD) - 13")
            .map {
                it.asFileTree.matching {
                    include("**/*.ec")
                }
            }
    )
}

tasks.register("runTestsAndSonar") {
    group = "verification"
    description =
        "Ejecuta los tests de Android, genera el informe de Jacoco y lanza el análisis en SonarQube"

    dependsOn("lintDebug", "connectedDebugAndroidTest", "jacocoConnectedTestReport", "sonar")

    doLast {
        println("Test executed.")
    }
}
