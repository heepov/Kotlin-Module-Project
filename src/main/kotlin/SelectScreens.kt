interface Selectable {
    fun updateMenuItems()
    fun displayMenuItems()
    fun getUserInputMenuSelect(): Int
    fun executeSelectAction(input: Int): Any?
}
abstract class SelectScreenLogic: Selectable {
    var menuTitle:String = ""
    val menuItems = mutableListOf<Pair<String, () -> Any?>>()
    override fun displayMenuItems() {
        updateMenuItems()
        println(Text.DELIMITER.value + menuTitle)
        menuItems.forEachIndexed { index, menuItem ->
            println("${index + 1}. ${menuItem.first}")
        }
    }
    override fun getUserInputMenuSelect():Int {
        var input:Int
        while (true){
            print("> ")
            try {
                input = readln().trim().toInt()
                if(input > 0 && input <= menuItems.size) return input
                else println(Text.ERRORS_INPUT_DIGIT_MENU.value)
            }catch (e:Exception){
                println(Text.ERRORS_INPUT_MENU.value)
            }
            displayMenuItems()
        }
    }
    override fun executeSelectAction(input: Int): Any? {
        return menuItems.getOrNull(input - 1)?.second?.invoke()
    }
}

class MainMenuSelectionScreen: SelectScreenLogic() {
    override fun updateMenuItems() {
        menuItems.clear()
        menuItems.add(Text.MAIN_SHUT_DOWN.value to {
            println(Text.MAIN_LAST_WORD.value)
            return@to 0
        })
        menuItems.add(Text.MAIN_ARCHIVE.value to { return@to 1})
        menuTitle = Text.MAIN_TITLE.value
    }
}
fun addItemsInMenuItems(items:List<Items>, description:String): MutableList<Pair<String, () -> Any?>>{
    val menuItems = mutableListOf<Pair<String, () -> Any?>>()
    if (items.isNotEmpty()) {
        for (i in items) {
            menuItems.add("$description ${i.title}" to { return@to i})
        }
    }
    return menuItems
}
class ArchiveSelectionScreen(private val archiveList:MutableList<Archive>): SelectScreenLogic() {
    override fun updateMenuItems() {
        menuItems.clear()
        menuItems.add(Text.ARCHIVE_EXIT.value to {return@to 0})
        menuItems.add(Text.ARCHIVE_CREATE.value to {
            val archiveCreationScreen = ArchiveCreationScreen()
            archiveCreationScreen.createArchive(archiveList)
            updateMenuItems()
        })
        menuItems.addAll(addItemsInMenuItems(archiveList, Text.ARCHIVE_SELECT.value))
        menuTitle = Text.ARCHIVE_TITLE.value
    }
}
class NoteSelectionScreen(private val archive: Archive): SelectScreenLogic(){
    override fun updateMenuItems() {
        menuItems.clear()
        menuItems.add(Text.NOTE_EXIT.value to { return@to 0})
        menuItems.add(Text.NOTE_CREATE.value to {
            val noteCreationScreen = NoteCreationScreen()
            noteCreationScreen.createNote(archive)
            updateMenuItems()
        })
        menuItems.addAll(addItemsInMenuItems(archive.getNoteList(),Text.NOTE_SELECT.value))
        menuTitle = "${Text.NOTE_TITLE.value} ${archive.title}"
    }
}
class NoteInsideScreen(private val note: Note):SelectScreenLogic(){
    override fun updateMenuItems() {
        menuItems.clear()
        menuItems.add(Text.NOTE_INSIDE_EXIT.value to { return@to 0})
        menuItems.add(Text.NOTE_INSIDE_EDIT.value to {
            val noteEditScreen = NoteEditScreen()
            noteEditScreen.editNote(note)
            updateMenuItems()
        })
        menuItems.add(Text.NOTE_INSIDE_DELETE.value to{
            val noteRemoveScreen = NoteRemoveScreen()
            if(noteRemoveScreen.removeNote(note)) return@to 0
            updateMenuItems()
        })
        menuTitle = "${Text.NOTE_INSIDE_NOTE_NAME.value} ${note.title}\n" +
                "${Text.NOTE_INSIDE_CONTENT.value} ${note.getContent()}"
    }
}