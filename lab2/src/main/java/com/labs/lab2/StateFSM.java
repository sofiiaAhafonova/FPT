package com.labs.lab2;
public class StateFSM extends FSM {
    private MachineState context, q0, q1, q2, q3, q4;

    public StateFSM(){
        this.q0 = new Q0();
        this.q1 = new Q1();
        this.q2 = new Q2();
        this.q3 = new Q3();
        this.q4 = new Q4();
    }
    private MachineState getContext() {
        switch (currentState){
            case q0: context = this.q0; break;
            case q1: context = this.q1; break;
            case q2: context = this.q2; break;
            case q3: context = this.q3; break;
            case q4: context = this.q4; break;
        }
        return context;
    }
    public State nextState(Event event){
        this.context = getContext();
        if (event == Event.PLUS)
            return context.plus();
        if (event == Event.DIGIT)
            return context.digit();
        return context.nonDigit();
    }
}
abstract class MachineState{
    State digit() {
        return State.ERROR;
    }
    State nonDigit(){
        return State.ERROR;
    }
    State plus(){
        return State.ERROR;
    }
}

class Q0 extends MachineState {
    public State plus(){
        return State.q1;
    }
}

class Q1 extends MachineState {
    public State digit(){
        return State.q4;
    }
    public State nonDigit(){
        return State.q3;
    }
    public State plus(){
        return State.q2;
    }
}

class Q2 extends MachineState {
    public State digit(){
        return State.q4;
    }
    public State nonDigit(){
        return State.q3;
    }
    public State plus(){
        return State.q2;
    }
}

class Q3 extends MachineState {
    public State digit(){
        return State.q4;
    }
}

class Q4 extends MachineState {
    public State digit(){
        return State.q4;
    }
    public State plus(){
        return State.q1;
    }
}
