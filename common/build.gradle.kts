plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm("backend") {
        jvmToolchain(17)
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }
        withJava()
    }
    js("frontend") {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":modules:graph"))
            }
        }
        val commonTest by getting {
        }
        val backendMain by getting {
        }
        val backendTest by getting
        val frontendMain by getting {}
        val frontendTest by getting
    }
}

//
//tasks.named<Copy>("backendProcessResources") {
//    val jsBrowserDistribution = tasks.named("frontendBrowserDistribution")
//    from(jsBrowserDistribution)
//}
