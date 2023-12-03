plugins {
    kotlin("multiplatform")
}
val jakartaServletApiVersion: String by project

kotlin {
    jvm {
        jvmToolchain(17)
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }

        withJava()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":common"))
                compileOnly(project(":db"))
            }
        }
        val commonTest by getting
        val jvmMain by getting
        val jvmTest by getting
    }
}
