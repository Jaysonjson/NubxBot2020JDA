package jda.jayson.guilds.nullbloxme.commands.fun.TicTacToe;

import java.util.ArrayList;

public class TTTContainer {
    public ArrayList<TTTField> fields = new ArrayList<>();
    Integer field_line;
    public TTTContainer(Integer field_line) {
        this.field_line = field_line;
    }
    public TTTField addField(TTTField field) {
        field.index = fields.size();
        fields.add(field);
        return field;
    }
    public TTTField getField(Integer index) {
        return this.fields.get(index);
    }
    public String Fields() {
        String field = "";
        Integer c = 0;
        for (int i = 0; i < fields.size(); i++) {
            c++;
            String content = getField(i).getContent().toString();
            content = content.replace("0", "0⃣ ");
            content = content.replace("1", "1⃣ ");
            content = content.replace("2", "2⃣ ");
            content = content.replace("3", "3⃣ ");
            content = content.replace("4", "4⃣ ");
            content = content.replace("5", "5⃣ ");
            content = content.replace("6", "6⃣ ");
            content = content.replace("7", "7⃣ ");
            content = content.replace("8", "8⃣ ");
            content = content.replace("9", "9⃣ ");
            content = content.replace("X","❌");
            content = content.replace("O","⭕");
            if(c == field_line) {
                c = 0;
                //field += "|"+ content + "| \n";
                field += ""+ content + "\n";
            } else {
                //field += "|"+ content + "| ";
                field += ""+ content + "";
            }
        }
        return field;
    }
    public static class FieldManagement {
        TTTContainer container;
        public FieldManagement(TTTContainer container) {
            this.container = container;
        }
        public TTTField getFirstField() {
            return this.container.fields.get(0);
        }
        public TTTField getLastField() {
            return this.container.fields.get(this.container.fields.size() - 1);
        }
        public TTTField getMidField() {
            return this.container.fields.get(this.container.fields.size() / 2);
        }
        public TTTField getLineFieldLast(Integer line) {
            return this.container.fields.get(this.container.field_line * line - 1);
        }
    }
}
