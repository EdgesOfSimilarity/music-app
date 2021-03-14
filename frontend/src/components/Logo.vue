<template lang="pug">
  router-link.logo(to="/")
    img(src="../assets/img/logo.svg" alt="")
</template>

<script>
export default {
  name: "Logo"
};
</script>

<style scoped lang="sass">
.logo
  width: 100px
  height: 100px
  display: flex
  justify-content: center
  margin: 32px auto
</style>
