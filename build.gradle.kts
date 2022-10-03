import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.moowork.gradle.node.npm.NpmInstallTask
import com.moowork.gradle.node.npm.NpmTask
import com.moowork.gradle.node.task.NodeTask
import com.moowork.gradle.node.yarn.YarnInstallTask
import com.moowork.gradle.node.yarn.YarnTask

plugins {
  java
  application
  id("com.github.johnrengelman.shadow") version "5.2.0"
  id("com.moowork.node") version "1.3.1"
}

repositories {
  mavenCentral()
}

val vertxVersion = "3.9.4"
val junitVersion = "5.3.2"

val mainVerticleName = "io.vertx.starter.MainVerticle"
val watchForChange = "src/**/*.java"
val doOnChange = "${projectDir}/gradlew classes"

dependencies {
  implementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))
  implementation("io.vertx:vertx-web:${vertxVersion}")

  implementation("io.vertx:vertx-core")

  testImplementation("io.vertx:vertx-junit5")
  testImplementation("io.vertx:vertx-web-client")
  testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
}

application {
  mainClassName = "io.vertx.core.Launcher"
}

tasks {

  getByName<JavaExec>("run") {
    args = listOf("run", mainVerticleName, "--redeploy=${watchForChange}", "--launcher-class=${application.mainClassName}", "--on-redeploy=${doOnChange}")
  }

  withType<ShadowJar> {
    classifier = "fat"
    manifest {
      attributes["Main-Verticle"] = mainVerticleName
    }
    mergeServiceFiles()
  }
}

node {
  yarnVersion = ""
  download = false
  nodeModulesDir = File("src/main/frontend")
}

val buildFrontend by tasks.creating(YarnTask::class) {
  setArgs(listOf("build"))
  dependsOn("yarn")
}

val copyToWebRoot by tasks.creating(Copy::class) {
  from("src/main/frontend/dist")
  destinationDir = File("${buildDir}/classes/java/main/webroot")
  dependsOn(buildFrontend)
}

val processResources by tasks.getting(ProcessResources::class) {
  dependsOn(copyToWebRoot)
}