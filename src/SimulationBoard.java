import javax.swing.*;
import java.awt.*;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Random;

public class SimulationBoard extends JPanel {

    public static int tileWorldWidth = 40;
    public static int tileWorldHeight = 24;
    public static int tileSize = 25;
    public static Tile[][] tileMap = new Tile[tileWorldHeight][tileWorldWidth];

    public static int[][] simulationGrid = new int[tileWorldHeight][tileWorldWidth];

    public static ArrayList<Organism> organisms = new ArrayList<>();

    public static int generationCounter = 0;
    public static int roundPeriod = 1000;
    public static int movementVelocity = 1;
    public static boolean paused = true;

    public static int generateRabbitNum = 18;
    public static int generateWolfNum = 2;

    public static int initialRabbitSpeed = 1;
    public static int initialRabbitSensoryRadius = 4;
    public static int initialWolfSpeed = 1;
    public static int initialWolfSensoryRadius = 4;

    public static int mutationRate = 70;
    public static int mutationSpeedCap = 4;
    public static int mutationSensoryCap = 10;

    public static int hungerReductionPerRound = 6;
    public static int maxPlantNum = 35;

    public static Random random = new Random();

    public SimulationBoard(){

        generateTileArray();


    }

    public static void generateTileArray(){
        //Fills an 2D array of Tile objects so that it creates an map from an single repeating tileImage
        for(int y = 0; y < tileMap.length; y ++ ){
            for(int x = 0; x < tileMap[0].length ; x ++){
                tileMap[y][x] = new Tile(x*tileSize,y*tileSize);
            }
        }
    }


    public static void  generateGrid(int rabbitNum, int wolfNum, int[][] grid){

        int rabbitCount = 0;
        int wolfCount = 0;
        int plantCount = 0;

        for(int y = 0; y < grid.length; y ++ ){
            for(int x = 0; x < grid[0].length ; x ++){
                if(wolfCount < wolfNum) {
                    grid[y][x] = 3;
                    wolfCount++;
                }
                else if(rabbitCount < rabbitNum){
                    grid[y][x] = 2;
                    rabbitCount++;
                }
                else if(plantCount < rabbitNum) {
                    //We are generating the same number of plants as rabbits, so rabbits have enough food
                    grid[y][x] = 1;
                    plantCount++;
                }
                else
                    grid[y][x] = 0;
            }
        }
        shuffle(grid);
    }


    public static void shuffle(int[][] array) {
        // Fisher–Yates algorithm
        for (int i = 0; i < array.length ; i ++) {
            for (int j = 0; j < array[i].length; j ++) {
                int m = (int)(Math.random() * array.length);
                int n = (int)(Math.random() * array[i].length);

                int temp = array[i][j];
                array[i][j] = array[m][n];
                array[m][n] = temp;
            }
        }
    }


    public static void generateOrganismArrayAccordingToGird(int[][] grid, ArrayList<Organism> organisms){
        // Resets and then populates the Organism ArrayList according to 2D array grid
        organisms.clear();
        for(int y = 0; y < grid.length; y ++ ){
            for(int x = 0; x < grid[0].length ; x ++){
                if(grid[y][x] == 3)
                    organisms.add(new Organism(x*tileSize,y*tileSize,"wolf",x*tileSize,y*tileSize,initialWolfSpeed, initialWolfSensoryRadius, 100, false));
                if(grid[y][x] == 2)
                    organisms.add(new Organism(x*tileSize,y*tileSize,"rabbit",x*tileSize,y*tileSize,initialRabbitSpeed, initialRabbitSensoryRadius, 100,false));
                if(grid[y][x] == 1)
                    organisms.add(new Organism(x*tileSize,y*tileSize,"plant", x*tileSize,y*tileSize,initialRabbitSpeed, initialRabbitSensoryRadius, 100,false));
            }
        }
    }

    public static void generateGridAccordingToOrganismArray(int[][] grid, ArrayList<Organism> organisms){
        // Resets and then populates the 2D array grid according to the organism array
        for(int y = 0; y < grid.length; y ++ ){
            for(int x = 0; x < grid[0].length ; x ++){
                grid[y][x] = 0;
            }
        }
        for(int i = 0; i < organisms.size(); i ++){
            if(organisms.get(i).type.equals("wolf")){
                grid[organisms.get(i).y / tileSize][organisms.get(i).x / tileSize] = 3;
            }
            if(organisms.get(i).type.equals("rabbit")){
                grid[organisms.get(i).y / tileSize][organisms.get(i).x / tileSize] = 2;
            }
            if(organisms.get(i).type.equals("plant")){
                grid[organisms.get(i).y / tileSize][organisms.get(i).x / tileSize] = 1;
            }
        }

    }




    public static void eatAndReproduceIfNextToFood(Organism organism){
        if(!organism.actionTaken) {
            for (int y = -1; y < 2; y++) {
                for (int x = -1; x < 2; x++) {
                    if (isInGrid(organism.y / tileSize + y, organism.x / tileSize + x)) {
                        if (organism.type.equals("wolf") && simulationGrid[organism.y / tileSize + y][organism.x / tileSize + x] == 2) {
                            // 2 represents rabbits, which is food for wolves.

                            simulationGrid[organism.y / tileSize + y][organism.x / tileSize + x] = 3;
                            // Replaces the rabbit with a new wolf

                            organism.actionTaken = true;
                            organism.hunger = 100;

                            int newSpeed = organism.speed;
                            int newSensoryRadius = organism.sensoryRadius;
                            if (random.nextInt(100) < mutationRate) {
                                int mutateSpeedAdder = random.nextInt(3) - 1;
                                int mutateSensoryAdder = random.nextInt(5) - 2;
                                //Gets a random number in the positive and negative range of the limiters, so organisms mutations can increase or decrease their traits.
                                if (newSpeed + mutateSpeedAdder < mutationSpeedCap && newSpeed + mutateSpeedAdder > 0)
                                    newSpeed += mutateSpeedAdder;
                                if (newSensoryRadius + mutateSensoryAdder < mutationSensoryCap && newSensoryRadius + mutateSensoryAdder > 0)
                                    newSensoryRadius += mutateSensoryAdder;
                                // Makes sure the new trait is not zero or below zero
                            }
                            // Mutates the new rabbit according to its parent, the mutation rate and the trait caps

                            for (int i = 0; i < organisms.size(); i++) {
                                if (organisms.get(i).x == organism.x + x * tileSize && organisms.get(i).y == organism.y + y * tileSize) {
                                    organisms.remove(i);
                                    i += 100;
                                }
                            }
                            // Removes the eaten rabbit from organism Array

                            organisms.add(new Organism(organism.x + x * tileSize, organism.y + y * tileSize, "wolf", organism.x + x * tileSize, organism.y + y * tileSize, newSpeed, newSensoryRadius, 100, true));
                            // Adds the new wolf to the organism Array
                        }
                        if (organism.type.equals("rabbit") && simulationGrid[organism.y / tileSize + y][organism.x / tileSize + x] == 1) {
                            // 1 represents plants, which is food for rabbits.

                            simulationGrid[organism.y / tileSize + y][organism.x / tileSize + x] = 2;
                            // Replaces the plant with a new rabbit

                            organism.actionTaken = true;
                            organism.hunger = 100;

                            int newSpeed = organism.speed;
                            int newSensoryRadius = organism.sensoryRadius;
                            if (random.nextInt(100) < mutationRate) {
                                int mutateSpeedAdder = random.nextInt(3) - 1;
                                int mutateSensoryAdder = random.nextInt(5) - 2;
                                //Gets a random number in the positive and negative range of the limiters, so organisms mutations can increase or decrease their traits.
                                if (newSpeed + mutateSpeedAdder < mutationSpeedCap && newSpeed + mutateSpeedAdder > 0)
                                    newSpeed += mutateSpeedAdder;
                                if (newSensoryRadius + mutateSensoryAdder < mutationSensoryCap && newSensoryRadius + mutateSensoryAdder > 0)
                                    newSensoryRadius += mutateSensoryAdder;
                                // Makes sure the new trait is not zero or below zero
                            }
                            // Mutates the new rabbit according to its parent, the mutation rate and the trait caps

                            for (int i = 0; i < organisms.size(); i++) {
                                if (organisms.get(i).x == organism.x + x * tileSize && organisms.get(i).y == organism.y + y * tileSize) {
                                    organisms.remove(i);
                                    i += 100;
                                }
                            }
                            // Removes the eaten plant from organism Array

                            organisms.add(new Organism(organism.x + x * tileSize, organism.y + y * tileSize, "rabbit", organism.x + x * tileSize, organism.y + y * tileSize, newSpeed, newSensoryRadius, 100, true));
                            // Adds the new rabbit to the organism Array
                        }
                    }
                }
            }
        }
    }


    public static void startRound(){
        for (int i = 0; i < SimulationBoard.organisms.size(); i++) {
            organisms.get(i).actionTaken = false;
        }
        generationCounter ++;
        OverviewPanel.timeCounter = 0;
        generateGridAccordingToOrganismArray(SimulationBoard.simulationGrid, SimulationBoard.organisms);
    }

    public static void decideAction(){
        //This is the process shown on the flowchart for organism actions

        for (int i = 0; i < SimulationBoard.organisms.size(); i++) {
            eatAndReproduceIfNextToFood(organisms.get(i));
            if(hungerDepletesInTwo(organisms.get(i))) {
                goToNearbyFood(organisms.get(i));
            }
            else{
                runAwayFromNearbyPredator(organisms.get(i));
                goToNearbyFood(organisms.get(i));
            }
            randomMovement(organisms.get(i));
        }
    }


    public static void hungerCheck(){
        // Loops through every organism to reduce their hunger, if one falls below 0 the organism gets removed
        for(int i = organisms.size() - 1; i > -1;  i--){
            if(!organisms.get(i).type.equals("plant")) {
            // Hunger doesn't apply to plants
                organisms.get(i).hunger -= hungerReductionPerRound;
                if (organisms.get(i).hunger < 1)
                    organisms.remove(i);
            }
        }
    }


    public static Boolean hungerDepletesInTwo(Organism organism){
        return organism.hunger - hungerReductionPerRound * 2 < 1;
    }


    public static void goToNearbyFood(Organism organism){
        if(!organism.actionTaken) {
            PathFind.Point start = new PathFind.Point(organism.x / SimulationBoard.tileSize, organism.y / SimulationBoard.tileSize, null);
            PathFind.Point end = SimulationBoard.getFoodCoordsInSensoryRadius(organism);
            java.util.List<PathFind.Point> path = null;
            if (end != null) {
                path = PathFind.FindPath(SimulationBoard.simulationGrid, start, end);
                if (path != null) {
                    SimulationBoard.followPath(organism, path);
                    organism.actionTaken = true;
                }
            }
            // !=null to make sure no pathing finding or following is done if no food was found
        }
    }


    public static void followPath(Organism organism, java.util.List<PathFind.Point> path){
        if(organism.speed < path.size()-1) {
            organism.targetX = path.get(organism.speed-1).x * tileSize;
            organism.targetY = path.get(organism.speed-1).y * tileSize;
        }
        else{
            if(path.size()-2 > -1) {
                organism.targetX = path.get(path.size() - 2).x * tileSize;
                organism.targetY = path.get(path.size() - 2).y * tileSize;
            }
        }
        //If else makes sure a low speed organism doesn't produce a out of bounds error for searching at -1 index
    }


    public static PathFind.Point getFoodCoordsInSensoryRadius (Organism organism){
        for (int i = 1; i < organism.sensoryRadius + 1; i++) {
            for (int y = -i; y < i + 1; y++) {
                for (int x = -i; x < i + 1; x++) {
                    // Using three nested for loops so that the function checks from the inner most circle (of squares) of locations, and if nothing is found, it checks the next most inner circle of locations

                    if(organism.type.equals("rabbit")){
                        if (isInGrid(organism.y / tileSize + y, organism.x / tileSize + x) && simulationGrid[organism.y / tileSize + y][organism.x / tileSize + x] == 1)
                            return new PathFind.Point(organism.x / tileSize + x, organism.y / tileSize + y, null);
                    }
                    // Returns food for rabbits

                    if(organism.type.equals("wolf")){
                        if (isInGrid(organism.y / tileSize + y, organism.x / tileSize + x) && simulationGrid[organism.y / tileSize + y][organism.x / tileSize + x] == 2)
                            return new PathFind.Point(organism.x / tileSize + x, organism.y / tileSize + y, null);
                    }
                    // Returns food for wolves
                }
            }
        }
        return null;
    }


    public static void runAwayFromNearbyPredator(Organism organism){
        if(!organism.actionTaken) {
            if (organism.type.equals("rabbit")) {
            // Wolves don't have predators, and plants can't move

                for (int i = 1; i < organism.sensoryRadius + 1; i++) {
                    for (int y = -i; y < i + 1; y++) {
                        for (int x = -i; x < i + 1; x++) {
                        // Using three nested for loops so that the function checks from the inner most circle (of squares) of locations, and if nothing is found, it checks the next most inner circle of locations

                            if (isInGrid(organism.y / tileSize + y, organism.x / tileSize + x) && simulationGrid[organism.y / tileSize + y][organism.x / tileSize + x] == 3) {
                                if (y <= 0 && isInGrid(organism.y / tileSize + organism.speed, organism.x / tileSize))
                                    organism.targetY = organism.y + organism.speed * tileSize;
                                    //Runs down if the predator is above

                                else if (y > 0 && isInGrid(organism.y / tileSize - organism.speed, organism.x / tileSize))
                                    organism.targetY = organism.y - organism.speed * tileSize;
                                    //Runs up is predator is below

                                organism.actionTaken = true;
                            }
                        }
                    }
                }
            }
        }
    }


    public static void randomMovement(Organism organism){
        if(!organism.actionTaken) {
            // Gets a random number between 0 and 3, 0 means the organism will go right, 1 means down, 2 means left, 3 means up.
            // If the organism in about to move outside of the grid, its 'moveDirection' is incremented so it changes direction.
            // If the organism is centered by other organisms and edges, it will not move.
            // If the organism is a plant, it will not move.
            if (!organism.type.equals("plant")) {
                int moveDirection = random.nextInt(4);
                if (moveDirection == 0) {
                    if (isInGrid(organism.y / tileSize, organism.x / tileSize + organism.speed)
                            && simulationGrid[organism.y / tileSize][organism.x / tileSize + organism.speed] == 0)
                        organism.targetX = organism.x + organism.speed * tileSize;
                    else
                        moveDirection = 1;
                }
                if (moveDirection == 1) {
                    if (isInGrid(organism.y / tileSize + organism.speed, organism.x / tileSize)
                            && simulationGrid[organism.y / tileSize + organism.speed][organism.x / tileSize] == 0)
                        organism.targetY = organism.y + organism.speed * tileSize;
                    else
                        moveDirection = 2;
                }
                if (moveDirection == 2) {
                    if (isInGrid(organism.y / tileSize, organism.x / tileSize - organism.speed)
                            && simulationGrid[organism.y / tileSize][organism.x / tileSize - organism.speed] == 0)
                        organism.targetX = organism.x - organism.speed * tileSize;
                    else
                        moveDirection = 3;
                }
                if (moveDirection == 3) {
                    if (isInGrid(organism.y / tileSize - organism.speed, organism.x / tileSize)
                            && simulationGrid[organism.y / tileSize - organism.speed][organism.x / tileSize] == 0)
                        organism.targetY = organism.y - organism.speed * tileSize;
                }
            }
        }
    }

    public static void generateNewPlants(){
        // Makes sure rabbits always have food in the environment

        int plantCount = 0;
        int rabbitCount = 0;
        for(int y = 0; y < simulationGrid.length; y ++ ){
            for(int x = 0; x < simulationGrid[0].length ; x ++){
                if(simulationGrid[y][x] == 1)
                    plantCount ++;
                if(simulationGrid[y][x] == 2)
                    rabbitCount ++;
            }
        }
        // Counts the number of rabbits and plants in simulation

        while(plantCount < rabbitCount && plantCount < maxPlantNum){
            for(int y = 0; y < simulationGrid.length; y ++ ){
                for(int x = 0; x < simulationGrid[0].length ; x ++){
                    if(simulationGrid[y][x] == 0) {
                        if (0 == random.nextInt(480)) {
                            simulationGrid[y][x] = 1;
                            organisms.add(new Organism(x * tileSize, y * tileSize, "plant", x * tileSize, y * tileSize, 0, 0, 100, true));
                            plantCount++;
                        }
                    }
                }
            }
        }
        // Adds plants at random locations until the number of plants = the number of rabbits in simulation, plants also can't exceed maximum plant number
    }

    public static boolean isInGrid(int y, int x) {
        if ((y < 0) || (x < 0)) {
            return false;
        }
        if((y >= tileWorldHeight) || (x >= tileWorldWidth)) {
            return false;
        }
        return true;
    }

    public static void checkOverlaps(){
        for(int i = 0 ; i < organisms.size() ; i++){
            for(int j = 0 ; j < organisms.size() ; j++){
                if ( i != j && organisms.get(i).targetX == organisms.get(j).targetX && organisms.get(i).targetY == organisms.get(j).targetY){
                    organisms.get(i).targetX = organisms.get(i).x;
                    organisms.get(i).targetY = organisms.get(i).y;
                }
            }
        }

    }

    public static void moveToTargetLocation(){
        for (int i = 0; i < SimulationBoard.organisms.size(); i++) {
            if(organisms.get(i).x < organisms.get(i).targetX)
                organisms.get(i).x += movementVelocity;
            if(organisms.get(i).x > organisms.get(i).targetX)
                organisms.get(i).x -= movementVelocity;
            if(organisms.get(i).y < organisms.get(i).targetY)
                organisms.get(i).y += movementVelocity;
            if(organisms.get(i).y > organisms.get(i).targetY)
                organisms.get(i).y -= movementVelocity;
        }
    }

    public static void checkPositions(){
        for (int i = 0; i < SimulationBoard.organisms.size(); i++) {
            if(!(organisms.get(i).x == organisms.get(i).targetX))
                organisms.get(i).x = organisms.get(i).targetX;
            if(!(organisms.get(i).y == organisms.get(i).targetY))
                organisms.get(i).y = organisms.get(i).targetY;
        }
    }

    public static double getRabbitAvgSpeed(){
        double sumOfSpeeds = 0;
        int rabbitCount = 0;
        for (int i = 0; i < SimulationBoard.organisms.size(); i++){
            if(organisms.get(i).type.equals("rabbit")){
                sumOfSpeeds += organisms.get(i).speed;
                rabbitCount ++;
            }
        }
        return sumOfSpeeds/rabbitCount;
    }

    public static double getRabbitAvgSensory(){
        double sumOfSensory = 0;
        int rabbitCount = 0;
        for (int i = 0; i < SimulationBoard.organisms.size(); i++){
            if(organisms.get(i).type.equals("rabbit")){
                sumOfSensory += organisms.get(i).sensoryRadius;
                rabbitCount ++;
            }
        }
        return sumOfSensory/rabbitCount;
    }

    public static double getWolfAvgSpeed(){
        double sumOfSpeeds = 0;
        int wolfCount = 0;
        for (int i = 0; i < SimulationBoard.organisms.size(); i++){
            if(organisms.get(i).type.equals("wolf")){
                sumOfSpeeds += organisms.get(i).speed;
                wolfCount ++;
            }
        }
        return sumOfSpeeds/wolfCount;
    }

    public static double getWolfAvgSensory(){
        double sumOfSensory = 0;
        int wolfCount = 0;
        for (int i = 0; i < SimulationBoard.organisms.size(); i++){
            if(organisms.get(i).type.equals("wolf")){
                sumOfSensory += organisms.get(i).sensoryRadius;
                wolfCount ++;
            }
        }
        return sumOfSensory/wolfCount;
    }


    public void paint(Graphics g) {

        super.paintComponent(g);

        // Drawing the 2d array of Tile objects
        for(int y = 0; y < tileMap.length; y ++ ){
            for(int x = 0; x < tileMap[0].length ; x ++){
                tileMap[y][x].draw(g);
            }
        }

        //Drawing Organism objects
        for (int i = 0; i < organisms.size(); i ++){
            organisms.get(i).draw(g);
        }

        repaint();
    }
}