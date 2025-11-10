import org.gradle.kotlin.dsl.named
import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.mysql:mysql-connector-j")

    implementation(project(":common:snowflake"))
    implementation(project(":common:pagination"))
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}