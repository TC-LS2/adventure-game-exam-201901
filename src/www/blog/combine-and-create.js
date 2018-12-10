import React from "react"
import ReactMarkdown from "react-markdown"

export default {
  date: new Date("Jan 28 2019").getTime(),
  title: "Combine and Create",
  tagline:
    "Solve me",
  world: {
    rooms:
      "Solve me",
    items:
      "Solve me",
  },
  map:
    "solve me",
  Component: () => (
    <ReactMarkdown
      source={`
Solve me

    `}
    />
  ),
}
