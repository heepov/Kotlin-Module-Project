import java.util.Stack
import Text.*
class MenuManager {
    fun start(){
        val screenStack = Stack<SelectScreenLogic>()
        val archiveList = mutableListOf<Archive>()
        screenStack.push(MainMenuSelectionScreen())

        println(MAIN_WELCOME.txt)

        while (screenStack.isNotEmpty()){
            val currentScreen = screenStack.peek()
            currentScreen.displayMenuItems()
            val userInput = currentScreen.getUserInputMenuSelect()
            val action = currentScreen.executeSelectAction(userInput)

            when(currentScreen){
                is MainMenuSelectionScreen ->{
                    when(action){
                        0 -> screenStack.pop()
                        1 -> screenStack.push(ArchiveSelectionScreen(archiveList))
                    }
                }
                is ArchiveSelectionScreen ->{
                    when(action){
                        0 -> screenStack.pop()
                        is Archive -> screenStack.push(NoteSelectionScreen(action))
                    }
                }
                is NoteSelectionScreen ->{
                    when(action){
                        0 -> screenStack.pop()
                        is Note -> screenStack.push(NoteInsideScreen(action))
                    }
                }
                is NoteInsideScreen ->{
                     if (action == 0) screenStack.pop()
                }
            }
        }
    }
}
