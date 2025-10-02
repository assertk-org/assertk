import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication

// Copyright (C) 2018 Vanniktech - Niklas Baudy
//Licensed under the Apache License, Version 2.0
fun forMultiplatform(artifactId: String, publication: MavenPublication, project: Project): String {
    val projectName = project.name
    return if (publication.artifactId == projectName) {
        artifactId
    } else if (publication.artifactId.startsWith("$projectName-")) {
        // Publications for specific platform targets use derived artifact ids (e.g. library, library-jvm,
        // library-js) and the suffix needs to be preserved
        publication.artifactId.replace("$projectName-", "$artifactId-")
    } else {
        throw IllegalStateException(
            "The plugin can't handle the publication ${publication.name} artifactId " +
                    "${publication.artifactId} in project $projectName",
        )
    }
}
