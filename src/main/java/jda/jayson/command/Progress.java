package jda.jayson.command;

import com.jayson.nubx.ANSI;

public class Progress {
    private static int high;
    Integer progress = 0;
    public Progress(Integer high) {
        this.high = high;
    }

    private static String getProgress(Integer integer, String objective, Boolean color) {
        StringBuilder str = new StringBuilder(ANSI.WHITE + "[");
        Integer remaining = 0;
        Integer d = (integer / high) * 100;
        for (int i = 0; i < integer; i++) {
            remaining++;
            if(i == (high / 2)) {
                str.append(ANSI.WHITE).append(objective).append(" ").append(d + "").append("%");
            }
            str.append(ANSI.GREEN + "=");
        }
        for (Integer i = remaining; i < high; i++) {
            if(i == (high / 2)) {
                str.append(ANSI.WHITE).append(objective).append(" ").append(d + "").append("%");
            }
            str.append(ANSI.WHITE + "-");
        }
        str.append(ANSI.WHITE + "]" + ANSI.RESET);
        return str.toString();
    }
    private String setProgress(String state, Boolean color) {
        this.progress++;
        return getProgress(progress,state,color);
    }
    public void printProgress(String state, Boolean color) {
        System.out.print(setProgress(state, color) + "\r");
        end();
    }
    public void end() {
        //System.out.println("\n");
        System.out.flush();
    }
}
