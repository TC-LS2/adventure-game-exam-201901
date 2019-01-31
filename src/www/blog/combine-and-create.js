import React from "react"
import ReactMarkdown from "react-markdown"

export default {
  date: new Date("Jan 29 2019").getTime(),
  title: "Combine and Create big things",
  tagline: "Now you have learned a new skill, you can do alchemy!",
  world: {
    rooms:
      "0 0:Kitchen:0 -1 0 -1:chicken\n" +
      "A kitchen with some food.\n" +
      "::::\n" +
      "0 1:Garden:0 -1 -1 0:stick\n" +
      "Nice garden with nice plants\n" +
      "::::\n" +
      "1 1:River:-1 0 -1 0:rock\n" +
      "Nice river with nice fishes\n" +
      "::::\n" +
      "1 0:Closet:-1 0 0 -1:onion\n" +
      "Large food store\n" +
      "::::\n",
    items:
      "yakitori: food 4\n" +
      "chicken: food 1\n" +
      "stick: weapon 1\n" +
      "rock: weapon 1\n" +
      "onion: food 1\n",
    combinations: "yakitori: chicken,stick\n",
  },
  map:
    "+-------+      +------+      \n" +
    "| CLOSET|      | RIVER|      \n" +
    "|larg...o------onic...|      \n" +
    "|(onion)|      |(rock)|      \n" +
    "+--o----+      +--o---+      \n" +
    "   |              |          \n" +
    "   |              |          \n" +
    "+--o----+      +--o---+      \n" +
    "|KITCHEN|      |GARDEN|      \n" +
    "|a ki...o------onic...|      \n" +
    "|(chi...|      |(st...|      \n" +
    "+-------+      +------+      \n",
  Component: () => (
    <ReactMarkdown
      source={`
### Combine items into new items

You can create new items from items that you have in your bag. Just try it:

    > look
    Kitchen
    A kitchen with some food.
    There is the chicken food.
    Exits: north, east.
    Player has 16 life points.
    There is: kirito.
    > get
    Kitchen
    A kitchen with some food.
    Exits: north, east.
    Player has 16 life points.
    There is: kirito.
    You have: chicken(food).
    > move east
    Garden
    Nice garden with nice plants
    There is the stick weapon.
    Exits: north, west.
    Player has 16 life points.
    There is: kirito.
    You have: chicken(food).
    > get
    Garden
    Nice garden with nice plants
    Exits: north, west.
    Player has the stick weapon.
    Player has 16 life points.
    There is: kirito.
    You have: chicken(food).
    > move north
    River
    Nice river with nice fishes
    There is the rock weapon.
    Exits: south, west.
    Player has the stick weapon.
    Player has 16 life points.
    There is: kirito.
    You have: chicken(food).
    > get
    River
    Nice river with nice fishes
    Exits: south, west.
    Player has the rock weapon.
    Player has 16 life points.
    There is: kirito.
    You have: chicken(food), stick(weapon).
    > combine chicken stick
    River
    Nice river with nice fishes
    Exits: south, west.
    Player has the rock weapon.
    Player has 16 life points.
    There is: kirito.
    You have: yakitori(food).
    > █

### Not everything combines

You have to guess what you can do. Not everything can be combined to obtain something new:

    > move west
    Closet
    Large food store
    There is the onion food.
    Exits: south, east.
    Player has the rock weapon.
    Player has 16 life points.
    There is: kirito.
    You have: yakitori(food).
    > get
    Closet
    Large food store
    Exits: south, east.
    Player has the rock weapon.
    Player has 16 life points.
    There is: kirito.
    You have: yakitori(food), onion(food).
    > combine yakitori onion
    Ops, there is no combination.
    > █

### You must have the ingredients

No ingredients? No new item. Sorry.

    > combine chicken stick
    Ops, this item is not in the bag.
    > █

    `}
    />
  ),
}
