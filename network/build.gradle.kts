plugins {
    id("kotlin")
}


dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libraries.kotlin)
    implementation(Libraries.retrofit)
    implementation(Libraries.moshi)

    implementation (project(path = ":network_interface"))
}

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}
