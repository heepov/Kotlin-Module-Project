import Text.*

interface Selectable {
    fun updateMenuItems()
    fun displayMenuItems()
    fun getUserInputMenuSelect(): Int
    fun executeSelectedAction(input: Int): Any?
}

abstract class SelectScreenLogic : Selectable {
    var menuTitle: String = ""
    val menuItems = mutableListOf<Pair<String, () -> Any?>>()
    override fun displayMenuItems() {
        updateMenuItems()
        println(DELIMITER.str + menuTitle) // display attractive menu title
        menuItems.forEachIndexed { index, menuItem -> // create and display attractive menu items (numbering starts from 1)
            println("${index + 1}. ${menuItem.first}") // first - it because fist element of pair include menu item name
        }
    }

    override fun getUserInputMenuSelect(): Int { // get and processing user input (only for numbered menu)
        var input: Int
        while (true) {
            print(INPUT_FIELD.str)
            try { // check is input to exist number
                input = readln().trim().toInt()
                if (input > 0 && input <= menuItems.size) return input
                else println(ERRORS_INPUT_DIGIT_MENU.str) // user input out of menu items range
            } catch (e: Exception) {
                println(ERRORS_INPUT_MENU.str) // user input isn't number
            }
            displayMenuItems()
        }
    }

    override fun executeSelectedAction(input: Int): Any? { // processing user choice
        return menuItems.getOrNull(input - 1)?.second?.invoke() // invoke and return lambda function
    }
}

class MainMenuSelectionScreen : SelectScreenLogic() {
    override fun updateMenuItems() { // create menu items for main menu
        menuItems.clear()
        menuItems.add(MAIN_SHUT_DOWN.str to { // exit item
            println(MAIN_LAST_WORD.str)
            return@to 0
        })
        menuItems.add(MAIN_ARCHIVE.str to { return@to 1 })
        menuTitle = MAIN_TITLE.str
    }
}

fun addItemsInMenuItems( // processing Items (Notes or Archive) for add them to menu
    items: List<Items>,
    description: String
): MutableList<Pair<String, () -> Any?>> {
    val menuItems = mutableListOf<Pair<String, () -> Any?>>()
    if (items.isNotEmpty()) {
        for (i in items) {
            menuItems.add("$description ${i.title}" to { return@to i }) // add title (Note or Archive) and lambda
        }
    }
    return menuItems
}

class ArchiveSelectionScreen(private val archiveList: MutableList<Archive>) : SelectScreenLogic() {
    override fun updateMenuItems() { // create menu items for archives menu
        menuItems.clear()
        menuItems.add(ARCHIVE_EXIT.str to { return@to 0 }) // return back item
        menuItems.add(ARCHIVE_CREATE.str to { ArchiveCreationScreen().createArchive(archiveList) }) // new archive create menu item
        menuItems.addAll(
            addItemsInMenuItems(
                archiveList,
                ARCHIVE_SELECT.str
            )
        ) // add all already created archive to menu
        menuTitle = ARCHIVE_TITLE.str
    }
}

class NoteSelectionScreen(private val archive: Archive) : SelectScreenLogic() {
    override fun updateMenuItems() { // create menu items for notes menu
        menuItems.clear()
        menuItems.add(NOTE_EXIT.str to { return@to 0 }) // return back item
        menuItems.add(NOTE_CREATE.str to { NoteCreationScreen().createNote(archive) }) // new note create menu item
        menuItems.addAll(
            addItemsInMenuItems(
                archive.getNoteList(),
                NOTE_SELECT.str
            )
        ) // add all already created notes to menu
        menuTitle = "${NOTE_TITLE.str} ${archive.title}"
    }
}

class NoteInsideScreen(private val note: Note) : SelectScreenLogic() {
    override fun updateMenuItems() { // create menu items for specific note
        menuItems.clear()
        menuItems.add(NOTE_INSIDE_EXIT.str to { return@to 0 }) // return back item
        menuItems.add(NOTE_INSIDE_EDIT.str to { NoteEditScreen().editNote(note) }) // edit note create menu item
        menuItems.add(NOTE_INSIDE_DELETE.str to { return@to NoteRemoveScreen().removeNote(note) }) // remove note create menu item
        menuTitle =
            "${NOTE_INSIDE_NOTE_NAME.str} ${note.title}\n${NOTE_INSIDE_CONTENT.str} ${note.getContent()}"
    }
}