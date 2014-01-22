import grails.plugin.springsecurity.SecurityEventListener

import org.springframework.security.authentication.DefaultAuthenticationEventPublisher

import ca.redtoad.eventlog.SpringSecurityEventLogger

class SpringSecurityEventlogGrailsPlugin {

    def version = "0.4"
    def grailsVersion = "2.0 > *"
    def loadAfter = ['springSecurityCore']
    def title = "Spring Security Event Log"
    def description = 'A plugin to log Spring Security events'
    def documentation = "https://github.com/ataylor284/spring-security-eventlog"
    def license = "APACHE"
    def developers = [
        [name: 'Andrew Taylor', email: 'ataylor@redtoad.ca']
    ]
    def scm = [url: "http://github.com/ataylor284/spring-security-eventlog"]
    def issueManagement = [system: 'GITHUB', url: "http://github.com/ataylor284/spring-security-eventlog/issues" ]

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
