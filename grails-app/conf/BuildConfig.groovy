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
        compile ':spring-security-core:2.0-RC5'
        build ':release:3.1.1', {
            export = false
        }
    }
}
