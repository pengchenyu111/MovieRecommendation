import httpFetch from "@/api/httpFetch";

export const queryIMDBRating = (doubanId) => {
  const url = `imdbRatings/doubanId/${doubanId}`
  return httpFetch.get(url)
}
