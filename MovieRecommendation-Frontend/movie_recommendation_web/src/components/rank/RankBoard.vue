<template>
  <div>
    <link rel="stylesheet" type="text/css" href="https://ianlunn.github.io/Hover/css/hover.css">
    <div class="rank-title-style">
      <slot name="rank-title"></slot>
    </div>
    <vueper-slides
      class="no-shadow"
      :autoplay="true"
      :duration="4000"
      :slideMultiple="true"
      :visible-slides="5"
      :slide-ratio="1 / 4"
      :gap="3"
      :arrowsOutside="true"
      :dragging-distance="70">
      <vueper-slide
        v-for="item in movieList"
        :key="item.doubanId"
        :image="item.coverUrl"
        class="hvr-grow-shadow"
        :link="movieDetailUrl(item.doubanId)">
        <template v-slot:content>
          <div class="movie-title-container-style">
            <div class="movie-title-container-bottom-style">
            </div>
              <div style="margin: 0 0 0 10px; color: black">{{ item.title.split(" ")[0] }}</div>

          </div>
        </template>
      </vueper-slide>
      <vueper-slide class="rank_get_more_style" :link="moreUrl">
        <template v-slot:content>
          <div class="vueperslide__content-wrapper" style="flex-direction: row">
            <span style="font-size: 20px; color: #FFFFFF">获 取 更 多</span>
          </div>
        </template>
      </vueper-slide>
    </vueper-slides>
  </div>
</template>

<script>
import {VueperSlides, VueperSlide} from 'vueperslides'
import 'vueperslides/dist/vueperslides.css'

export default {
  name: "RankBoard",
  components: {VueperSlides, VueperSlide},
  props: {
    movieList: Array,
    getMoreUrl: String
  },
  computed: {
    movieDetailUrl() {
      return (id) => 'movie/' + id
    },
    moreUrl() {
      return this.getMoreUrl
    }
  },
  methods: {
  }
}
</script>

<style scoped>
.rank-title-style {
  margin-top: 100px;
  margin-bottom: 30px;
  text-align: center;
  font-size: 80px;
  font-weight: bolder;
}

.movie-title-container-style {
  position: absolute;
  bottom: 0;
  width: 100%;
  margin-top: 215px;
  font-size: 20px;
  font-weight: bolder;
  background: linear-gradient(rgba(255, 255, 255, 0.05), rgba(255, 255, 255, 0.9));
}

.movie-title-container-bottom-style {
  height: 100px;
}

.rank_get_more_style {
  background: linear-gradient(135deg, #facdae, #84af9b);
}
</style>
