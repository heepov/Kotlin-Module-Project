import java.util.Stack
import Text.*

class MenuManager {
    fun start() {
        val screenStack = Stack<SelectScreenLogic>()
        val archiveList = mutableListOf<Archive>()
        screenStack.push(MainMenuSelectionScreen())

        println(MAIN_WELCOME.str)

        while (screenStack.isNotEmpty()) {
            val currentScreen = screenStack.peek() // get current screen
            currentScreen.displayMenuItems()
            val userInput = currentScreen.getUserInputMenuSelect()
            val action = currentScreen.executeSelectedAction(userInput)

            when (currentScreen) { // define current screen
                is MainMenuSelectionScreen -> { // main screen
                    when (action) {
                        0 -> screenStack.pop() // remove main screen from stack
                        1 -> screenStack.push(ArchiveSelectionScreen(archiveList)) // add to stack archives list screen
                    }
                }

                is ArchiveSelectionScreen -> { // archives list screen
                    when (action) {
                        0 -> screenStack.pop() // remove archive screen from stack
                        is Archive -> screenStack.push(NoteSelectionScreen(action)) // add to stack notes list screen
                    }
                }

                is NoteSelectionScreen -> { //notes list screen
                    when (action) {
                        0 -> screenStack.pop() // remove notes screen from stack
                        is Note -> screenStack.push(NoteInsideScreen(action)) // add to stack specific note screen
                    }
                }

                is NoteInsideScreen -> { // specific note screen
                    if (action == 0) screenStack.pop() // remove specific note screen from stack
                }
            }
        }
    }
}
