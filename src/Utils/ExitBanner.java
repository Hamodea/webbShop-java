package Utils;
import static utils.AnsiColors.*;
public class ExitBanner {

    public static String endArt() {

        return GREEN  + """ 
                 _____                 _   _                   /\\            /\\\s
                |  __ \\               | | | |                 |/\\|          |/\\|
                | |  \\/ ___   ___   __| | | |__  _   _  ___                    \s
                | | __ / _ \\ / _ \\ / _` | | '_ \\| | | |/ _ \\                   \s
                | |_\\ | (_) | (_) | (_| | | |_) | |_| |  __/                   \s
                 \\____/\\___/ \\___/ \\__,_| |_.__/ \\__, |\\___|                   \s
                                                  __/ |             ______     \s
                                                 |___/             |______|    \s
                """ + RESET;
}

}