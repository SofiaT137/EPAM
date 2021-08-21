package com.epam.project2.composite;

public class CodeLine extends Composite {

    private String CodeLine;

    public CodeLine(String CodeLine) {
        this.CodeLine = CodeLine;
        this.type = Parts.CODELINE;
    }

    @Override
    public String toString() {
        return CodeLine;
    }
}
