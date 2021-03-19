<template>
  <div>首页</div>
  <div>{{ $store.state.currentUser }}</div>
  <div>{{ userInfo }}</div>
</template>

<script>
export default {
  name: "Home",
  data () {
    return {
      userInfo: sessionStorage.getItem("currentUser")
    }
  }
}
</script>

<style scoped>

</style>
