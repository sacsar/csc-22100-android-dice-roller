// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    val kotlin_version = "1.6.21"

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.+")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.42")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.4.2")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

fun readGitIgnore(): List<String>  {
    val gitignore = project.projectDir.resolve(".gitignore")
    if (gitignore.exists()) {
       return gitignore.readLines().filterNot { line ->
            line.startsWith("#") || line.isBlank()
        }
    }
    return listOf()
}

tasks.register<Zip>("packageProject") {
    archiveFileName.set("${project.name}.zip")
    destinationDirectory.set(project.projectDir)

    from(project.layout.projectDirectory) {
        include("**/*.gradle.kts")
        include("*.gradle")
        include("gradle/**")
        include("gradlew*")
        include("**/src/**")
        include("proguard-rules.pro")
        include("gradle.properties")

        exclude("**/build")

        // additionally, exclude whatever was in .gitignore
        readGitIgnore().forEach{ ex ->
            exclude(ex)
        }

        into(project.name)
    }

    outputs.upToDateWhen{false}
}