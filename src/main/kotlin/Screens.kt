interface Selectable {
    fun updateMenuItems()
    fun displayMenu()
    fun getUserInputDigit(): Int
    fun executeSelectAction(input: Int): Any?
}

interface Editable {
    fun getUserInputString(instruction: String): String
    fun getUserInputBoolean(instruction: String):Boolean
}


abstract class BaseMenu: Selectable,Editable {
    var menuTitle:String = ""
    val menuItems = mutableListOf<Pair<String, () -> Any?>>()
    override fun displayMenu() {
        updateMenuItems()
        println(Text.DELIMITER.value + menuTitle)
        menuItems.forEachIndexed { index, menuItem ->
            println("${index + 1}. ${menuItem.first}")
        }
    }
    override fun updateMenuItems() {}
    override fun getUserInputDigit():Int {
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
            displayMenu()
        }
    }
    override fun getUserInputBoolean(instruction: String):Boolean {
        var input:String
        while (true){
            println(instruction)
            print("> ")
            try {
                input = readln()
                return when(input.lowercase().trim()){
                    "да" -> true
                    else -> false
                }
            }catch (e:Exception){
                println(Text.ERRORS_INPUT.value)
            }
        }
    }

    override fun getUserInputString(instruction:String):String {
        var input:String
        while (true){
            println(instruction)
            print("> ")
            input = readLine().toString().trim()
            if (input.isNotEmpty()) return input
            else println(Text.ERRORS_INPUT_CREATE.value)
        }
    }

    override fun executeSelectAction(input: Int): Any? {
        return menuItems.getOrNull(input - 1)?.second?.invoke()
    }
}
class MainMenuScreen: BaseMenu() {
    init {
        menuItems.apply {
            menuItems.add(Text.MAIN_SHUT_DOWN.value to {
                println(Text.MAIN_LAST_WORD.value)
                return@to 0
            })
            menuItems.add(Text.MAIN_ARCHIVE.value to { return@to 1})
        }
        menuTitle = Text.MAIN_TITLE.value
    }
}
class ArchiveSelectionScreen(val archiveList:MutableList<Archive>): BaseMenu() {
    override fun updateMenuItems() {
        menuItems.clear()

        menuItems.add(Text.ARCHIVE_EXIT.value to {return@to 0})
        menuItems.add(Text.ARCHIVE_CREATE.value to {
            val archiveCreationScreen = ArchiveCreationScreen()
            archiveCreationScreen.createArchive(archiveList)
            updateMenuItems()
        })

        if (archiveList.isNotEmpty()) {
            for (i in archiveList) {
                menuItems.add("${Text.ARCHIVE_SELECT.value} ${i.title}" to {
                    return@to i
                })
            }
        }
    }
    init {
        updateMenuItems()
        menuTitle = Text.ARCHIVE_TITLE.value
    }
}


class ArchiveCreationScreen:BaseMenu() {
    fun createArchive(archiveList:MutableList<Archive>){
        val input = getUserInputString(Text.ARCHIVE_CREATING.value)
        when(input){
            "0" -> return
            else -> {
                archiveList.add(Archive(input))
                println(Text.ARCHIVE_CREATED.value)
            }
        }
    }
}
class NoteSelectionScreen(val archive: Archive): BaseMenu(){

    override fun updateMenuItems() {
        menuItems.clear()

        menuItems.add(Text.NOTE_EXIT.value to { return@to 0})
        menuItems.add(Text.NOTE_CREATE.value to {
            val noteCreationScreen = NoteCreationScreen()
            noteCreationScreen.createNote(archive)
            updateMenuItems()
        })

        if (archive.getNoteList().isNotEmpty()) {
            for (i in archive.getNoteList()) {
                menuItems.add("${Text.NOTE_SELECT.value} ${i.title}" to {
                    return@to i
                })
            }
        }
    }
    init {
        updateMenuItems()
        menuTitle = "${Text.NOTE_TITLE.value} ${archive.title}"
    }
}

class NoteCreationScreen:BaseMenu() {
    fun createNote(archive: Archive){
        val title = getUserInputString(Text.NOTE_CREATING_NAME.value)
        val note:Note
        when(title){
            "0" -> return
            else -> {
                val content = getUserInputString(Text.NOTE_CREATING_TEXT.value)
                when(content){
                    "0" -> return
                    else -> {
                        note = Note(title, content)
                        archive.addNote(note)
                    }
                }
            }
        }
        println("${Text.NOTE_CREATED.value}\n${note.title}\n${note.getContent()}\n")

    }
}

class NoteInsideScreen(val note: Note):BaseMenu(){
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
    init {
        updateMenuItems()
    }
}
class NoteEditScreen():BaseMenu(){
    fun editNote(note:Note){
        val content = getUserInputString(
            "${Text.NOTE_INSIDE_EDIT_MESSAGE.value}\n"+
                    "${Text.NOTE_INSIDE_NOTE_NAME.value} ${note.title}\n" +
                    "${Text.NOTE_INSIDE_CONTENT.value} ${note.getContent()}")
        when(content){
            "0" -> return
            else -> {
                note.setContent(content)
                println(Text.NOTE_INSIDE_EDITED.value)
            }
        }
    }
}
class NoteRemoveScreen():BaseMenu(){
    fun removeNote(note: Note):Boolean{
        return when(getUserInputBoolean(Text.NOTE_INSIDE_DELETE_MESSAGE.value)){
            true -> {
                note.removeFromArchive()
                println(Text.NOTE_INSIDE_DELETED.value)
                true
            }
            else -> false
        }
    }
}

