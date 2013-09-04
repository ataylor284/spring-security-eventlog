package ca.redtoad.eventlog

import javax.servlet.http.*
import org.apache.commons.logging.LogFactory
import org.springframework.context.ApplicationListener
import org.springframework.security.authentication.event.AbstractAuthenticationEvent
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutHandler 
import org.springframework.security.web.authentication.switchuser.AuthenticationSwitchUserEvent

class SpringSecurityEventLogger implements ApplicationListener<AbstractAuthenticationEvent>, LogoutHandler {
 
    private static final log = LogFactory.getLog(this)

    void logAuthenticationEvent(String eventName, Authentication authentication, String remoteAddress, String switchedUsername) {
        try {
            def username = authentication?.principal?.hasProperty('username')?.getProperty(authentication?.principal) ?: authentication?.principal
            def sessionId = authentication?.details?.sessionId
            SpringSecurityEvent.withTransaction {
                def event = new SpringSecurityEvent(username: username,
                                                    eventName: eventName,
                                                    sessionId: sessionId,
                                                    remoteAddress: remoteAddress,
                                                    switchedUsername: switchedUsername)
                event.save(failOnError:true)
            }
        } catch (RuntimeException e) {
            log.error("error saving spring security event", e)
            throw e
        }
    }

    void onApplicationEvent(AbstractAuthenticationEvent event) {
        logAuthenticationEvent(event.class.simpleName, event.authentication, event.authentication?.details?.remoteAddress, switchedUsername(event))
    }

    void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logAuthenticationEvent('Logout', authentication, request.remoteHost, null)
    }

    private static String switchedUsername(AbstractAuthenticationEvent event) {
        if (event instanceof AuthenticationSwitchUserEvent) {
            event.targetUser.username
        } else {
            null
        }
    }

}
