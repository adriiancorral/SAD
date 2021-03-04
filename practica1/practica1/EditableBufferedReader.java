package practica1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class EditableBufferedReader extends BufferedReader {

    private static final int ENTER = 13;
    private static final int ESC = 27;
    private static final int SPECIAL = 300;
    private static final int LEFT = 'D';
    private static final int RIGHT = 'C';
    private static final int UP = 'A';
    private static final int DOWN = 'B';
    private static final int HOME = 'H';
    private static final int END = 'F';
    private static final int INSERT = '2';
    private static final int DEL = '3';
    private static final int BACKSPACE = '8';

    private Line line;

    public EditableBufferedReader(Reader in) {
        super(in);
        line = new Line();
    }

    public static void setRaw() {
        try {
            String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void unsetRaw() {
        try {
            String[] cmd = {"/bin/sh", "-c", "stty cooked </dev/tty"};
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public int read() throws IOException {
        int character = super.read();
        if (character == ESC) {
            character = super.read();
            character = super.read();
            switch (character) {
                case LEFT:
                    System.out.print((char)27 + "[D");
                    break;
                case RIGHT:
                    System.out.print((char)27 + "[C");
                    break;
                case UP:
                    System.out.print((char)27 + "[A");
                    break;
                case DOWN:
                    System.out.print((char)27 + "[B");
                    break;
                case HOME:
                    System.out.print((char)27 + "[H");
                    break;
                case END:
                    System.out.print((char)27 + "[F");
                    break;
                case INSERT:
                    System.out.print((char)27 + "[2~");
                    break;
                case DEL:
                    System.out.print((char)27 + "[3~");
                    break;
                case BACKSPACE:
                    System.out.print((char)27 + "[8~");
                    break;
                default:
                    System.out.print((char)27 + "[D");
                    break;
            }
            return SPECIAL;
        } else {
            return character;
        }
    }

    @Override
    public String readLine() throws IOException {

        setRaw();

        int character = 0;

        do {
            character = read();
            if (character != SPECIAL) {
                line.addChar((char)character);
            }
        } while(character != ENTER);

        unsetRaw();

        return line.toString();
    }

}
