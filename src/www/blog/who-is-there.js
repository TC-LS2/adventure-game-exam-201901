import React from "react"
import ReactMarkdown from "react-markdown"

export default {
  date: new Date("Dec 01 2018").getTime(),
  title: "Who is there?",
  world: {
    rooms:
      "== rooms:\n" +
      "0 0:Meeting point:-1 -1 -1 -1:nothing\n" +
      "This is a large room. Probably the largest room in the world. Everyone, sooner or later, comes here. Sometimes is calm and quiet. Sometimes is crowd and full of people.\n" +
      "::::\n",
  },
  map:
    "+-------------+      \n" +
    "|MEETING POINT|      \n" +
    "|this is a ...|      \n" +
    "|             |      \n" +
    "+-------------+      \n",
  tagline:
    "Did you ever have thought that you were alone playing? Well, you are not alone! You never have been alone!",
  Component: () => (
    <ReactMarkdown
      source={`
## Everything is calm when you are alone.
Time to time, you will wander across the world, you will reach new rooms and new places, and no one will be there with you. But now will will know.

    > login kirito
    Meeting point
    This is a large room. Probably the largest room
    in the world. Everyone, sooner or later, comes
    here. Sometimes is calm and quiet. Sometimes is
    crowd and full of people.
    Player has 16 life points.
    There is: kirito.
    > █

## But it can get full of friends.
Did you ever have seen how things disappeared in front of your eyes? Did you know that were other players who actually got they? Did you know that you can see them? Invite your friends and play all together.

    > login asuna
    > login leafa
    > login sinon
    > login kirito
    Meeting point
    This is a large room. Probably the largest room
    in the world. Everyone, sooner or later, comes
    here. Sometimes is calm and quiet. Sometimes is
    crowd and full of people.
    Player has 16 life points.
    There is: kirito.
    There is: asuna, leafa, sinon, kirito.
    > █



    `}
    />
  ),
}
