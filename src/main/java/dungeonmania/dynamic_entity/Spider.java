package dungeonmania.dynamic_entity;

/**
 * Are among the eneny entities that WILL harm Player. Has following properties:
 *      - Spawn at RANDOM locations ANYWHERE on the map at time of dungeon creation. However, it is RECOMMENDED that we 
 *          use a four co-ordinate box to spawn spiders.
 *      - Immediately on spawn, spiders move one square upwards and then later they "circle" clockwise one square at a time.
 *      - However, if there is a boulder in the spider's movement path, it then reverses the "circling" and then moves in other direction.
 *      - Walls, doors, switches, portals, exits all have no effect on them and hence they can simply traverse through them,
 *          ONLY boulders will effect the movement path and this is noted in previous point.
 */
public class Spider extends DynamicEntity {
    
}
