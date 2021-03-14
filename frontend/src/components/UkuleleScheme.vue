<template lang="pug">
  .ukuleleScheme
    .ukuleleScheme__strings(v-for="string in [1,2,3,4]")
      .ukuleleScheme__string
        .ukuleleScheme__fret(v-for="fret in [0, 1,2,3,4,5,6,7,8,9,10,11,12]")
          .ukuleleScheme__note X
    .ukuleleScheme__frets
      .ukuleleScheme__fret(v-for="fret in [0,1,2,3,4,5,6,7,8,9,10,11,12]")
        .ukuleleScheme__nums {{ fret === 0 ? '' : fret }}
</template>

<script>
export default {}
</script>

<style scoped lang="sass">
.ukuleleScheme
  width: 100%
  &__string
    display: flex
    position: relative
    height: 32px
    margin-bottom: 8px
    &:before
      content: ''
      position: absolute
      top: 50%
      transform: translateY(-50%)
      left: 80px
      width: 100%
      height: 2px
      background: #000000
  &__fret
    display: flex
    justify-content: center
    width: 64px
    &:first-child
      & .ukuleleScheme__note
        background: #aafff1
  &__note
    width: 32px
    height: 32px
    background: #e098ff
    border-radius: 50%
    position: relative
    display: flex
    justify-content: center
    align-items: center
    z-index: 2
  &__frets
    display: flex
  &__nums
    width: 32px
    display: flex
    justify-content: center
</style>