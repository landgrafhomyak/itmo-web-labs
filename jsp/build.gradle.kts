plugins {
    kotlin("multiplatform")
    war
}

operator fun File.div(child: String): File = this.resolve(child)

configurations.forEach { println(it) }


kotlin {
    jvm("backend") {
        jvmToolchain(17)
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }

        withJava()

        /*tasks.register<War>("war") {
            val sourceCompilation = compilations["main"]
            dependsOn(tasks[sourceCompilation.compileAllTaskName])
            classpath = sourceCompilation.output.classesDirs + sourceCompilation.runtimeDependencyFiles
            group = "build"
            webAppDirectory = projectDir / "src" / "backendMain" / "webapp"
            webXml = projectDir / "src" / "backendMain" / "resources" / "WEB-INF" / "web.xml"


            // archiveVersion.set("v${project.version}")
            destinationDirectory = projectDir.resolve("out")
            idea.project.settings.ideArtifacts {
                this.register("war") {
                    this.archive()
                }
            }
        }*/


        tasks.war {
            val sourceCompilation = compilations["main"]
            dependsOn(tasks[sourceCompilation.compileAllTaskName])
            classpath = sourceCompilation.output.classesDirs + sourceCompilation.runtimeDependencyFiles
            group = "build"

            val moduleDirectory = projectDir / "src" / "backendMain"
            webAppDirectory =  moduleDirectory / "webapp"
            webXml =moduleDirectory / "WEB-INF" / "web.xml"
            webInf {
                from(moduleDirectory / "WEB-INF" / "beans.xml")
            }

            // archiveVersion.set("v${project.version}")
            destinationDirectory = projectDir.resolve("out")
        }
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
        val commonMain by getting
        val commonTest by getting {
        }
        val backendMain by getting {
            dependencies {
                compileOnly("jakarta.servlet:jakarta.servlet-api:6.0.0")
                implementation("jakarta.inject:jakarta.inject-api:2.0.1")
            }
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
