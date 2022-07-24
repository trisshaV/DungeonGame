package dungeonmania;

import java.io.Serializable;

public class Spiderspawner implements Serializable {
    private DungeonManiaController dungeon;
    private int spiderAttack;
    private int spiderHealth;
    private int spiderSpawnRate;
    private int spiderTick;
    private boolean active;


    /**
     * Spiderspawner Constructor
     * @param dungeon
     * @param spiderAttack
     * @param spiderHealth
     * @param spiderSpawnRate
     */
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

    /**
     * Tick for spiders
     */
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
