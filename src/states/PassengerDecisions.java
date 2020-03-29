package states;

/**
 * Contains the typical Passenger Decisions for use in the WhatShouldIDo function.
 * <ul>
 *     <li>COLLECT_A_BAG - The passenger takes this state when his journey is ending and he has bags to collect.</li>
 *     <li>GO_HOME - When the passenger journey is ending and he has no bags, he goes home.</li>
 *     <li>TAKE_A_BUS - If his journey continues, the passenger goes to take a bus.</li>
 * </ul>
 */
public enum PassengerDecisions {

    /**
     * The passenger takes this state when his journey is ending and he has bags to collect.
     */
    COLLECT_A_BAG,
    /**
     * When the passenger journey is ending and he has no bags, he goes home.
     */
    GO_HOME,
    /**
     * If his journey continues, the passenger goes to take a bus.
     */
    TAKE_A_BUS
}
