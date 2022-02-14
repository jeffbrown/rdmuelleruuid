package rdmuelleruuid

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class WidgetController {

    WidgetService widgetService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond widgetService.list(params), model:[widgetCount: widgetService.count()]
    }

    def show(String id) {
        respond widgetService.get(id)
    }

    def create() {
        respond new Widget(params)
    }

    def save(Widget widget) {
        if (widget == null) {
            notFound()
            return
        }

        try {
            widgetService.save(widget)
        } catch (ValidationException e) {
            respond widget.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'widget.label', default: 'Widget'), widget.id])
                redirect widget
            }
            '*' { respond widget, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond widgetService.get(id)
    }

    def update(Widget widget) {
        if (widget == null) {
            notFound()
            return
        }

        try {
            widgetService.save(widget)
        } catch (ValidationException e) {
            respond widget.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'widget.label', default: 'Widget'), widget.id])
                redirect widget
            }
            '*'{ respond widget, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        widgetService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'widget.label', default: 'Widget'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'widget.label', default: 'Widget'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
