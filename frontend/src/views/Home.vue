<template lang="pug">
  .home
    Instruments
    br
    br
    PianoScheme
    br
    br
    UkuleleScheme
    br
    br
    GuitarScheme
</template>

<script>
import Instruments from "@/components/Instruments";
import PianoScheme from "@/components/PianoScheme";
import UkuleleScheme from "@/components/UkuleleScheme";
import GuitarScheme from "@/components/GuitarScheme";
export default {
  name: "Home",
  components: {
    Instruments,
    PianoScheme,
    UkuleleScheme,
    GuitarScheme
  }
};
</script>

<style lang="sass">
.logo
  width: 100px
</style>
