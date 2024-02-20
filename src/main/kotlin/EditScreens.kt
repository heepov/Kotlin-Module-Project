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
                println(Text.ERRORS_INPUT.value)
            }
        }
    }
    override fun getUserInputString(instruction:String):String {
        var input:String
        while (true){
            printInstruction(instruction)
            input = readLine().toString().trim()
            if (input.isNotEmpty()) return input
            else println(Text.ERRORS_INPUT_CREATE.value)
        }
    }
}
class ArchiveCreationScreen:EditScreenLogic() {
    fun createArchive(archiveList:MutableList<Archive>){
        when(val input = getUserInputString(Text.ARCHIVE_CREATING.value)){
            "0" -> return
            else -> {
                archiveList.add(Archive(input))
                println(Text.ARCHIVE_CREATED.value)
            }
        }
    }
}


class NoteCreationScreen:EditScreenLogic() {
    fun createNote(archive: Archive){
        val title = getUserInputString(Text.NOTE_CREATING_NAME.value)
        val note:Note
        when(title){
            "0" -> return
            else -> {
                when(val content = getUserInputString(Text.NOTE_CREATING_TEXT.value)){
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

class NoteEditScreen:EditScreenLogic(){
    fun editNote(note:Note){
        val content = getUserInputString("${Text.NOTE_INSIDE_EDIT_MESSAGE.value}\n"+
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
class NoteRemoveScreen:EditScreenLogic(){
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