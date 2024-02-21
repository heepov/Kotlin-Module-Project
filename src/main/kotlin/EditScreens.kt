import Text.*

interface Editable {
    fun getUserInputString(instruction: String): String
    fun getUserInputBoolean(instruction: String): Boolean
}

abstract class EditScreenLogic : Editable {
    private fun printInstruction(instruction: String) {
        println(instruction)
        print(INPUT_FIELD.str)
    }

    override fun getUserInputBoolean(instruction: String): Boolean { // get and processing user input for "Yes or No"
        var input: String
        while (true) {
            printInstruction(instruction)
            try {
                input = readln()
                return when (input.lowercase().trim()) {
                    "да" -> true
                    else -> false
                }
            } catch (e: Exception) {
                println(ERRORS_INPUT.str)
            }
        }
    }

    override fun getUserInputString(instruction: String): String { // get and processing user input for multi words strings
        var input: String
        while (true) {
            printInstruction(instruction)
            input = readlnOrNull().toString().trim()
            if (input.isNotEmpty()) return input // processing empty input
            else println(ERRORS_INPUT_CREATE.str)
        }
    }
}

class ArchiveCreationScreen : EditScreenLogic() {
    fun createArchive(archiveList: MutableList<Archive>) { // create new Archive
        when (val input = getUserInputString(ARCHIVE_CREATING.str)) {
            "0" -> return // back to last menu screen
            else -> { // add new archive
                archiveList.add(Archive(input))
                println(ARCHIVE_CREATED.str)
            }
        }
    }
}

class NoteCreationScreen : EditScreenLogic() {
    fun createNote(archive: Archive) { // create new Note
        val title = getUserInputString(NOTE_CREATING_NAME.str)
        val note: Note
        when (title) { // processing note title
            "0" -> return // back to last menu screen
            else -> {
                when (val content =
                    getUserInputString(NOTE_CREATING_TEXT.str)) { // processing note title
                    "0" -> return // back to last menu screen
                    else -> { // add new note
                        note = Note(title, content)
                        archive.addNote(note)
                    }
                }
            }
        }
        println("${NOTE_CREATED.str}\n${note.title}\n${note.getContent()}\n") // display new note

    }
}

class NoteEditScreen : EditScreenLogic() {
    fun editNote(note: Note) { // Edit content already created note
        val content = getUserInputString(
            "${NOTE_INSIDE_EDIT_MESSAGE.str}\n" +
                    "${NOTE_INSIDE_NOTE_NAME.str} ${note.title}\n" +
                    "${NOTE_INSIDE_CONTENT.str} ${note.getContent()}"
        )
        when (content) { // processing NEW note's content
            "0" -> return // back to last menu screen without change note's content
            else -> { // change note's content
                note.setContent(content)
                println(NOTE_INSIDE_EDITED.str)
            }
        }
    }
}

class NoteRemoveScreen : EditScreenLogic() {
    fun removeNote(note: Note): Int { // remove note
        return when (getUserInputBoolean(NOTE_INSIDE_DELETE_MESSAGE.str)) {
            true -> { // delete note and back to "Notes List" screen
                note.removeFromArchive()
                println(NOTE_INSIDE_DELETED.str)
                0  // back to pre last menu screen
            }

            else -> 1 // back to last menu screen
        }
    }
}