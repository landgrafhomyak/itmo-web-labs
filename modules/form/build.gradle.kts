plugins {
    kotlin("multiplatform")
}

operator fun File.div(child: String): File = this.resolve(child)

val jakartaServletApiVersion: String by project

kotlin {
    jvm("backend") {
        jvmToolchain(17)
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }
    }
    js("frontend") {
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":modules:utility"))
            }
        }
        val commonTest by getting
        val backendMain by getting
        val backendTest by getting
        val frontendMain by getting
        val frontendTest by getting
    }
}