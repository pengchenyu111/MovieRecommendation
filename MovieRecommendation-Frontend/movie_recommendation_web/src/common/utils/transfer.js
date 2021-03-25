export default {

  numberListToString(list, division) {
    return list.join(division)
  },

  stringToNumberList(str, division) {
    return str.split(division).map(Number)
  }
}
