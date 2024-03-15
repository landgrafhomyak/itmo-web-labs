plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm{
        jvmToolchain(17)
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }

        withJava()
    }
    js {
        browser()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":modules:utility"))
            }
        }
        val commonTest by getting
        val jvmMain by getting
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting
    }
}
