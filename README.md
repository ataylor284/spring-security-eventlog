Spring Security Eventlog Plugin
===============================


This plugin creates a simple log of spring security events.  Each time
a user logs in or logs out, a log entry will created, storing the
remote address, session id, user name, event name, switched user name,
and the time at which the event occurred.

Events are logged to a table named SPRING_SECURITY_EVENT, mapped to a
domain object ca.redtoad.eventlog.SpringSecurityEvent.

Each event has the following fields:

* username - the username entered
* sessionId - the user's session
* eventName - the name of the event
* remoteAddress - the user's IP address
* switchedUsername - username that is being switched to
* dateCreated - the event's timestamp

Some of the event names that are captured:

* AuthenticationFailureBadCredentialsEvent - a bad username or password
* AuthenticationSuccessEvent - a successful login
* InteractiveAuthenticationSuccessEvent - a successful login where the user entered his/her username and password
* AuthenticationSwitchUserEvent - user having ROLE_SWITCH_USER has assumed the identity of a (likely different) user
* Logout - a user logged out interactively


Customizing
-----------

You can specify your own logger if you would like to override how
events get logged.  Create a subclass of SpringSecurityEventLogger and
add your custom behavior to logAuthenticationEvent.  For example:

    package mypackage
    
    import ca.redtoad.eventlog.SpringSecurityEventLogger
    import org.springframework.security.core.Authentication
    
    class CustomEventLogger extends SpringSecurityEventLogger {
        void logAuthenticationEvent(String eventName, Authentication authentication, String remoteAddress) {
            println "$eventName! $authentication from $remoteAddress"
        }
    }

In your `Config.groovy`, tell grails to your own event logger class:

    grails.plugins.springsecurity.eventlog.eventLogger = mypackage.CustomEventLogger


Changelog
---------

* Changes in spring-security-eventlog 0.3
   * added logging of 'switched user name' when a AuthenticationSwitchUserEvent occurs
   * small cleanup of plugin definition

* Changes in spring-security-eventlog 0.2

    * fix exception on anonymous logout
    * changed logAuthenticationEvent signature to take remoteAddress
      directly rather than pulling it out of authentication.details
