package com.labs.lab2;

enum State {
    q0, q1, q2, q3, q4, ERROR
}

enum Event {
    DIGIT, PLUS, NON_DIGIT
}


 abstract class FSM {
    State currentState;
    private boolean isFinalState(State state) {
        return (state == State.q1 || state == State.q2 ||  state == State.q3 || state == State.q4);
    }
     Event recognizeEvent(char ch) {
        if (ch == '+')
            return Event.PLUS;
        else if (Character.isDigit(ch))
            return Event.DIGIT;
        return Event.NON_DIGIT;
    }

    boolean scanString(String input) {
        currentState = State.q0;
        Event event;
        for (int i = 0; i < input.length(); i++) {
            event = recognizeEvent(input.charAt(i));
            currentState = nextState(event);
            if (currentState == State.ERROR)
                return false;
        }
        return isFinalState(currentState);
    }

    abstract State nextState(Event event);
}


