plugins {
    kotlin("multiplatform")
}
val jakartaEeApiVersion: String by project

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
                compileOnly("jakarta.platform:jakarta.jakartaee-api:$jakartaEeApiVersion")
                implementation("jakarta.enterprise:jakarta.enterprise.cdi-api:4.1.0-M1")
            }
        }
        val jvmTest by getting
    }
}
