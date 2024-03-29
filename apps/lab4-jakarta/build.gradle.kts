plugins {
    kotlin("multiplatform")
    war
}

operator fun File.div(child: String): File = this.resolve(child)

val jakartaServletApiVersion: String by project
val jakartaJspApiVersion: String by project

kotlin {
    jvm("backend") {
        jvmToolchain(17)
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }

        withJava()

        tasks.war {
            val sourceCompilation = compilations["main"]
            dependsOn(tasks[sourceCompilation.compileAllTaskName])
            classpath = sourceCompilation.output.classesDirs
                .plus(sourceCompilation.runtimeDependencyFiles)
                .plus(files(sourceCompilation.output.resourcesDir))
            group = "build"

            val moduleDirectory = projectDir / "src" / "backendMain"
            webAppDirectory = moduleDirectory / "webapp"

            from(project(":modules:ui").projectDir / "src" / "frontendMain" / "static")

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
                compileOnly("jakarta.servlet.jsp:jakarta.servlet.jsp-api:$jakartaJspApiVersion")
                implementation(project(":modules:db:jakarta"))
            }
        }
        val backendTest by getting
        val frontendMain by getting
        val frontendTest by getting
    }
}


tasks.war {
    dependsOn(tasks["frontendBrowserWebpack"])
    from(buildDir / "compiledK2JS")
}