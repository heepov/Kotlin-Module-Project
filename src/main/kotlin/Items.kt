interface Items {
    val title:String
}
class Note(override val title: String, private var content: String): Items {
    var archive: Archive? = null
    init {
        if (content == "1") content = "Пустая заметка"
    }
    fun getContent(): String = content
    fun setContent(value: String) { content = value }
    fun removeFromArchive() {
        archive?.delNote(title)
        archive = null
    }
}
class Archive(override val title:String): Items {
    private val noteList = mutableListOf<Note>()

    fun getNoteList(): MutableList<Note> = noteList

    fun addNote(note: Note) {
        noteList.add(note)
        note.archive = this
    }

    fun delNote(noteTitle: String) {
        val noteToRemove = noteList.find { it.title == noteTitle }
        noteToRemove?.let {
            noteList.remove(it)
            it.archive = null
        }
    }
}