plugins {
    kotlin("multiplatform")
    war
}

operator fun File.div(child: String): File = this.resolve(child)

val jakartaServletApiVersion: String by project

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

        val sourceCompilation = compilations["main"]
        val moduleDirectory = projectDir / "src" / "backendMain"
        val assembledWebAppPath = buildDir / "assembledWebApp"
        val webappDirAssembleTask = tasks.create<Copy>("assembleWebApp") {
            dependsOn(tasks[sourceCompilation.compileAllTaskName])
            from( moduleDirectory / "webapp", project(":modules:ui").projectDir / "src" / "frontendMain" / "static")
            destinationDir = assembledWebAppPath
        }

        tasks.war {


            dependsOn(tasks[sourceCompilation.compileAllTaskName], webappDirAssembleTask)
            classpath = sourceCompilation.output.classesDirs + sourceCompilation.runtimeDependencyFiles
            group = "build"

            webAppDirectory = assembledWebAppPath

            webXml = moduleDirectory / "WEB-INF" / "web.xml"
            webInf {
                from(moduleDirectory / "WEB-INF" / "beans.xml")
            }

            // archiveVersion.set("v${project.version}")
            destinationDirectory = projectDir / "out"
        }
    }
    js("frontend") {
        binaries.executable()
        browser {
            commonWebpackConfig {
                outputFileName = "form.js"
                outputPath = buildDir / "compiledK2JS"
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":modules:db"))
                implementation(project(":modules:graph"))
                implementation(project(":modules:form"))
                implementation(project(":modules:utility"))
            }
        }
        val commonTest by getting
        val backendMain by getting {
            dependencies {
                compileOnly("jakarta.servlet:jakarta.servlet-api:$jakartaServletApiVersion")
                implementation("jakarta.inject:jakarta.inject-api:2.0.1")
                implementation("jakarta.ws.rs:jakarta.ws.rs-api:3.1.0")
                implementation(project(":modules:db:jakarta"))
                compileOnly("jakarta.servlet.jsp:jakarta.servlet.jsp-api:4.0.0-M1")
            }
        }
        val backendTest by getting
        val frontendMain by getting
        val frontendTest by getting
    }
}

//
//tasks.named<Copy>("backendProcessResources") {
//    val jsBrowserDistribution = tasks.named("frontendBrowserDistribution")
//    from(jsBrowserDistribution)
//}

tasks.war {
    dependsOn(tasks["frontendBrowserWebpack"])
    from(buildDir / "compiledK2JS")
}