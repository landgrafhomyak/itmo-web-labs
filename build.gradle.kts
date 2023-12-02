plugins {
    kotlin("multiplatform") version "1.9.0" apply false
}

group = "ru.landgrafhomyak"

allprojects {
    repositories {
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
        maven("https://maven.landgrafhomyak.ru/")
    }
}
