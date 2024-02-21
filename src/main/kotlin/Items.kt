interface Items { // interface to summarize the output Notes and Archives in a menu
    val title: String
}

class Note(override val title: String, private var content: String) : Items {
    var archive: Archive? = null

    init {
        if (content == "1") content = "Пустая заметка" // if user wants to create empty note
    }

    fun getContent(): String = content // get note's content
    fun setContent(value: String) {
        content = value // change note's content
    }

    fun removeFromArchive() { // remove note from archive without links to archive
        archive?.delNote(title)
        archive = null
    }
}

class Archive(override val title: String) : Items {
    private val noteList = mutableListOf<Note>()
    fun getNoteList(): MutableList<Note> = noteList // get note list
    fun addNote(note: Note) { // add new note to the note list
        noteList.add(note)
        note.archive = this
    }

    fun delNote(noteTitle: String) { // execute remove note from archive without links to archive
        val noteToRemove = noteList.find { it.title == noteTitle }
        noteToRemove?.let {
            noteList.remove(it)
            it.archive = null
        }
    }
}