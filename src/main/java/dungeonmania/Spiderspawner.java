package dungeonmania;

import dungeonmania.dynamic_entity.Spider;

public class Spiderspawner {
    DungeonManiaController dungeon;
    int spiderAttack;
    int spiderHealth;
    int spiderSpawnRate;
    int spiderTick;
    boolean active;


    public Spiderspawner(DungeonManiaController dungeon, int spiderAttack, int spiderHealth, int spiderSpawnRate) {
        this.dungeon = dungeon;
        this.spiderTick = 0;
        this.spiderAttack = spiderAttack;
        this.spiderHealth = spiderHealth;
        this.spiderSpawnRate = spiderSpawnRate;
        if (spiderSpawnRate == 0) {
            active = false;
        }
        active = true;
        this.spiderTick = 0;
    }

    public void tick() {
        if (active == true) {
            spiderTick += 1;
            if (spiderTick == spiderSpawnRate) {
                spiderTick = 0;
                dungeon.spawnSpider(spiderAttack, spiderHealth);
            }
        }
    }
}
