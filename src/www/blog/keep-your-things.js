import React from "react"
import ReactMarkdown from "react-markdown"

export default {
  date: new Date("Jan 17 2019").getTime(),
  title: "Keep your things",
  tagline:
    "You asked for this and there is it! You can have more than one thing!",
  world: {
    rooms:
      "0 0:Hall of Warriors:0 -1 -1 -1:nothing\n" +
      "Great hall displaying the best artifacts from the greater warriors.\n" +
      "::::\n" +
      "1 0:Swords corridor:0 0 -1 -1:dagger\n" +
      "The best swords of the world are displayed in this corridor\n" +
      "::::\n" +
      "2 0:Swords end:-1 0 0 -1:excalibur\n" +
      "The best swords of the world are displayed in this corridor\n" +
      "::::\n" +
      "2 1:Shields corridor:-1 -1 0 0:woodshield\n" +
      "The best shields of the world are displayed in this corridor\n" +
      "::::\n" +
      "2 2:Shields end:-1 0 -1 0:ancile\n" +
      "The best shields of the world are displayed in this corridor\n" +
      "::::\n" +
      "1 2:Sunny room:0 0 -1 -1:key\n" +
      "Just a pleasant room with huge windows to enjoy the sun\n" +
      "::::\n" +
      "0 2:Misterious room:0 -1 -1 123:rustykey\n" +
      "Where this room goes?\n" +
      "::::\n" +
      "0 1:Secret room:-1 -1 0 -1:nothing\n" +
      "There is the secret scroll.\n" +
      "::::\n",
    items:
      "dagger: weapon 10\n" +
      "excalibur: weapon 20\n" +
      "woodshield: shield 2\n" +
      "ancile: shield 10\n" +
      "key: key 123\n" +
      "rustykey: key 234\n",
  },
  map:
    "+----------------+      +----------------+      +---------------+      \n" +
    "|   SWORDS END   |      |SHIELDS CORRIDOR|      |  SHIELDS END  |      \n" +
    "|the best swor...o------othe best shie...o------othe best shi...|      \n" +
    "|     (excalibur)|      |    (woodshield)|      |       (ancile)|      \n" +
    "+-------o--------+      +----------------+      +------o--------+      \n" +
    "        |                                              |               \n" +
    "        |                                              |               \n" +
    "+-------o--------+                              +------o--------+      \n" +
    "| SWORDS CORRIDOR|                              |   SUNNY ROOM  |      \n" +
    "|the best swor...|                              |just a pleas...|      \n" +
    "|        (dagger)|                              |          (key)|      \n" +
    "+-------o--------+                              +------o--------+      \n" +
    "        |                                              |               \n" +
    "        |                                              |               \n" +
    "+-------o--------+      +----------------+      +------o--------+      \n" +
    "|HALL OF WARRIORS|      |   SECRET ROOM  |      |MISTERIOUS ROOM|      \n" +
    "|great hall di...x      xthere is the ...o------▒where this r...|      \n" +
    "|                |      |                |      |     (rustykey)|      \n" +
    "+----------------+      +----------------+      +---------------+      \n",
  Component: () => (
    <ReactMarkdown
      source={`
## Carry more than one weapon

Tired of killing flies with a cannon? Tired of those players that
follows you everywhere waiting to drop accidentally your best
weapon? That has finished! You have now a bag where to keep your weapon

    > look 
    Hall of Warriors
    Great hall displaying the best artifacts from the
    greater warriors.
    Exits: north.
    Player has 16 life points.
    There is: kirito.
    > move north
    Swords corridor
    The best swords of the world are displayed in
    this corridor
    There is the dagger weapon.
    Exits: north, south.
    Player has 16 life points.
    There is: kirito.
    > get 
    Swords corridor
    The best swords of the world are displayed in
    this corridor
    Exits: north, south.
    Player has the dagger weapon.
    Player has 16 life points.
    There is: kirito.
    > move north
    Swords end
    The best swords of the world are displayed in
    this corridor
    There is the excalibur weapon.
    Exits: south, east.
    Player has the dagger weapon.
    Player has 16 life points.
    There is: kirito.
    > get 
    Swords end
    The best swords of the world are displayed in
    this corridor
    Exits: south, east.
    Player has the excalibur weapon.
    Player has 16 life points.
    There is: kirito.
    You have: dagger(weapon).
    > █
    
Did you have seen that? your other weapon is in your bag.

## Equip the other sword

That is right, you can have more than one weapon, but
how do you decide which one to use? It is easy, just
equip the weapon that you like more!

    > equip dagger
    Swords end
    The best swords of the world are displayed in
    this corridor
    Exits: south, east.
    Player has the dagger weapon.
    Player has 16 life points.
    There is: kirito.
    You have: excalibur(weapon).
    > █

## You cannot equip what you do not have

Of course what you have is just a bag, nothing else.
It is not a dark magic that invokes the item that you
want. If the item is not in the bag, you cannot
equip that item.

    > equip anduril
    Ops, this item is not in the bag.
    > █

## Carry more than one shield

If you can carry more than one weapon, why you cannot
carry more than one shield? You can!
Get as many shield as you want.

    > move east
    Shields corridor
    The best shields of the world are displayed in
    this corridor
    There is the woodshield shield.
    Exits: east, west.
    Player has the dagger weapon.
    Player has 16 life points.
    There is: kirito.
    You have: excalibur(weapon).
    > get 
    Shields corridor
    The best shields of the world are displayed in
    this corridor
    Exits: east, west.
    Player has the woodshield shield.
    Player has the dagger weapon.
    Player has 16 life points.
    There is: kirito.
    You have: excalibur(weapon).
    > move east
    Shields end
    The best shields of the world are displayed in
    this corridor
    There is the ancile shield.
    Exits: south, west.
    Player has the woodshield shield.
    Player has the dagger weapon.
    Player has 16 life points.
    There is: kirito.
    You have: excalibur(weapon).
    > get 
    Shields end
    The best shields of the world are displayed in
    this corridor
    Exits: south, west.
    Player has the ancile shield.
    Player has the dagger weapon.
    Player has 16 life points.
    There is: kirito.
    You have: excalibur(weapon), woodshield(shield).
    > █

## Equip the other shield

And of course, you can change of field by equipping the other field.

    > equip woodshield
    Shields end
    The best shields of the world are displayed in
    this corridor
    Exits: south, west.
    Player has the woodshield shield.
    Player has the dagger weapon.
    Player has 16 life points.
    There is: kirito.
    You have: excalibur(weapon), ancile(shield).
    > █

## Carry more than one key

Yes! I know, you have been waiting for that one. You are tired to return to
old rooms looking for those keys that you have already seen.
Now you can have them and carry them in your bag.

    > move south
    Sunny room
    Just a pleasant room with huge windows to enjoy
    the sun
    There is the key key.
    Exits: north, south.
    Player has the woodshield shield.
    Player has the dagger weapon.
    Player has 16 life points.
    There is: kirito.
    You have: excalibur(weapon), ancile(shield).
    > get 
    Sunny room
    Just a pleasant room with huge windows to enjoy
    the sun
    Exits: north, south.
    Player has the key key.
    Player has the woodshield shield.
    Player has the dagger weapon.
    Player has 16 life points.
    There is: kirito.
    You have: excalibur(weapon), ancile(shield).
    > move south
    Misterious room
    Where this room goes?
    There is the rustykey key.
    Exits: north, west (closed).
    Player has the key key.
    Player has the woodshield shield.
    Player has the dagger weapon.
    Player has 16 life points.
    There is: kirito.
    You have: excalibur(weapon), ancile(shield).
    > get 
    Misterious room
    Where this room goes?
    Exits: north, west (closed).
    Player has the rustykey key.
    Player has the woodshield shield.
    Player has the dagger weapon.
    Player has 16 life points.
    There is: kirito.
    You have: excalibur(weapon), ancile(shield),
    key(key).
    > █

## But, only one opens of the door

We can make your life more easy, but we cannot make it too easy. It is not funny.
You may carry the good key in your bag, but you will not be able to guess which one is.
Equip the key that you want to use, and try!

    > move west
    Ops, exit west is closed and requires a key.
    > equip key
    Misterious room
    Where this room goes?
    Exits: north, west (closed).
    Player has the key key.
    Player has the woodshield shield.
    Player has the dagger weapon.
    Player has 16 life points.
    There is: kirito.
    You have: excalibur(weapon), ancile(shield),
    rustykey(key).
    > move west
    Secret room
    There is the secret scroll.
    Exits: east.
    Player has the woodshield shield.
    Player has the dagger weapon.
    Player has 16 life points.
    There is: kirito.
    You have: excalibur(weapon), ancile(shield),
    rustykey(key).
    > █

    `}
    />
  ),
}
