> **Note:** This was a high school summer project of mine from 2020. It doesn't reflect my current skill level or coding practices — I'm keeping it up as a snapshot of where I started.

# Evolution Simulator

A grid-based predator-prey simulation built with Java Swing. Rabbits eat plants, wolves eat rabbits, and each meal has a chance to mutate the offspring's speed and sensory radius, so populations evolve over generations as they compete for food and survival.

## Features
- Live simulation grid with rabbits, wolves, and plants moving, eating, fleeing, and reproducing each round
- Start/pause control and a playback speed slider (locked while running)
- Live JFreeChart graphs tracking average rabbit/wolf speed and sensory radius over time
- Adjustable starting settings: population counts, initial traits, mutation rate/caps, hunger and plant regrowth rates

## Running it
Open the project in IntelliJ IDEA — the `.idea/` project config is included and points at the JFreeChart/JCommon jars already bundled in `lib/`. Run `src/main.java`.

A packaged standalone macOS app (no Java install required) can also be built with `jpackage`.
