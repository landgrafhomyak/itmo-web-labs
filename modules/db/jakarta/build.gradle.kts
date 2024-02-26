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
        val commonMain by getting
        val commonTest by getting
        val jvmMain by getting {
            dependencies {
                compileOnly(project(":modules:db"))
                implementation(project(":modules:db:string"))
                compileOnly("jakarta.servlet:jakarta.servlet-api:$jakartaServletApiVersion")
                implementation("jakarta.enterprise:jakarta.enterprise.cdi-api:4.1.0-M1")
            }
        }
        val jvmTest by getting
    }
}
