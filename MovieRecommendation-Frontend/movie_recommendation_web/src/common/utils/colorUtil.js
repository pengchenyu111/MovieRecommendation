export default {
  randomColorPicker() {
    const colors = [
      "#ff4364",
      "#fc9d99",
      "#facdae",
      "#c8c7a8",
      "#84af9b",

      "#b6c29a",
      "#8a977b",
      "#f4d000",
      "#e58308",
      "#dc5712",
      "#b6c29a",

      "#b2c8bb",
      "#458994",
      "#757947",
      "#725334",

      "#823835",
      "#8abeb2",
      "#c9ba83",
      "#ddd38c",
      "#df9d53",
    ]

    return colors[Math.floor((Math.random() * colors.length))];
  }
}

