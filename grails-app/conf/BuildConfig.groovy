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
    }

    plugins {
        compile ":hibernate:$grailsVersion"
        build ":tomcat:$grailsVersion"
        compile ":spring-security-core:1.2.7.3" 
        compile ":release:2.2.0"
    }
}
