Scenario: Entering and saving a variable
Given a new paser
When I parse string <input>
Then I should get <result>
Then should be created var <varname> with value <result>

Examples:
|input                       |varname|result                      |
|A = [[100]]                 |A      |[[100.0]]                   |
|B = [[0.32, -1.2, 32]]      |B      |[[0.32, -1.2, 32.0]]        |
|C = [[-10.32, 0], [1.2, 32]]|C      |[[-10.32, 0.0], [1.2, 32.0]]|

Scenario: Expression with saving variables
Given a new paser
When I parse string <input>
When I parse string <varname>
Then I should get <result>

Examples:
|input                                  |varname|result                  |
|A = [[100.00]] + [[112.5]] ^T          |A      |[[212.5]]               |
|B = [[2], [-2], [1]] + [[1], [5], [2]] |B      |[[3.0], [3.0], [3.0]]   |
|C = [[1, 2], [2, 1]] + [[1, 2], [2, 1]]|C      |[[2.0, 4.0], [4.0, 2.0]]|


Scenario: Invalid format
Given a new paser
When I parse string <input>
Then I should get <result>

Examples:
|input                               |result|
|100.00 + 112.5A                     |null  |
|([2, -2, 1] + [1, 5, 2 ) * [1, 2, 3]|null  |
|3 + [1, 5, 2) * [1, 2, 3]           |null  |
|[[1, 2], 2, 1]] + [[1, 2], [2, 1]]  |null  |
|det([[[2, 4], [4, 2]])              |null  |

Scenario: Usage of saved variables
When I parse string <input>
Then I should get <result>

Examples:
|input                                                    |result   |
|A = [[2], [-2], [1]] ^T * [[1], [1], [1]]                |[[1]]    |
|B = [[6, -1, -1]] * ([[6], [9], [-3]] + [[4], [-4], [0]])|[[58.0]] |
|C = [[6]] * 10                                           |[[60.0]] |
|A + B                                                    |[[59.0]] |
|A * B                                                    |[[58.0]] |
|A * B + C                                                |[[118.0]]|