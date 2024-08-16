plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "pl_api"
include("src:test:resources")
findProject(":src:test:resources")?.name = "resources"
