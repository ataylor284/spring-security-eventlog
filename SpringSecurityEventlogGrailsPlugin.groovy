import grails.plugin.springsecurity.SecurityEventListener

import org.springframework.security.authentication.DefaultAuthenticationEventPublisher

import ca.redtoad.eventlog.SpringSecurityEventLogger

class SpringSecurityEventlogGrailsPlugin {

    def version = "0.3"
    def grailsVersion = "2.0 > *"
    def loadAfter = ['springSecurityCore']
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def author = "Andrew Taylor"
    def authorEmail = "ataylor@redtoad.ca"
    def title = "Spring Security Event Log"
    def description = '''\\
        |A plugin to log spring security events.
    '''.stripMargin()

    def documentation = "https://github.com/ataylor284/spring-security-eventlog"

    def license = "APACHE"
    def scm = [ url: "http://github.com/ataylor284/spring-security-eventlog" ]

    def doWithSpring = {
        def eventLoggerClass = application.config.grails.plugin.springsecurity.eventlog.eventLogger ?: SpringSecurityEventLogger
        springSecurityEventLogger(eventLoggerClass)

        // normally these two beans are instantiated only if
        // 'useSecurityEventListener' is true, but they're needed so
        // we override the config
        securityEventListener(SecurityEventListener)
        authenticationEventPublisher(DefaultAuthenticationEventPublisher)
    }

    def doWithApplicationContext = { ctx ->
        ctx.logoutHandlers << ctx.springSecurityEventLogger
    }
}
