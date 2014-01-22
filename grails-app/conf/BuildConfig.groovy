grails.project.work.dir = 'target'

grails.project.dependency.resolution = {

    inherits 'global'
    log 'warn'

    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
        mavenRepo 'http://repo.spring.io/milestone/'
    }

    plugins {
        compile ":hibernate:3.6.10.7", {
            export = false
        }
        compile ':spring-security-core:2.0-RC2'
        build ':release:2.2.1', ':rest-client-builder:1.0.3', {
            export = false
        }
    }
}
