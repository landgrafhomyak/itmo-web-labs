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
        browser {}
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
            }
        }
        val commonTest by getting
        val backendMain by getting {
            dependencies {
                compileOnly("jakarta.servlet:jakarta.servlet-api:$jakartaServletApiVersion")
            }
        }
        val backendTest by getting
        val frontendMain by getting
        val frontendTest by getting
    }
}