package com.labs.lab2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.*;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    private static StateFSM machine;
    @BeforeAll
    static void beforeAll(){
        machine = new StateFSM();
    }
    @DisplayName("Test for NON_DIGIT recognizeEvent)")
    @ParameterizedTest
    @ValueSource(chars = {'c', ' ', '.', 'e', '='})
    void recognizeEventNonDigitTest(char ch){
        assertEquals(Event.NON_DIGIT, machine.recognizeEvent(ch));
    }

    @DisplayName("Test for DIGIT recognizeEvent)")
    @ParameterizedTest
    @ValueSource(chars = {'1', '2', '0', '5', '4', '3', '6', '7', '8', '9'})
    void recognizeEventDigitTest(char ch){
        assertEquals(Event.DIGIT, machine.recognizeEvent(ch));
    }

    @DisplayName("Test for PLUS recognizeEvent)")
    @ParameterizedTest
    @ValueSource(chars = {'+'})
    void recognizeEventPlusTest(char ch){
        assertEquals(Event.PLUS, machine.recognizeEvent(ch));
    }

    @DisplayName("Test for not suitable string")
    @ParameterizedTest
    @ValueSource(strings = {"", "+aa", "a", "+a7a", "Hello", "World"})
    void notSuitableStrings(String input){
        assertFalse(machine.scanString(input));
    }

    @DisplayName("Test for suitable string")
    @ParameterizedTest
    @ValueSource(strings = {"+", "++++", "+a", "+++a", "+a7", "+a3333", "+++s324215626"})
    void suitableStrings(String input){
        assertTrue(machine.scanString(input));
    }


    @DisplayName("Test for non suitable string with method source")
    @ParameterizedTest
    @MethodSource("createIncorrectInput")
    void incorrectInputWithMethodSource(String input) {
        assertFalse(machine.scanString(input));
    }

    private static Stream<String> createIncorrectInput() {
        return Stream.of("Hello", "Junit", "", "+aa", "a", "+a7a", "World");
    }

    @DisplayName("Test for suitable string with method source")
    @ParameterizedTest
    @MethodSource("createCorrectInput")
    void correctInputWithMethodSource(String input) {
        assertTrue(machine.scanString(input));
    }
    private static Stream<String> createCorrectInput() {
        return Stream.of("+", "++++", "+a", "+++a", "+a7", "+a3333", "+++s324215626");
    }

    @DisplayName("Test for non suitable string with csv file source")
    @ParameterizedTest
    @CsvFileSource(resources = "incorrectInputTest.csv")
    void invalidInputCsvFileSourceTest(String input){
        assertFalse(machine.scanString(input));
    }
    @DisplayName("Test for suitable string with csv file source")
    @ParameterizedTest
    @CsvFileSource(resources = "correctInputTest.csv")
    void validInputCsvFileSourceTest(String input){
        assertTrue(machine.scanString(input));
    }

    @DisplayName("Test for non suitable string with argument source")
    @ParameterizedTest
    @ArgumentsSource(WrongArgumentsProvider.class)
    void testWithIncorrectArgumentsSource(String input) {
        assertFalse(machine.scanString(input));
    }

    static class WrongArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of("foo", "bar","Hello", "Junit", "", "+aa", "a", "+a7a", "World").map(Arguments::of);
        }
    }

    @DisplayName("Test for suitable string with argument source")
    @ParameterizedTest
    @ArgumentsSource(CorrectArgumentsProvider.class)
    void testWithCorrectArgumentsSource(String input) {
        assertTrue(machine.scanString(input));
    }

    static class CorrectArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of("+", "++++", "+a", "+++a", "+a7", "+a3333", "+++s324215626").map(Arguments::of);
        }
    }
}
