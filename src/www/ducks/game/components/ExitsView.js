import React from "react"

const ExitsView = ({ exits }) =>
  (exits && exits.length > 0 && (
    <div>
      Exits:{" "}
      {exits
        .map(exit => `${exit.name}${exit.open ? "" : " (closed)"}`)
        .join(", ")}
      .
    </div>
  )) ||
  null

export default ExitsView
