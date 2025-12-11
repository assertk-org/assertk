plugins {
    `maven-publish`
    signing
}

group = "com.willowtreeapps.assertk"
version = rootProject.version

repositories {
    mavenCentral()
}

val emptyJavadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

publishing {
    publications.all {
        if (this is MavenPublication) {
            artifact(emptyJavadocJar)

            val siteUrl = "https://github.com/assertk-org/assertk"
            val gitUrl = "https://github.com/assertk-org/assertk.git"

            pom {
                name.set(project.name)
                description.set("Assertions for Kotlin inspired by assertj")
                url.set(siteUrl)

                scm {
                    url.set(siteUrl)
                    connection.set(gitUrl)
                    developerConnection.set(gitUrl)
                }

                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("repo")
                    }
                }

                developers {
                    developer {
                        id.set("evant")
                        name.set("Eva Tatarka")
                    }
                }
            }
        }
    }
}

signing {
    setRequired {
        findProperty("signing.keyId") != null
    }
    publishing.publications.all { sign(this) }
}

// TODO: remove after https://youtrack.jetbrains.com/issue/KT-46466 is fixed
project.tasks.withType(AbstractPublishToMaven::class.java).configureEach {
    dependsOn(project.tasks.withType(Sign::class.java))
}