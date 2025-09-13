plugins {
    id("assertk.multiplatform")
    id("assertk.migration")
}

repositories {
    mavenLocal()
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation("org.assertk:assertk:${rootProject.version}")
            }
        }
    }
}

publishing {
    publications.all {
        if (this is MavenPublication) {
            artifactId = forMultiplatform("assertk-coroutines", this, project)
        }
    }
}