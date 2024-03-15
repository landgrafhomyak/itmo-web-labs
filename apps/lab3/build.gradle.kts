plugins {
    kotlin("multiplatform")
    war
}

operator fun File.div(child: String): File = this.resolve(child)

val jakartaEeApiVersion: String by project
val jakartaServletApiVersion: String by project
val jakartaJsfVersion: String by project

kotlin {
    jvm("backend") {
        jvmToolchain(17)
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }

        withJava()

        val sourceCompilation = compilations["main"]
        val moduleDirectory = projectDir / "src" / "backendMain"
        val assembledWebAppPath = buildDir / "assembledWebApp"
        val webappDirAssembleTask = tasks.create<Copy>("assembleWebApp") {
            dependsOn(tasks[sourceCompilation.compileAllTaskName])
            from(moduleDirectory / "webapp", project(":modules:ui").projectDir / "src" / "frontendMain" / "static")
            destinationDir = assembledWebAppPath
        }

        tasks.war {
            dependsOn(tasks[sourceCompilation.compileAllTaskName], webappDirAssembleTask)
            classpath = sourceCompilation.output.classesDirs
                .plus(sourceCompilation.runtimeDependencyFiles)
                .plus(files(sourceCompilation.output.resourcesDir))

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
//                implementation("jakarta.platform:jakarta.jakartaee-api:$jakartaEeApiVersion")
//                implementation("jakarta.servlet:jakarta.servlet-api:$jakartaServletApiVersion")
//                implementation("com.sun.faces:jsf-api:$jakartaJsfVersion")
//                implementation("com.sun.faces:jsf-impl:$jakartaJsfVersion")
//                implementation("jakarta.inject:jakarta.inject-api:2.0.1")
//                implementation("jakarta.ws.rs:jakarta.ws.rs-api:3.1.0")
//                implementation("org.primefaces:primefaces:13.0.7:jakarta")
//                implementation("org.glassfish:jakarta.faces:3.0.0")
//

                compileOnly("jakarta.ejb:jakarta.ejb-api:4.0.0")
                compileOnly("jakarta.faces:jakarta.faces-api:3.0.0")
                compileOnly("jakarta.servlet:jakarta.servlet-api:5.0.0")
                implementation("org.hibernate:hibernate-core:6.0.2.Final")
                implementation("org.glassfish.jaxb:jaxb-runtime:3.0.2")
                implementation("com.h2database:h2:2.2.224")

                implementation(project(":modules:db:jpa"))

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