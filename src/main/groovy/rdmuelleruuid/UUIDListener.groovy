package rdmuelleruuid

import grails.events.annotation.gorm.Listener
import org.grails.datastore.mapping.engine.event.ValidationEvent

class UUIDListener {

    @Listener(Widget)
    void preInsertEvent(ValidationEvent event) {
        if (event.entityObject.id == null)
            event.entityAccess.setProperty('id', UUID.randomUUID().toString())
    }
}
