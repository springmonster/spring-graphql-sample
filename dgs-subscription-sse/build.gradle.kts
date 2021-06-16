import com.netflix.graphql.dgs.codegen.gradle.GenerateJavaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.1"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.10"
	kotlin("plugin.spring") version "1.5.10"
	id("com.netflix.dgs.codegen") version "4.6.6" //https://plugins.gradle.org/plugin/com.netflix.dgs.codegen
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_16

repositories {
	mavenCentral()
}

dependencies {
	implementation(platform("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:latest.release"))
	implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter") {
		exclude("org.yaml", "snakeyaml")
	}
	implementation("com.netflix.graphql.dgs:graphql-dgs-subscriptions-sse-autoconfigure") {
		exclude("org.yaml", "snakeyaml")
	}
	implementation("org.yaml:snakeyaml:1.28")

	//Spring and kotlin
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	// test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<GenerateJavaTask> {
	schemaPaths =
		mutableListOf("${projectDir}/src/main/resources/schema") // List of directories containing schema files
	packageName = "com.example.demo.gql" // The package name to use to generate sources
	generateClient = true // Enable generating the type safe query API
	shortProjectionNames = false
	maxProjectionDepth = 2
	snakeCaseConstantNames = true
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "16"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}