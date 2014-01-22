package ca.redtoad.eventlog

import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.switchuser.AuthenticationSwitchUserEvent

class SpringSecurityEventLoggerTests {

    def logger = new SpringSecurityEventLogger()

    void testLogAuthenticationEventWithNullAuthentication() {
        logger.logAuthenticationEvent("event", null, "127.0.0.1", null)

        assert SpringSecurityEvent.count() == 1
        def event = SpringSecurityEvent.list().first()
        assert event.username == null
        assert event.sessionId == null
        assert event.eventName == "event"
        assert event.switchedUsername == null
        assert event.remoteAddress == "127.0.0.1"
    }

    void testLogAuthenticationEventWithStringPrincipal() {
        def authentication = new TestingAuthenticationToken("username", [])
        logger.logAuthenticationEvent("event", authentication, "127.0.0.1", null)

        assert SpringSecurityEvent.count() == 1
        def event = SpringSecurityEvent.list().first()
        assert event.username == "username"
        assert event.sessionId == null
        assert event.eventName == "event"
        assert event.switchedUsername == null
        assert event.remoteAddress == "127.0.0.1"
    }

    void testLogAuthenticationEventWithUserDetailsPrincipal() {
        def principal = { -> "username" } as UserDetails
        def authentication = new TestingAuthenticationToken(principal, [])
        logger.logAuthenticationEvent("event", authentication, "127.0.0.1", null)

        assert SpringSecurityEvent.count() == 1
        def event = SpringSecurityEvent.list().first()
        assert event.username == "username"
        assert event.sessionId == null
        assert event.eventName == "event"
        assert event.switchedUsername == null
        assert event.remoteAddress == "127.0.0.1"
    }

    void testLogAuthenticationSwitchUserEvent() {
        def principal = { -> "username" } as UserDetails
        def authentication = new TestingAuthenticationToken(principal, [])
        authentication.details = [remoteAddress: '127.0.0.1', sessionId: 'mockSessionId']
        def targetUser = { -> "switchedUsername" } as UserDetails

        logger.onApplicationEvent(new AuthenticationSwitchUserEvent(authentication, targetUser))

        assert SpringSecurityEvent.count() == 1
        def event = SpringSecurityEvent.list().first()
        assert event.username == "username"
        assert event.sessionId == "mockSessionId"
        assert event.eventName == "AuthenticationSwitchUserEvent"
        assert event.switchedUsername == "switchedUsername"
        assert event.remoteAddress == "127.0.0.1"
    }
}
