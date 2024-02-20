import Text.*
interface Editable {
    fun getUserInputString(instruction: String): String
    fun getUserInputBoolean(instruction: String):Boolean
}
abstract class EditScreenLogic: Editable {
    private fun printInstruction(instruction: String){
        println(instruction)
        print("> ")
    }
    override fun getUserInputBoolean(instruction: String):Boolean {
        var input:String
        while (true){
            printInstruction(instruction)
            try {
                input = readln()
                return when(input.lowercase().trim()){
                    "да" -> true
                    else -> false
                }
            }catch (e:Exception){
                println(ERRORS_INPUT.txt)
            }
        }
    }
    override fun getUserInputString(instruction:String):String {
        var input:String
        while (true){
            printInstruction(instruction)
            input = readLine().toString().trim()
            if (input.isNotEmpty()) return input
            else println(ERRORS_INPUT_CREATE.txt)
        }
    }
}
class ArchiveCreationScreen:EditScreenLogic() {
    fun createArchive(archiveList:MutableList<Archive>){
        when(val input = getUserInputString(ARCHIVE_CREATING.txt)){
            "0" -> return
            else -> {
                archiveList.add(Archive(input))
                println(ARCHIVE_CREATED.txt)
            }
        }
    }
}


class NoteCreationScreen:EditScreenLogic() {
    fun createNote(archive: Archive){
        val title = getUserInputString(NOTE_CREATING_NAME.txt)
        val note:Note
        when(title){
            "0" -> return
            else -> {
                when(val content = getUserInputString(NOTE_CREATING_TEXT.txt)){
                    "0" -> return
                    else -> {
                        note = Note(title, content)
                        archive.addNote(note)
                    }
                }
            }
        }
        println("${NOTE_CREATED.txt}\n${note.title}\n${note.getContent()}\n")

    }
}

class NoteEditScreen:EditScreenLogic(){
    fun editNote(note:Note){
        val content = getUserInputString("${NOTE_INSIDE_EDIT_MESSAGE.txt}\n"+
                "${NOTE_INSIDE_NOTE_NAME.txt} ${note.title}\n" +
                "${NOTE_INSIDE_CONTENT.txt} ${note.getContent()}")
        when(content){
            "0" -> return
            else -> {
                note.setContent(content)
                println(NOTE_INSIDE_EDITED.txt)
            }
        }
    }
}
class NoteRemoveScreen:EditScreenLogic(){
    fun removeNote(note: Note):Int{
        return when(getUserInputBoolean(NOTE_INSIDE_DELETE_MESSAGE.txt)){
            true -> {
                note.removeFromArchive()
                println(NOTE_INSIDE_DELETED.txt)
                0
            }
            else -> 1
        }
    }
}