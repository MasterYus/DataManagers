import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.serialization") version "1.5.31"
    application
}

group = "me.yus"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven") }
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.redundent:kotlin-xml-builder:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
    implementation("org.jsoup:jsoup:1.14.3")
    implementation(kotlin("reflect"))

}

tasks.test {
    useJUnit()
}

task<JavaExec>("execute") {
    mainClass.set("Main");
    classpath = java.sourceSets["main"].runtimeClasspath
    val args = args("-c", "-f", "tag")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}