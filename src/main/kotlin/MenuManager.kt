import java.util.Stack
class MenuManager {
    fun start(){
        val scrnStack = Stack<BaseMenu>()
        val archiveList = mutableListOf<Archive>()
        scrnStack.push(MainMenuScreen())

        println(Text.MAIN_WELCOME.value)

        while (scrnStack.isNotEmpty()){
            val currentScreen = scrnStack.peek()
            currentScreen.displayMenu()
            val userInput = currentScreen.getUserInputDigit()
            val action = currentScreen.executeSelectAction(userInput)

            when(currentScreen){
                is MainMenuScreen ->{
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
