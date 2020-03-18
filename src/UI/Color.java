
public enum Color {

    //Color reset
    RESET("\033[0m"),

    //Text
    B_RED("\033[1;31m"),
    B_GREEN("\033[1;32m"),
    B_BLUE("\033[1;34m"),
    B_MAGENTA("\033[1;35m"),

    //Text with background
    BB_RED("\033[1;31;107m"),
    BB_GREEN("\033[1;32;107m"),
    BB_BLUE("\033[1;34;107m"),
    BB_MAGENTA("\033[1;35;107m"),

    //Background
    BACK_BLACK("\033[0;107m");

    private final String code;

    Color(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}