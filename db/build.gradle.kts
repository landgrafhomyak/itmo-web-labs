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
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
        }
        val jvmMain by getting {
        }
        val jvmTest by getting
    }
}
