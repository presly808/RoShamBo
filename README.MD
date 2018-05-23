# RoShamBo Game 
Artifact Intelligence(Bot) for RoShamBo

#### How to open as an idea project
If you opened project using IDE, in Intellij IDEA just open build.gradle file and choose option 'open as a project'

#### Build
linux: ```./gradlew build``` windows: ```gradlew build```

#### Run after building
```java -jar build/libs/RoShamBo-1.0-SNAPSHOT.jar```

#### Tests
```./gradlew test```

Tests show if user choose his figure occasionally, we will have DRAW in long run perspective

In other cases, if player uses some strategy, our bot can adapt itself and wins 

#### The Algorithm's explanation
Let's assume that a player will choose next step considering prev result. Our figures are  ROCK(0), PAPER(1), SCISSORS(2)

For an instance:

if a player lost last round by his scissors and bot's rock, he probably will choose paper and so on...
In such case, we will keep in mind how user changes his figure considering last round.

To do so, we save shifts (0,1,2) and their weighs in order to know how.
If shift is 1, it means prev figure(ROCK will be shifted to PAPER) Rock(0) + 1 = 1(Paper) etc.

More Examples
* shift 0 ROCK -> ROCK, PAPER -> PAPER, SCISSORS -> SCISSORS
* shift 1 ROCK -> PAPER, PAPER -> SCISSORS, SCISSORS -> ROCK
* shift 2 ROCK -> SCISSORS, PAPER -> ROCK, SCISSORS -> PAPER

we should shift prev decision considering the result of last round

Lifecycle
        
        1) Init weights
            Shifts with weights grouped by result
            WIN     0,0,0
            LOSE    0,0,0
            DRAW    0,0,0
        2) play first round
            User -> Paper, Bot -> SCISSORS Bot Won

        3) play next round
            User -> Rock, Bot -> SCISSORS User Won
            It means that the user has changed from Paper to Rock (shift is 2), because he lost prev round when chose Paper

            We are updating weights
                1) Mult each weight by 0.9, for fast changing bot's algo, if user changes his strategy
                    WIN     0 * 0.9, 0 * 0.9, 0 * 0.9
                    LOSE    0 * 0.9, 0 * 0.9, 0 * 0.9
                    DRAW    0 * 0.9, 0 * 0.9, 0 * 0.9
                2) Add 1 to weights. If we lost prev round, we go to LOSE weights, and our shift is 2
                    WIN     0 , 0 , 0
                    LOSE    0 , 0 , 1
                    DRAW    0 , 0 , 0
        4) now we know if user loses, he probably change his lose figure by shifting(2)
        
#### Possible improvements
I would try ReinforcementLearning and take python for convenient use
