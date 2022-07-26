# Assumptions

---

## Player
- The inventory of the Player remains after the Player dying
- The Player’s health is always greater than 0 (If not, the player will be removed from the list of entities)

## Static Entities
- Exit destroyed by bomb or a switch destroyed by a bomb in a dungeon with exit goal or active goal it is removed.
- When target destination for portal is blocked it will look for another open cardinally adjacent destination
- Boulders can be pushed o to collectibles
- Zombies can move onto portals without any effect
- When a boulder is on top of an enemy or portal it is counted as just a boulder.
- Using a weapon on a zombie spawner will not affect its durability.
- Zombies and player cannot go through zombie spawner
- Zombies and mercenary can’t push boulders
- Bribing a mercenary doesn’t count as defeating an enemy
- Player will stay on top of portal if cannot travel
- When boulder is pushed onto spider tile counts as a boulder
- When bribe radius is 0 cannot be bribed
- If a bomb explodes the portal, the portal still functions from one end only
- When spawning zombie from zombie spawner, do not initiate combat with player even if player is there

## Dynamic Entities
- Two or more dynamic entities can exist on the same tile (eg, if two spiders move into the same tile)
- Zombies and Mercenaries can not move boulders
- Zombies will always try to move in a random direction (testing all 4 cardinal directions). Should no directions be valid (blocked by wall, etc), the Zombie will remain stationary
- Mercenaries should not move towards the player when running away, even though the only valid position is towards them
- Mercenaries will move away from the player with an invincibility potion active in the player’s new portal position rather than the portal position the player walked into
- Mercenaries are able to move onto portals but have no effect
- Zombies are able to move onto portals but have no effect
- Mercenaries do not move if there is no path to the player.

## Buildable
- The materials used when building an item is undefined/no specific order
- Buildable items used after battle which have broken (durability == 0) should be removed from Inventory
- You can only build one bow or shield at a time (cannot build multiple bows all at once)
- If a itemUsedId is undefined for building, the tick for entities moving (aside from player) is still executed

## Battle
- Order of battle has no specific order (is undefined)
- Multiple copies of the same weapon can be used at the same time (2 swords)
- However, the durability of all weapons used should be updated
- Mercenaries run away from players who have consumed an Invincibility potion on the same tick
- Having an invincibility potion during battle will not decrease the durability of weapons used
- The only weapon used is the invincibility potion itself
- If the defence buff is greater than the enemy’s attack, the player will heal (can be thought of as absorption of damage into health)

## Bombs
- Bombs cannot explode other bombs
- Bombs become an obstacle and cannot be picked up again after being placed
- Bombs will destroy every entity but the player
- Destroying enemies with bombs will not contribute to the enemy goal
- If there are multiple active bombs around an active switch the order in which they explode is undefined/no specific order
