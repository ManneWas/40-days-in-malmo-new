# language: en

  #DONE
  #- World
  #- Location
  #- Find Item in a location
  #- Pick up item
  #- Lookaround
  #- Storing in backpack
  #- Open backpack
  #- Equip equipment
  #- Fail to equip equipment
  #- Close backpack
  #- Go to another Location
  #- Interaction menu and Event handling
  #- Npc interaction (Approach, Talk, Battle, Trade, Approached by hostile NPC, Loot)
  #- Trade (Buy and Sell)
  #- Faction change
  #- Rewards
  #- Battle-system (Attack, Block, Item, Run, Special attack)
  #- Matrix movement (Go and Look)
  #- Die
  #- End game
  #- Level up
  #- Skill points
  #- Quest
  #- Backpack transfer to new player
  #- Time Counter

  #@todo ¿Faction?

Feature: Full Game

  Scenario: Startup with Empty backpack
    Given the game is on
    And I am at "Start"
    And the game prologue story is told
    And I start with an empty backpack
    When I go to "Malmö C"
    Then I am at "Malmö C"

  Scenario: Find a torch
    Given I am at "Malmö C"
    When  I look around my vicinity
    Then I see a "Torch" item

  Scenario: Pick up Torch
    Given I see a "Torch" item
    And I have space in my backpack
    When I approach the "Torch" item
    And I say "pick up Torch"
    Then the "Torch" item is in my backpack

  Scenario: Open backpack
    Given I am in a "neutral" state
    When I say "open backpack"
    Then I can see my inventory

  Scenario: Equip Torch
    Given I am in "backpack" state
    When I say "select Torch"
    And I say "equip"
    Then I equip "Torch"

  Scenario: Close backpack
    Given I am in "backpack" state
    When I close my "backpack"
    Then I am in "neutral" state

  Scenario: Check time played before travel
    When I say "time"
    Then I can see time played is 0

  Scenario: Go from Malmö C to Möllan
    Given I am in "neutral" state
    And I am at "Malmö C"
    When I say "go to mollan"
    Then I am at "Möllan"

  Scenario: Check time played after travel
    When I say "time"
    Then I can see time played is 1

  Scenario: Leave Player Profile page
    Given I am in "profile" state
    When I say "close"
    Then I return to "neutral" state

  Scenario: Find intractable events
    Given I am in "mollan"
    When I say "look around"
    Then I get a list of all available events

  Scenario: Interact with NPC and display
    Given I see an NPC named "David"
    When I say "approach David"
    Then I approach "David"
    And I get a list of all available events

  Scenario: Start talking with David
    Given I am in a "neutral" state
    When I say "talk"
    Then I am in a "dialogue" state with "David"

  Scenario Outline: Dialogue
    Given the NPC <npc> tells me about <content>
    When  I answer <reply>
    Then The "dialogue" state ends
    And the faction strength between "Player" and <NPC faction> changes with <faction strength>

    Examples: | npc   | content          | reply          | NPC faction | faction strength |
    | David | his wretched day | cool story bro | The Hermits | -15              |

  Scenario: Battle with David
    Given I am in a "neutral" state
    When I say "battle"
    Then the "battle" state starts with me as "instigator" and "David" as "responder"
    And the faction strength between "Player" and <NPC faction> changes with <faction strength>

  Scenario: Attack David
    Given I am in a "battle" state with "David"
    When I choose "Attack"
    Then the resulting damage is calculated
    And Our hp is updated

  Scenario: Block against David
    Given I am in a "battle" state with "David"
    When I choose "Block"
    Then the result is calculated
    And our hp is updated

  Scenario: Kill David
    Given I "Attack" "David"
    When "David" dies
    Then I am rewarded for winning the battle

  Scenario: Gets xp after battle
    Given I am being rewarded for winning a battle
    When I am given xp
    Then my "xp" is raised

  Scenario: Level Up
    Given my "xp" is raised
    When "xp" is higher than "requiredXp"
    Then I level up

  Scenario: Get Skill points
    Given I level up
    When I get a new level
    Then I get 3 new Skill points

  Scenario: Ending battle
    Given I am in "battle" state
    When I got all my rewards for winning the battle
    Then I return to "neutral" state

  Scenario: Loot David event
    Given I just won a battle
    When I am in "neutral" state
    Then I can choose the event "Loot David"

  Scenario: Looting David after battle
    Given I can choose the event "Loot David"
    When I say "Loot"
    Then A list of Davids Loot are being displayed
    And transferred to my backpack

  Scenario: Open Player profile page
    Given I am in "neutral" state
    When I say "Player"
    Then Information about player is displayed

  Scenario: Start spending Skill Points
    Given I am in "profile" state
    When I say "use skillpoints"
    Then A option list of stats is displayed

  Scenario: Allocate Skill Points
    Given I have skill Points to spend
    When I choose 2 (Attack) 3 times
    Then my attack stat is raised

  Scenario: No more Skill Points
    Given I am in "skill points" state
    When I am out of Skill Points
    Then I return to "profile" state

  Scenario: Leave Player Profile page
    Given I am in "profile" state
    When I say "close"
    Then I return to "neutral" state

  Scenario: Leave Möllan
    Given I am in "mollan"
    When I choose to leave
    Then A "list of linked locations" is displayed

  Scenario: Enter pildammsparken"
    Given I am in a "switch location" state
    When I choose "pildammsparken"
    Then my "currentLocation" switches to "pildammsparken"

  Scenario: Go North
    Given I am in "pildammsparken"
    When I go north
    Then I end up 1 tile to the north

  Scenario: Go West
    Given I am in "pildammsparken"
    When I go west
    Then I go 1 tile West

  Scenario: Look North
    Given I am in "pildammsparken"
    When I look to the north
    Then I can see a "Health potion" 1 tile away

  Scenario: Pick up the "Health potion"
    Given I have moved 1 tile north
    When I pick up the "Health potion"
    Then I put the "Health potion" in my "Backpack"

  Scenario: Go East
    Given I am in a "neutral" state
    When I go east 2 tiles
    Then I end up 2 tiles to the east

  Scenario: Look East
    Given I am in a "neutral" state
    When I look to the east
    Then I can see a "Merchant" 1 tile away

  Scenario: Approach the merchant
    Given I see a "Merchant"
    When I say "approach Merchant"
    Then I get a list of all available events

  Scenario: Talk with merchant
    Given I am in "neutral" state
    When I say "Talk"
    Then I enter a dialog with the merchant

  Scenario:
    Given that I have talked to the merchant
    And "Quest" is stated in the "List" of available actions
    When I say "Quest"
    Then The merchant start informing me about a quest

  Scenario: Accept quest from merchant
    Given The merchant has finished explaining the quest
    When I say "accept"
    Then The quest is set as players "nameOfPlayersCurrentQuest"

  Scenario: Trade with Merchant
    Given I am in a "neutral" state
    When I say "Trade"
    Then I enter a "trade" state with the "Merchant"

  Scenario: Enter buy state
    Given I am in a "trade" state
    When I say "Buy"
    Then a "List of available goods" is dispalyed

  Scenario: Buy a modified Fidget-spinner
    Given that the "Fidget-spinner" is in the "List" of available items
    When I pick the "Fidget-spinner"
    Then the "Fidget-spinner" is added to the "Backpack"

  Scenario: Leave buy state
    Given I am in a "buy" state
    When I leave the "buy" state
    Then I go back to "trade" state

  Scenario: Enter sell state
    Given I am in a "trade" state
    When I say "Sell"
    Then a "List" of my available "backpack" items is displayed

  Scenario: Sell your Torch
    Given I see "Torch" in the list
    When I pick "Torch"
    Then "Torch is removed from my "backpack"
    And currency is added to my "backpack"

  Scenario: Leave sell state
    Given I am in "sell" state
    When I leave the "sell" state
    Then I enter "trade" state

  Scenario: Leave trade state
    Given I am in "trade" state
    When I leave "trade" state
    Then I enter "neutral" state

  Scenario: Open backpack
    Given I am in a "neutral" state
    When I say "open backpack"
    Then I can see my inventory

  Scenario: Fail to Equip Fidget Spinner
    Given I dont have the required lvl for the "Fidget Spinner"
    When I say "select Fidget Spinner"
    And I say "equip"
    Then I get a message that I dont meet the requirements to equip the "fidget spinner"

  Scenario: Close backpack
    Given I am in "backpack" state
    When I close my "backpack"
    Then I am in "neutral" state

  Scenario: Go South
    Given I am in a "neutral" state
    When I go south 4 tiles
    Then I end up 4 tiles to the south

  Scenario: Look South
    Given I am in a "neutral" state
    When I look to the south
    Then I can see a "NPC" 1 tile away

  Scenario: Approached by a hostile NPC
    Given I am in a "neutral" state
    When I am next to the hostile "NPC"
    Then the "NPC" approaches me

  Scenario: Getting attacked
    Given I was approached by the "NPC"
    When He gets closer i get to know he is called "Olafo" and wants to fight me
    Then I enter "battle" state as the "responder"
    And the faction strength between "Player" and <NPC faction> changes with <faction strength>

  Scenario: Attack Olafo
    Given I am in a "battle" state with "Olafo"
    When I choose "Attack"
    Then the resulting damage is calculated
    And Our hp is updated

  Scenario: Special attack on Olafo
    Given I have enough endurence points
    When I choose "Special Attack"
    Then the resulting damage is calculated
    And our hp is updated

  Scenario: Failed Special attack on Olafo
    Given I don't have enough endurence points
    When I choose "Special Attack"
    Then I get a message saying "Not enough endurence points"

  Scenario: Display Item in battle
    Given I am in a "battle" state with "Olafo"
    When I choose "Item"
    Then a list of available "Items" are displayed

  Scenario: Pick item in battle
    Given I am viewing my available items
    When I pick the "health potion"
    Then the health potion is selected

  Scenario: Getting health from the potion
    Given I have selected the "health potion"
    When "Olafo" has executed his move
    Then my "hp" is updated with the value from the "potion"

  Scenario: Scenario: Attack Olafo
    Given I am in a "battle" state with "Olafo"
    When I choose "Attack"
    Then the resulting damage is calculated
    And Our hp is updated

  Scenario: Block against Olafo
    Given I am in a "battle" state with "Olafo"
    When I choose "Block"
    Then the result is calculated
    And our hp is updated

  Scenario: Fail to run away
    Given Given I am in a "battle" state with "Olafo"
    When I choose to "Run"
    And the "dice" is rolled
    Then I fail to run

  Scenario: I die
    Given Given I am in a "battle" state with "Olafo"
    When I choose to "Attack"
    And "Olafo" damages more than my health
    Then I die

  Scenario: Ending the game
    Given that I died against "Olafo"
    When the "battle" state ends
    Then the "story" about how I died is remembered
    And my "backpack" passes on to the next player


  Scenario: Startup with a previous players backpack
    Given the previous player lost
    When the game prologue story is told
    Then I start with the previous players backpack
    And I am at "Malmö C"

  Scenario: I quit
    Given I have a menu
    When I quit
    Then the game is saved
    And the game is exited