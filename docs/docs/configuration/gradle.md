---
title: Converting a WebforJ Maven Project to Gradle
sidebar_position: 3
description: Learn how to migrate a WebforJ project from Maven to Gradle using Groovy Domain Specific Language.
---

# Converting a WebforJ maven project to gradle

If you're working with a WebforJ project and looking to switch from Maven to Gradle, this guide walks you through the key changes required to translate a typical `pom.xml` file into a working `build.gradle` script using Groovy Domain Specific Language. The examples are tailored specifically for WebforJ setups, including Jetty integration, WAR packaging, and profile-based configuration.

## 1. Apply required plugins

WebforJ typically packages the app as a WAR file and runs using Jetty. Enable the following plugins:

```groovy
plugins {
    id 'java'
    id 'war'
    id 'org.gretty'
}
```

## 2. Define project metadata

These properties match the `<groupId>`, `<artifactId>`, and `<version>` entries from Maven:

```groovy
group = 'org.example'
version = '1.0-SNAPSHOT'
```

## 3. Java version compatibility

```groovy
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
```

## 4. Define repositories

Include Maven Central and the Sonatype snapshots repository:

```groovy
repositories {
    mavenCentral()
    maven {
        url 'https://s01.oss.sonatype.org/content/repositories/snapshots'
        mavenContent { snapshotsOnly() }
    }
}
```

## 5. Declare dependencies

Replace the `<dependencies>` block from your `pom.xml` with:

```groovy
def webforjVersion = '25.00-SNAPSHOT'

dependencies {
    implementation "com.webforj:webforj:${webforjVersion}"
    implementation 'org.slf4j:slf4j-simple:2.0.16'
    testImplementation 'com.microsoft.playwright:playwright:1.49.0'
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.10.3'
}
```

## 6. Resource and WAR Handling

You can copy config files as part of your `processResources` phase:

```groovy
def configFile = project.hasProperty('prod') ? 'webforj-prod.conf' : 'webforj-dev.conf'

tasks.register('copyWebforjConfig', Copy) {
    from "src/main/resources/${configFile}"
    into "$buildDir/resources/main"
    rename { 'webforj.conf' }
}

processResources.dependsOn copyWebforjConfig
```

## 7. Gretty integration

Configure your gretty setup, its going to handle the jetty deployment:

```groovy
gretty {
    httpPort = 8080
    contextPath = '/'
    servletContainer = 'jetty11'
}
```

## 8. Testing

To enable JUnit 5 (Jupiter), include:

```groovy
test {
    useJUnitPlatform()
}
```

## 9. IDE Integration

### IntelliJ IDEA
- Open the project root folder.
- IntelliJ will detect `build.gradle` and prompt to import.
- Ensure the "Use Gradle from wrapper" option is enabled.

### Eclipse
- Install the Buildship Gradle plugin if not already installed.
- Go to File → Import → Gradle → Existing Gradle Project.
- Select your project directory and finish.
<!-- vale off -->
### VS Code
<!-- vale on -->
- Install the Java Extension Pack.
- Open the folder, and VS Code will detect the Gradle project.
- Use the Gradle Tasks tab or command palette to build or run tasks.


## 10. Running the app

Since you are using gretty you will need to build a gradle wrapper first. For this purpose run `gradle wrapper`.
Now you can use `./gradlew appRun` and the task will utilize the jetty deployment automatically.

The completed `build.gradle` should look similar to this:

```groovy
plugins {
    id 'java'
    id 'war'
    id 'org.gretty' version '4.1.1'
}

group = 'com.gradletest'
version = '1.0-SNAPSHOT'

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

gretty {
    httpPort = 8080
    contextPath = '/'
    servletContainer = 'jetty11'
}

def webforjVersion = '25.00'

repositories {
    mavenCentral()
    maven {
        url 'https://s01.oss.sonatype.org/content/repositories/snapshots'
        mavenContent { snapshotsOnly() }
    }
}

dependencies {
    implementation "com.webforj:webforj:$webforjVersion"
    implementation 'org.slf4j:slf4j-simple:2.0.16'

    testImplementation 'com.microsoft.playwright:playwright:1.49.0'
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.10.3'
}

test {
    useJUnitPlatform()
}

def configFile = project.hasProperty('prod') ? 'webforj-prod.conf' : 'webforj-dev.conf'

tasks.register('copyWebforjConfig', Copy) {
    from "src/main/resources/${configFile}"
    into "$buildDir/resources/main"
    rename { 'webforj.conf' }
}

processResources.dependsOn 'copyWebforjConfig'

``` 
---


