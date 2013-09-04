package ca.redtoad.eventlog

class SpringSecurityEvent {

    String username
    String sessionId
    String eventName
    String remoteAddress
    String switchedUsername
    Date dateCreated

    static constraints = {
        username(nullable: true)
        sessionId(nullable: true)
        eventName()
        remoteAddress(nullable: true)
        switchedUsername(nullable: true)
        dateCreated()
    }

    static mapping = {
        version false
    }
}
