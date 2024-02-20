import Text.*

interface Selectable {
    fun updateMenuItems()
    fun displayMenuItems()
    fun getUserInputMenuSelect(): Int
    fun executeSelectAction(input: Int): Any?
}

abstract class SelectScreenLogic : Selectable {
    var menuTitle: String = ""
    val menuItems = mutableListOf<Pair<String, () -> Any?>>()
    override fun displayMenuItems() {
        updateMenuItems()
        println(DELIMITER.txt + menuTitle)
        menuItems.forEachIndexed { index, menuItem ->
            println("${index + 1}. ${menuItem.first}")
        }
    }

    override fun getUserInputMenuSelect(): Int {
        var input: Int
        while (true) {
            print("> ")
            try {
                input = readln().trim().toInt()
                if (input > 0 && input <= menuItems.size) return input
                else println(ERRORS_INPUT_DIGIT_MENU.txt)
            } catch (e: Exception) {
                println(ERRORS_INPUT_MENU.txt)
            }
            displayMenuItems()
        }
    }

    override fun executeSelectAction(input: Int): Any? {
        return menuItems.getOrNull(input - 1)?.second?.invoke()
    }
}

class MainMenuSelectionScreen : SelectScreenLogic() {
    override fun updateMenuItems() {
        menuItems.clear()
        menuItems.add(MAIN_SHUT_DOWN.txt to {
            println(MAIN_LAST_WORD.txt)
            return@to 0
        })
        menuItems.add(MAIN_ARCHIVE.txt to { return@to 1 })
        menuTitle = MAIN_TITLE.txt
    }
}

fun addItemsInMenuItems(
    items: List<Items>,
    description: String
): MutableList<Pair<String, () -> Any?>> {
    val menuItems = mutableListOf<Pair<String, () -> Any?>>()
    if (items.isNotEmpty()) {
        for (i in items) {
            menuItems.add("$description ${i.title}" to { return@to i })
        }
    }
    return menuItems
}

class ArchiveSelectionScreen(private val archiveList: MutableList<Archive>) : SelectScreenLogic() {
    override fun updateMenuItems() {
        menuItems.clear()
        menuItems.add(ARCHIVE_EXIT.txt to { return@to 0 })
        menuItems.add(ARCHIVE_CREATE.txt to { ArchiveCreationScreen().createArchive(archiveList) })
        menuItems.addAll(addItemsInMenuItems(archiveList, ARCHIVE_SELECT.txt))
        menuTitle = ARCHIVE_TITLE.txt
    }
}

class NoteSelectionScreen(private val archive: Archive) : SelectScreenLogic() {
    override fun updateMenuItems() {
        menuItems.clear()
        menuItems.add(NOTE_EXIT.txt to { return@to 0 })
        menuItems.add(NOTE_CREATE.txt to { NoteCreationScreen().createNote(archive) })
        menuItems.addAll(addItemsInMenuItems(archive.getNoteList(), NOTE_SELECT.txt))
        menuTitle = "${NOTE_TITLE.txt} ${archive.title}"
    }
}

class NoteInsideScreen(private val note: Note) : SelectScreenLogic() {
    override fun updateMenuItems() {
        menuItems.clear()
        menuItems.add(NOTE_INSIDE_EXIT.txt to { return@to 0 })
        menuItems.add(NOTE_INSIDE_EDIT.txt to { NoteEditScreen().editNote(note) })
        menuItems.add(NOTE_INSIDE_DELETE.txt to { return@to NoteRemoveScreen().removeNote(note) })
        menuTitle =
            "${NOTE_INSIDE_NOTE_NAME.txt} ${note.title}\n${NOTE_INSIDE_CONTENT.txt} ${note.getContent()}"
    }
}