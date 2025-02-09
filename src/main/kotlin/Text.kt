// this file stores all main string literals of the program
enum class Text(val str: String) {
    DELIMITER("------------\n"),
    INPUT_FIELD("> "),

    MAIN_WELCOME("Приложение Заметки запущено!"),
    MAIN_TITLE("ГЛАВНОЕ МЕНЮ"),
    MAIN_ARCHIVE("Открыть архив или создать новый"),
    MAIN_SHUT_DOWN("Завершить работу программы."),
    MAIN_LAST_WORD("Спасибо за игру :)"),

    ARCHIVE_TITLE("МЕНЮ АРИХИВЫ"),
    ARCHIVE_SELECT("Открыть архив:"),
    ARCHIVE_CREATE("Создать архив"),
    ARCHIVE_EXIT("Вернуться в главное меню"),
    ARCHIVE_CREATING("Для создания нового архива напишите его название\nДля возврата к выбору архивов введите цифру \"0\""),
    ARCHIVE_CREATED("Архив успешно создан"),

    NOTE_TITLE("МЕНЮ ЗАМЕТКИ || архив:"),
    NOTE_SELECT("Открыть заметку:"),
    NOTE_CREATE("Создать заметку"),
    NOTE_EXIT("Вернуться к выбору архива"),
    NOTE_CREATING_NAME("Для создания новой заметки напишите ее название\nДля возврата к выбору заметок введите цифру \"0\""),
    NOTE_CREATING_TEXT("Напишите текст заметки, для создания пустой заметки отправьте цифру \"1\"\nДля возврата к выбору заметок введите цифру \"0\" (заметка будет удалена)"),
    NOTE_CREATED("Заметка успешно создана"),

    NOTE_INSIDE_NOTE_NAME("ЗАМЕТКА:"),
    NOTE_INSIDE_CONTENT("ТЕКСТ:"),
    NOTE_INSIDE_EDIT("Редактирование текста заметки"),
    NOTE_INSIDE_EDIT_MESSAGE("Редактирования текста заметки\nВведите НОВЫЙ текст заметки, для отмены редактирования отправьте цифру \"0\""),
    NOTE_INSIDE_EDITED("Заметка успешно отредактирована"),
    NOTE_INSIDE_DELETE("Удаление заметки"),
    NOTE_INSIDE_DELETE_MESSAGE("Вы уверены что хотите удалить заметку?\nДля удаления отправьте слово \"да\", для отмены удаления отправьте любую строку"),
    NOTE_INSIDE_DELETED("Заметка успешно удалена"),
    NOTE_INSIDE_EXIT("Вернуться к выбору заметки"),

    ERRORS_INPUT("!!!Ошибка ввода!!!"),
    ERRORS_INPUT_MENU("${ERRORS_INPUT.str}\nВведите ЧИСЛО, соответствующее пункту меню.\n"),
    ERRORS_INPUT_DIGIT_MENU("${ERRORS_INPUT.str}\nНет пункта в меню, соответствующего данному числу.\n"),
    ERRORS_INPUT_CREATE("${ERRORS_INPUT.str}\nВвод должен содержать минимум один символ.\n");
}
