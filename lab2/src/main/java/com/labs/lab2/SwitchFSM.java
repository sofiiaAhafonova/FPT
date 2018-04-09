package com.labs.lab2;

public class SwitchFSM extends FSM {

    public State nextState(Event event) {
        switch (this.currentState){
            case q0:
                switch (event){
                    case PLUS:
                        return State.q1;
                    default:
                        return State.ERROR;
                }
            case q1:
            case q2:
                switch (event){
                    case PLUS:
                        return State.q2;
                    case NON_DIGIT:
                        return  State.q3;
                    case DIGIT:
                        return State.q4;
                    default:
                        return State.ERROR;
                }
            case q3:
            case q4:
                switch (event){
                    case DIGIT:
                        return State.q4;
                    default:
                        return State.ERROR;
                }
        }
        return State.ERROR;
    }
}
