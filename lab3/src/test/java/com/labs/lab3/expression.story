Scenario: Expression without saving
Given a new paser
When I parse string <input>
Then I should get <result>

Examples:
|input                                         |result                  |
|[[100.00]] + [[112.5]]                        |[[212.5]]               |
|([[2, -2, 1]] + [[1, 5, 2]]) * [[1], [2], [3]]|[[18.0]]                |
|[[1, 5, 2]] * [[1], [2], [3]]                 |[[2.0]]                 |
|[[1, 2], [2, 1]] + [[1, 2], [2, 1]]           |[[2.0, 4.0], [4.0, 2.0]]|
|[[2, 4], [4, 2]] ^T                           |[[2.0, 4.0], [4.0, 2.0]]|

Scenario: Expression with saving
Given a new paser
When I parse string <input>
When I parse string <varname>
Then I should get <result>

Examples:
|input                                  |varname|result                  |
|A = [[100.00]] + [[112.5]] ^T          |A      |[[212.5]]               |
|B = [[2], [-2], [1]] + [[1], [5], [2]] |B      |[[3.0], [3.0], [3.0]]   |
|C = [[1, 2], [2, 1]] + [[1, 2], [2, 1]]|C      |[[2.0, 4.0], [4.0, 2.0]]|


Scenario: Incorret matrix format
Given a new paser
When I parse string <input>
Then I should get <result>

Examples:
|input                                       |result|
|([[2, -2,]] + [[1, 5, 2]]) * [[1], [2], [3]]|null  |
|[[1, 5, 2]] * [[1], [2, 4], [3]]            |null  |
|[[1, 2],[2, 1]] + [[1, 2], [2, 1]]          |null  |
|[[2, 4], [4 2]] ^T                          |null  |

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

Scenario: Usage of unexisting variables
When I parse string <input>
Then I should get <result>

Examples:
|input                                                    |result|
|[[2], [-2], [1]] ^T * D                                  |null  |
|B * [[6, -1, -1]] * ([[6], [9], [-3]] + [[4], [-4], [0]])|null  |
|C + [[6]] * 10                                           |null  |
|A + B                                                    |null  |
|A * B                                                    |null  |
|A * B + C                                                |null  |

Scenario: Expression with existing variables and saving variables
When I parse string <input>
When I parse string <varname>
Then I should get <result>

Examples:
|input                                                    |varname|result   |
|A = [[2], [-2], [1]] ^T * [[1], [1], [1]]                |A      |[[1]]    |
|B = [[6, -1, -1]] * ([[6], [9], [-3]] + [[4], [-4], [0]])|B      |[[58.0]] |
|C = A + B                                                |C      |[[59.0]] |
|B = C + [[-15.0]]                                        |B      |[[44.0]] |
|C = (B + A) * [[10]]                                     |C      |[[450.0]]|