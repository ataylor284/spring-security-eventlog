package ca.redtoad.eventlog
 
import javax.servlet.http.*
import org.apache.commons.logging.LogFactory
import org.springframework.context.ApplicationListener
import org.springframework.security.authentication.event.AbstractAuthenticationEvent
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutHandler 

class SpringSecurityEventLogger implements ApplicationListener<AbstractAuthenticationEvent>, LogoutHandler {
 
    private static final log = LogFactory.getLog(this)

    void logAuthenticationEvent(String eventName, Authentication authentication) {
        authentication.with {
            try {
                def username = principal.hasProperty('username')?.getProperty(principal) ?: principal
                SpringSecurityEvent.withTransaction {
                    def event = new SpringSecurityEvent(username: username,
                                                        eventName: eventName,
                                                        sessionId: details?.sessionId,
                                                        remoteAddress: details?.remoteAddress)
                    event.save(failOnError:true)
                }
            } catch (RuntimeException e) {
                log.error("error saving spring security event", e)
                throw e
            }
        }
    }

    void onApplicationEvent(AbstractAuthenticationEvent event) {
        logAuthenticationEvent(event.class.simpleName, event.authentication)
    }

    void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logAuthenticationEvent('Logout', authentication)
    }
}
