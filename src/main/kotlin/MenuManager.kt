import java.util.Stack
class MenuManager {
    fun start(){
        val scrnStack = Stack<SelectScreenLogic>()
        val archiveList = mutableListOf<Archive>()
        scrnStack.push(MainMenuSelectionScreen())

        println(Text.MAIN_WELCOME.value)

        while (scrnStack.isNotEmpty()){
            val currentScreen = scrnStack.peek()
            currentScreen.displayMenuItems()
            val userInput = currentScreen.getUserInputMenuSelect()
            val action = currentScreen.executeSelectAction(userInput)

            when(currentScreen){
                is MainMenuSelectionScreen ->{
                    when(action){
                        0 -> scrnStack.pop()
                        1 -> scrnStack.push(ArchiveSelectionScreen(archiveList))
                    }
                }
                is ArchiveSelectionScreen ->{
                    when(action){
                        0 -> scrnStack.pop()
                        is Archive -> scrnStack.push(NoteSelectionScreen(action))
                    }
                }
                is NoteSelectionScreen ->{
                    when(action){
                        0 -> scrnStack.pop()
                        is Note -> scrnStack.push(NoteInsideScreen(action))
                    }
                }
                is NoteInsideScreen ->{
                     if (action == 0) scrnStack.pop()
                }
            }
        }
    }
}
