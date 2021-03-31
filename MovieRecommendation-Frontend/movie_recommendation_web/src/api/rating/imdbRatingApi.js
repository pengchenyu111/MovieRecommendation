import httpFetch from "@/api/httpFetch";

export const queryIMDBRating = (doubanId) => {
  const url = `imdbRatings/doubanId/${doubanId}`
  return httpFetch.get(url)
}

export  const queryIMDBVotes = (doubanId) => {
  const url = `imdbRatings/vote/doubanId/${doubanId}`
  return httpFetch.get(url)
}
