plugins {
    id("application")
    kotlin("jvm") version "1.8.21"
}

group = "dev.github.gabrielmartins"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("org.jfree:jfreechart:1.5.3")

    implementation("org.openjdk.nashorn:nashorn-core:15.4")
}

application {
    mainClass.set("dev.github.gabrielmartins.Main")
}
