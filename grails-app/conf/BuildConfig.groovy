grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.dependency.resolution = {
    inherits("global") {
    }
    log "warn"
    repositories {
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenCentral()
        mavenRepo 'http://repo.spring.io/milestone/'
    }

    plugins {
        compile ":hibernate:$grailsVersion", {
            export = false
        }
        compile ':spring-security-core:2.0-RC2'
        build ':release:2.2.1', ':rest-client-builder:1.0.3', {
            export = false
        }
    }
}
