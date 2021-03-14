<template lang="pug">
  #app
    .container.is-widescreen
      Logo
      #nav
        router-link(to="/") main
        router-link(to="/about") about
      router-view
</template>

<script>
import Logo from "@/components/Logo.vue";
export default {
  components: {
    Logo
  }
};
</script>

<style lang="sass">
//
</style>
