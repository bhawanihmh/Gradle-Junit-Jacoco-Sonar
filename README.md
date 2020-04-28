# Convert Plan Java Project To Gradle Java Project #

## Steps to build ##
* __ Copy build.gradle and settings.gradle at roon directory
* __ Run command : "gradle wrapper" : to generate wrapper files for gradle project
* __ Run command : "gradlew build OR gradle build" : to build gradle project
* __ Run command : "gradle task fatJar" : to generate jar file for project

## Content of setting.gradle file ##
rootProject.name = 'javaProjectToGradleProject'

## Sonar for Java 8
https://binaries.sonarsource.com/Distribution/sonarqube/sonarqube-7.8.zip

## Some commands ##
gradle wrapper
gradle build
gradlew test
gradlew clean test --info
gradlew clean sonarqube

## Content of build.gradle file with some details ##
```
/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java project to get you started.
 * For more details take a look at the Java Quickstart chapter in the Gradle
 * User Manual available at https://docs.gradle.org/6.3/userguide/tutorial_java_projects.html
 */

plugins {
    // Apply the java plugin to add support for Java
    id 'java'

    // Apply the java plugin to add support for jacoco 
    id 'jacoco'
    
    //Gradle plugin to help analyzing projects with SonarQube
    id "org.sonarqube" version "2.8"
}

version = '1.0.0'

sourceSets {
    main {
         java {
            srcDirs = ['src']
         }
    }

    test {
        java {
            srcDirs = ['test']
        }
    }
}

repositories {
	// If you want to use a (flat) filesystem directory as a repository
	flatDir {
		dirs 'lib'
		//dirs 'lib1', 'lib2'
	}
}

dependencies {
	implementation fileTree(dir: 'lib', include: '*.jar')
	testImplementation fileTree(dir: 'lib', include: '*.jar')
	testRuntimeOnly fileTree(dir: 'lib', include: '*.jar')
}

jacoco {
    //toolVersion = "0.8.5"
    reportsDir = file("$buildDir/jacoco/adcb")
    //applyTo run
}

jacocoTestReport {
    File jacocoLibDir = file("lib")
	jacocoClasspath = files { jacocoLibDir.listFiles() }
}

/*
jacocoTestCoverageVerification {
	violationRules {
		rule {
			limit {
				counter = 'LINE'
				value = 'COVEREDRATIO'
				minimum = 0.02
			}
		}
	}
}
*/

test {
    useJUnitPlatform()
    jacoco {
    	//append = false
        //destinationFile = file("*/build/jacoco/jacocoTest.exec")
        //classDumpDir = file("*/build/jacoco/classpathdumps")
        destinationFile = file("$buildDir/jacoco/jacocoTest.exec")
        classDumpDir = file("$buildDir/jacoco/classpathdumps")
    }
}

//https://docs.sonarqube.org/latest/analysis/scan/sonarscanner-for-gradle/
sonarqube {
    properties {
    	property "sonar.sources", "src"
    	property "sonar.tests", "test"
    	property "sonar.sourceEncoding", "UTF-8"
    	property 'sonar.projectName', project.name
        property 'sonar.projectKey', "com.adcb.xyz:sonar-jacoco"
        property 'sonar.host.url', 'http://localhost:9000'
        //bb6615b17177fa6122ddf149c99d9d1802b25f93
       	// property "sonar.exclusions", "/Generated.java"       	
    }
}

//create a single Jar with all dependencies
task fatJar(type: Jar) {
	manifest {
        attributes 'Implementation-Title': 'Gradle Jar File Example for ADCB',  
        	//'Implementation-Version': version,
        	'Main-Class': 'com.adcb.xyz.App'
    }
   	archiveBaseName = project.name// + '-' + version    
    into('lib') {
        from 'lib'
    }
    with jar // The important part is with jar. Without it, the classes of this project are not included.
}

test.finalizedBy jacocoTestReport
 //,sonarqube
//check.dependsOn jacocoTestCoverageVerification
//build.finalizedBy sonarqube
```


