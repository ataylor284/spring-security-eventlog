package ca.redtoad.eventlog

class SpringSecurityEvent {

    String username
    String sessionId
    String eventName
    String remoteAddress
    Date dateCreated

    static constraints = {
        username(nullable: true)
        sessionId(nullable: true)
        eventName()
        remoteAddress()
        dateCreated()
    }

    static mapping = {
        version false
    }
}
