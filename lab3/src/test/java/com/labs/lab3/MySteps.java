package com.labs.lab3;


import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MySteps extends Steps {
    private Parser parser;
    private Matrix result;
    private Exception exception;
    @Given ("a new paser")
    public void newParser() {
        parser = new Parser();
    }

    @Given("an parser with a created variable $varname with value result")
    public void givenEvaluatorWithCreatedVariableWithValue(String varname, String result) {
        parser.Parse(varname + " = " + result);
    }

    @Given("a created variable $varname with value result")
    public void givenCreatedVariableWithValue(String varname, String result) {

        parser.Parse(varname + " = " + result);
    }
    @When ("I parse string <input>")
    public void IParseString(@Named("input") String input) {
        try{
            result = parser.Parse(input);
        } catch (Exception e){
            exception = e;
        }
    }
    @When ("I parse string <varname>")
    public void IParseStringVar(@Named("varname") String varname) {
        try{
            result = parser.Parse(varname);
        } catch (Exception e){
            exception = e;
        }
    }

    @Then("I should get <result>")
    public void thenValueShouldBe(@Named("result") String result) {
        assertEquals(result, result.toString());
    }

    @Then("I should get an error message $message")
    public void iShouldGetErrorMessage(String message) {
        assertEquals(message, exception.getMessage());
    }

    @Then("should be created var $varname with value $result")
    public void varCreated(String varname, String result) {
        assertEquals(result, parser.Parse(varname).toString());
    }
}
