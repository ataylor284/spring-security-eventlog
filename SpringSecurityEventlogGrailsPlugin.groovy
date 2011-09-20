import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.plugins.springsecurity.SecurityEventListener
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher
import ca.redtoad.eventlog.SpringSecurityEventLogger

class SpringSecurityEventlogGrailsPlugin {

    def version = "0.1"
    def grailsVersion = "1.3 > *"
    def dependsOn = [springSecurityCore: '1.0 > *']
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

    def doWithSpring = {
        springSecurityEventLogger(SpringSecurityEventLogger)

        // normally these two beans are instantiated only if
        // 'useSecurityEventListener' is true, but they're needed so
        // we override the config
        securityEventListener(SecurityEventListener)
        authenticationEventPublisher(DefaultAuthenticationEventPublisher)

        def logoutHandlerNames = ConfigurationHolder.config.grails.plugins.springsecurity.logout.handlerNames ?: SpringSecurityUtils.LOGOUT_HANDLER_NAMES
        logoutHandlerNames << 'springSecurityEventLogger'
    }
}
