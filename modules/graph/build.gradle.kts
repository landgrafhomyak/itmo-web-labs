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
        this.browser {  }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api("ru.landgrafhomyak.itmo.web:graph-meta:2.2")
                implementation(project(":modules:utility"))
                implementation(project(":modules:db"))
            }
        }
        val commonTest by getting
        val backendMain by getting
        val backendTest by getting
        val frontendMain by getting
        val frontendTest by getting
    }
}