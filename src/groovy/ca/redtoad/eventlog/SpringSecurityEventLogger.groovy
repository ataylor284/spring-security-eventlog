package ca.redtoad.eventlog
 
import javax.servlet.http.*
import org.apache.commons.logging.LogFactory
import org.springframework.context.ApplicationListener
import org.springframework.security.authentication.event.AbstractAuthenticationEvent
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutHandler 

class SpringSecurityEventLogger implements ApplicationListener<AbstractAuthenticationEvent>, LogoutHandler {
 
    private static final log = LogFactory.getLog(this)

    void onApplicationEvent(AbstractAuthenticationEvent event) {
        event.authentication.with {
            def username = principal.hasProperty('username')?.getProperty(principal) ?: principal
            try {
                SpringSecurityEvent.withTransaction {
                    new SpringSecurityEvent(username: username, eventName: event.class.simpleName, sessionId: details?.sessionId, remoteAddress: details?.remoteAddress).save(failOnError:true)
                }
            } catch (RuntimeException e) {
                log.error("error saving spring security event", e)
                throw e
            }
        }
    }

    void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        authentication.with {
            def username = principal.hasProperty('username')?.getProperty(principal) ?: principal
            try {
                SpringSecurityEvent.withTransaction {
                    new SpringSecurityEvent(username: username, eventName: 'Logout', sessionId: details?.sessionId, remoteAddress: details?.remoteAddress).save(failOnError:true)
                }
            } catch (RuntimeException e) {
                log.error("error saving spring security event", e)
                throw e
            }
        }
    }
}
