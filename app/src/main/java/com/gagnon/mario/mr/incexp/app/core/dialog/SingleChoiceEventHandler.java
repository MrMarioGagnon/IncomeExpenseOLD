package com.gagnon.mario.mr.incexp.app.core.dialog;

public interface SingleChoiceEventHandler {

    SingleChoiceEventHandler NO_OP = new SingleChoiceEventHandler() {
        public void execute(int idSelected) {
        }
    };

    void execute(int idSelected);
}
