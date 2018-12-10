import React from "react"

export default ({ player: { username, lifePoints, key, shield, weapon } }) => (
  <span>
    {` ${username}`} ({lifePoints} pts)
    {key && ` Key: ${key.name}`}
    {shield && ` Shield: ${shield.name}`}
    {weapon && ` Weapon: ${weapon.name}`}
  </span>
)
