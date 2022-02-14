package rdmuelleruuid

class Widget {
    String id
    String name
    static mapping = {
        id generator:'assigned'
    }
}
